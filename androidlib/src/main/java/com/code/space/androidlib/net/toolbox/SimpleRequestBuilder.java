package com.code.space.androidlib.net.toolbox;

import android.content.Context;
import android.util.Log;

import com.code.space.androidlib.app.CSApplication;
import com.code.space.androidlib.net.INetResult;
import com.code.space.androidlib.net.IRequest;
import com.code.space.androidlib.net.IUpload;
import com.code.space.androidlib.net.Method;
import com.code.space.androidlib.net.NetBiz;
import com.code.space.androidlib.net.ResponseListener;
import com.code.space.androidlib.utilities.utils.AppUtils;
import com.code.space.androidlib.utilities.utils.CollectionUtils;
import com.code.space.androidlib.utilities.utils.ContextUtils;
import com.code.space.androidlib.utilities.utils.LogUtils;
import com.code.space.androidlib.utilities.utils.ObjectUtils;
import com.code.space.androidlib.utilities.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 网络请求builder的实现
 */
public class SimpleRequestBuilder<RESPONSE> implements IRequest<RESPONSE>, INetResult<RESPONSE>, Runnable {

    protected String url;
    protected int id;

    protected Map<String, String> params;
    protected Map<String, IUpload> fileMap;
    protected int method = Method.POST;

    protected boolean autoControlDialog;
    protected Context contextShowDialog;

    protected String name = "unnamed";

    protected ResponseListener<RESPONSE> listener;
    protected Class<RESPONSE> clazz;

    protected String sessionId;

    @Override
    public SimpleRequestBuilder<RESPONSE> url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> params(Map<String, String> params) {
        if (this.params == null) this.params = params;
        else this.params.putAll(params);
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> files(Map<String, IUpload> files) {
        if (fileMap == null) this.fileMap = files;
        else this.fileMap.putAll(files);
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> method(int method) {
        this.method = method;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> showDialog(Context context) {
        if (context != null) {
            this.contextShowDialog = context;
            this.autoControlDialog = true;
        } else {
            autoControlDialog = false;
        }
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> listener(ResponseListener<RESPONSE> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> clazz(Class<RESPONSE> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> addParam(String key, Object value) {
        if (params == null) params = new HashMap<>();
        params.put(key, ObjectUtils.toString(value));
        return this;
    }

    @Override
    public SimpleRequestBuilder<RESPONSE> addParam(String key, List<?> list) {
        if (params == null) params = new HashMap<>();
        StringBuilder sb = new StringBuilder(key);
        for (int i = 0; i < list.size(); i++) {
            params.put(sb.append("[]").toString(), ObjectUtils.toString(list.get(i)));
            sb.delete(key.length(),sb.length());
        }
        return this;
    }


    @Override
    public SimpleRequestBuilder<RESPONSE> addFile(String key, IUpload file) {
        if (fileMap == null) fileMap = new HashMap<>();
        fileMap.put(key, file);
        return this;
    }

    protected String getURL() {
        return ContextUtils.<CSApplication>getApplication(contextShowDialog).getAppInfo().appendRoot(url);
    }

    protected RESPONSE parse(String response) {
        Log.d("TAG", "response:" + response);
        if (getClazz() != null) return StringUtils.json2Entity(response, getClazz());
        else return StringUtils.json2Entity(response);
    }

    protected Class<RESPONSE> getClazz() {
        return clazz;
    }

    protected ResponseListener<RESPONSE> getListener() {
        return listener;
    }

    protected Map<String, String> getParams() {
        return params;
    }

    protected Map<String, IUpload> getFiles() {
        return fileMap;
    }


    @Override
    public void connect() {
        try {
            if (autoControlDialog) {
                showNetLoading();
            }
            String url = getURL();
            url = NetBiz.checkUrl(url);
            if (fileMap != null && method == Method.GET) method = Method.POST;
            Request.Builder requestBuilder = new Request.Builder();
            if (method == Method.GET && params != null)
                url = StringUtils.map2GetParams(url, params);
            requestBuilder.url(url);
            RequestBody body = null;
            if (method == Method.POST) {
                body = toRequestBody(getParams(), getFiles());
            }
            if (body != null) requestBuilder.post(body);
//            setCookie(client, requestBuilder);
            modifyRequest(requestBuilder);
            okhttp3.Request request = requestBuilder.build();
            OkHttpClient client = NetBiz.getClient().newBuilder().cookieJar(new CookiesManager()).build();
            this.okCall = client.newCall(request);
            this.okCall.enqueue(this);
        } catch (Exception e) {
            if (AppUtils.debugging()) throw e;
        }
    }

    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(ContextUtils.<CSApplication>getApplication(contextShowDialog));

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            LogUtils.i("reponse cookie:", url.toString() + ">>cookie:" + cookies.toString());
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            LogUtils.log("Cookie", cookies.toString());
            return cookies;
        }
    }

    /**
     * 给header设置cookie
     *
     * @param client
     * @param requestBuilder
     */
    private void setCookie(OkHttpClient client, Request.Builder requestBuilder) {
        CookiesManager manager = new CookiesManager();
//        List<Cookie> cookies = client.cookieJar().loadForRequest(HttpUrl.parse(getURL()));
        List<Cookie> cookies = manager.loadForRequest(HttpUrl.parse(getURL()));
        if (cookies != null && cookies.size() > 0) {
            requestBuilder.addHeader("Cookie", cookieHeader(cookies));
        }
    }

    /**
     * 生成向请求的header中添加的cookie的键值
     *
     * @param cookies
     * @return
     */
    private String cookieHeader(List<Cookie> cookies) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            if (i > 0) {
                sb.append("; ");
            }
            Cookie cookie = cookies.get(i);
            sb.append(cookie.name()).append("=").append(cookie.value());
        }
        return sb.toString();
    }


    /**
     * 用来修改Request的方法,在匿名内部类中重写可以在enqueue之前修改Request.Builder
     *
     * @param builder okhttp3的 request builder对象
     */
    @SuppressWarnings("unused")
    protected void modifyRequest(Request.Builder builder) {
    }

    public static RequestBody toRequestBody(Map<String, String> params, Map<String, IUpload> files) {
        if (CollectionUtils.isEmpty(files)) return toRequestBody(params);
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multiBuilder.addFormDataPart(ObjectUtils.toString(entry.getKey()), ObjectUtils.toString(entry.getValue()));
        }
        if(files!=null&&!files.containsValue(null)){
            for (Map.Entry<String, IUpload> entry : files.entrySet()) {
                entry.getValue().addToBuilder(entry.getKey(), multiBuilder);
            }
        }
        return multiBuilder.build();
    }

    public static RequestBody toRequestBody(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params)) return null;
        FormBody.Builder builder = new FormBody.Builder();
        if (CollectionUtils.notEmpty(params)) {
            Iterator<Map.Entry<String, String>> i = params.entrySet().iterator();
            Map.Entry<String, String> entry;
            while (i.hasNext()) {
                entry = i.next();
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    protected boolean success;
    protected Call okCall;
    protected Response okResponse;
    protected Exception okError;
    protected RESPONSE responseEntity;

    protected void onFailure(Throwable t, Call call) {
        IOException ioException = new IOException();
        ioException.initCause(t);
        onFailure(call, ioException);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (autoControlDialog) dismissLoading(false);
        if (AppUtils.debugging()) {
            LogUtils.tableLog(
                    "netbiz",
                    "name", name,
                    "result", "onFailure",
                    "url", url,
                    "params", StringUtils.map2GetParams(params),
                    "call", call,
                    "error", e,
                    "id", id);
        }
        this.success = false;
        this.okCall = call;
        this.okError = e;
        if (getListener() != null) postResult();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (autoControlDialog) dismissLoading(true);
        if (response == null) {
            onFailure(new NullPointerException("response is null"), call);
            return;
        }
        if (AppUtils.debugging()) {
            LogUtils.tableLog("netbiz",
                    "name", name,
                    "id", id,
                    "result", "onResponse",
                    "url", url,
                    "params", StringUtils.map2GetParams(params),
                    "files", LogUtils.toString(fileMap),
                    "call", call,
                    "success", "" + response.isSuccessful() + response.code(),
                    "message", response.message(),
                    "protocol", response.protocol()
            );
            LogUtils.i("Net", response.headers().toString());
        }
        this.okCall = call;
        this.okResponse = response;
        if (getListener() == null) return;
        if (response.isSuccessful()) {
            final String body = response.body().string();
            LogUtils.log("netbiz", body);
            if (StringUtils.notEmpty(body)) {
                responseEntity = parse(body);
                if (this.responseEntity != null) {
                    this.success = true;
                    postResult();
                    return;
                }
            }
        }
        postResult();
    }

    private void postResult() {
        if (getListener() != null) {
            if (Thread.currentThread().getId() != 0) ContextUtils.mainHandler().post(this);
            else run();
        }
    }

    /**
     * 联网返回后在主线程调用, 可以重写此方法接收结果
     *
     * @param result 网络请求结果
     */
    protected void onResult(INetResult<RESPONSE> result) {
        if (getListener() != null) getListener().onNetResult(result);
    }

    @Override
    public void run() {
        try {
            onResult(this);
        } catch (Exception e) {
            if (AppUtils.debugging()) throw e;
            else onFailure(e, okCall());
        }
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public RESPONSE response() {
        return responseEntity;
    }

    @Override
    public Call okCall() {
        return okCall;
    }

    @Override
    public Response okResponse() {
        return okResponse;
    }

    @Override
    public Exception error() {
        return okError;
    }

    @Override
    public IRequest<RESPONSE> showNetLoading() {
        return this;
    }

    @Override
    public IRequest<RESPONSE> dismissLoading(boolean success) {
        return this;
    }
}