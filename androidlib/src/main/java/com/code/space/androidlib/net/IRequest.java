package com.code.space.androidlib.net;

import android.content.Context;

import com.code.space.androidlib.net.toolbox.SimpleRequestBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.Callback;

/**
 * 构建网络请求request
 */
public interface IRequest<RESPONSE> extends Callback{

    /**
     *
     * @param url 请求地址
     * @return  this
     */
    IRequest<RESPONSE> url(String url);

    /**
     * key-value参数
     * @param params 键值对map
     * @return
     */
    IRequest<RESPONSE> params(Map<String,String> params);

    /**
     * 文件key-file
     * @param files 要上传的文件
     * @see com.code.space.androidlib.net.toolbox.upload.UploadImpl
     * @return
     */
    IRequest<RESPONSE> files(Map<String,IUpload> files);

    /**
     * 连接方式
     * @see Method
     * @param method
     * @return
     */
    IRequest<RESPONSE> method(int method);

    /**
     * 当前连接的id
     * @param id
     * @return
     */
    IRequest<RESPONSE> id(int id);

    /**
     * 用来自动控制显示联网loading dialog的context对象, 设置后开始连接自动显示, 联网结束后自动消除
     * @param context
     * @return
     */
    IRequest<RESPONSE> showDialog(Context context);

    /**
     * 回调接口
     * 如果是可遗弃请求（举个例子，某个fragment中的网络请求，activity回收后返回结果也没用了，还可能出错，使用LeakResponseListener）
     * @see com.code.space.androidlib.net.toolbox.LeakResponseListener
     * new LeakResponseListener<Entity>(new ResponseListener<Entity>(){
     *     public void void onNetResult(INetResult<RESPONSE> result){
     *         do something
     *     }
     * });
     * @param listener
     * @return
     */
    IRequest<RESPONSE> listener(ResponseListener<RESPONSE> listener);

    /**
     * 用来自动解析的class对象,不设置的话又需要自动解析会使用FastJson的TypeReference, 效率会低一些
     * @see com.alibaba.fastjson.TypeReference
     * @param clazz
     * @return
     */
    IRequest<RESPONSE> clazz(Class<RESPONSE> clazz);

    /**
     * 本次连接的名字, 用来打印log的, 对程序无实际用处
     * @param name
     * @return
     */
    IRequest<RESPONSE> name(String name);

    /**
     * 添加一个key-value
     * @param key
     * @param value
     * @return
     */
    IRequest<RESPONSE> addParam(String key, Object value);

    SimpleRequestBuilder<RESPONSE> addParam(String key, List<?> list);

    /**
     * 添加一个key-file
     * @param key
     * @param file
     * @return
     */
    IRequest<RESPONSE> addFile(String key, IUpload file);

    /**
     * 开始连接
     */
    void connect();

    /**
     * 设置context后会使用这个函数显示dialog
     * @return
     */
    IRequest<RESPONSE> showNetLoading();

    /**
     * 设置context后会使用这个函数消除dialog
     * @param success
     * @return
     */
    IRequest<RESPONSE> dismissLoading(boolean success);
}
