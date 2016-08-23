package com.code.space.androidlib.hardware.bluetooth;

/**
 * Created by shangxuebin on 16-8-19.
 */
public interface OpenBluetoothCallback {
    //打开成功
    int RESULT_OK = 0;
    //用户取消
    int RESULT_CANCEL = 1;
    //直接打开失败，让用户自行去设置页面打开
    int RESULT_USER = 2;
    //本身蓝牙就是开着的
    int ALREADY_OPENNED = 3;
    void openBluetoothResult(int result);
}
