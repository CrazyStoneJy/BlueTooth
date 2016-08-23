package com.code.space.androidlib.hardware.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by shangxuebin on 16-8-19.
 */
public interface ScanListener {
    //操作成功
    int MANI_SUCCESS = 0;
    //操作失败
    int MANI_FAILED = 1;
    //重复操作，不予执行
    int MANI_REPEAT = 2;

    int SCAN_START = 1;
    int SCAN_FINISH = 2;
    int SCAN_STOP = 3;

    void onControlScanResult(int result, int type);
    void onDeviceFind(BluetoothDevice device, int rssi);
    void onDeviceFindError(int errorCode);
    void onScanStateChangeListener(int state);
}
