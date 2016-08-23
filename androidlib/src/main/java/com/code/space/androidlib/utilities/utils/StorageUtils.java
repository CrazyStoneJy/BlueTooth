package com.code.space.androidlib.utilities.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * SD 存储
 * Created by crazystone on 2016/6/2.
 */
public class StorageUtils {

    /* 相片路径  */
    public static final String IMAGE_PATH = "photo";

    private static final String TAG = "StorageUtils";

    /**
     * 判断是否SD card挂载，并是否有对sd card的读写权限
     *
     * @return
     */
    public static boolean isSDCardMOUNT() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * sd card 是否可读
     *
     * @return
     */
    public static boolean isSDCardCanRead() {
        String state = Environment.getExternalStorageState();
        return (state.equals(Environment.MEDIA_MOUNTED) && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY));
    }

    /**
     * 返回文件名为时间戳的jpg文件
     *
     * @param name 文件夹的名称
     * @return file 返回文件名为时间戳的jpg文件
     */
    public static File getImageStorageFile(String name) {
        if (!isSDCardMOUNT()) return null;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name == null ? IMAGE_PATH : name);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                LogUtils.log(TAG, "file make fail");
            }
        }

        StringBuilder outputPath = new StringBuilder();
        outputPath.append(file.getAbsolutePath()).append(File.separator).append("img_").append(System.currentTimeMillis()).append(".").append("jpg");
        File output = new File(outputPath.toString());
        return output;
    }

    /**
     * 通过文件路径获取bitmap
     *
     * @param filePath bitmap在sdcard的文件路径
     * @return
     */
    public static Bitmap getBitmap(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

}
