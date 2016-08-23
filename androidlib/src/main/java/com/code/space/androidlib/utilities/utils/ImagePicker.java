package com.code.space.androidlib.utilities.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.code.space.androidlib.utilities.ICheckLegal;

import java.io.File;

import static com.code.space.androidlib.utilities.utils.Image.CAMERA;
import static com.code.space.androidlib.utilities.utils.Image.GALLERY;

/**
 * Created by shangxuebin on 16-6-18.
 */
public class ImagePicker implements ICheckLegal{


    public final int REQUEST_CODE = hashCode();

    protected boolean clip;

    public static final String JPG = "jpg";
    public static final String PNG = "png";
    public static final String BMP = "bmp";
    public static final String GIF = "gif";

    protected String fileName;
    protected String fileType = JPG;

    protected int from;

    protected boolean success;

    protected Activity activity;

    protected Uri imageUri;

    public ImagePicker(Activity activity) {
        this.activity = activity;
    }


    public void pickImage(int from){
        this.from = from;
        switch (from){
            case CAMERA:
                break;
            case GALLERY:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        success = requestCode==REQUEST_CODE;
        if(success){

        }
    }

    protected String getFileName(){
        return fileName+"."+fileType;
    }

    protected void camera() throws ImagePickerException {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, getFileName());
            imageUri = activity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            beforeActivityStart(from);
            activity.startActivityForResult(intent, REQUEST_CODE);
        }else {
            ex("请确认已经插入SD卡");
        }
    }


    @Override
    public boolean checkLegal() {
        return success;
    }

    public void ex(String info) throws ImagePickerException{
        ImagePickerException ex = new ImagePickerException();
        ex.errorInfo = info;
        throw ex;
    }

    public void beforeActivityStart(int to){

    }

    public static class ImagePickerException extends Exception{

        protected String errorInfo;

        public String errorInfo(){
            return errorInfo;
        }
    }
}
