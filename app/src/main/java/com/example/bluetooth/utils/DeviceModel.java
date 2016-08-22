package com.example.bluetooth.utils;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DeviceModel {

    private String name;
    private String address;
    private BluetoothDevice device;

    public String getName() {
        return name;
    }

    public DeviceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DeviceModel setAddress(String address) {
        this.address = address;
        return this;
    }


    public BluetoothDevice getDevice() {
        return device;
    }

    public DeviceModel setDevice(BluetoothDevice device) {
        this.device = device;
        return this;
    }
}
