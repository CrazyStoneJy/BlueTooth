package com.code.space.androidlib.hardware.bluetooth;

/**
 * Created by shangxuebin on 16-8-19.
 */
public interface ConnectListener {
    void errorOccur();
    void connectBreak();
    void onBluetoothReceive(String msg);

}
