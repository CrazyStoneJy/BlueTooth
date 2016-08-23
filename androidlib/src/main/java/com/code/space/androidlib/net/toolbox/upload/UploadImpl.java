package com.code.space.androidlib.net.toolbox.upload;

import com.code.space.androidlib.net.IUpload;
import com.code.space.androidlib.utilities.utils.AppUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传文件的实现
 */
public class UploadImpl implements IUpload {

    String name;
    MediaType mediaType;
    File file;

    public UploadImpl() {
    }

    public UploadImpl(String fileName){
        setFile(fileName);
    }

    public UploadImpl(File file){
        this.file = file;
    }

    public String getName() {
        if(name==null && getFile()!=null)return getFile().getName();
        return name;
    }

    @Override
    public void addToBuilder(String key, MultipartBody.Builder builder) {
        if(checkLegal()) builder.addFormDataPart(key,getName(),RequestBody.create(mediaType,file));
        else {
            if(AppUtils.debugging()) throw new IllegalArgumentException("Upload file illegal "+ toString());
        }
    }

    public UploadImpl setName (String name) {
        this.name = name;
        return this;
    }

    public UploadImpl setFile(String fileName) {
        this.file = new File(fileName);
        return this;
    }

    public UploadImpl setFile(File file) {
        this.file = file;
        return this;
    }

    public File getFile() {
        return file;
    }

    public UploadImpl setMediaType(String mediaType){
        this.mediaType = MediaType.parse(mediaType);
        return this;
    }

    public UploadImpl setMediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }

    public MediaType getMediaType(){
        return mediaType;
    }

    @Override
    public boolean checkLegal() {
        return getFile()!=null&&getFile().exists()&&getMediaType()!=null;
    }

    @Override
    public String toString() {
        return "[file="+
                (file==null?"null":file.getName())+
                "; file exit="+
                (file==null?"null":file.exists())+
                "; mediatype="+
                mediaType+"]";
    }
}
