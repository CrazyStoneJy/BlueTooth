package com.example.bluetooth.utils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BlueToothBroadcastReceiver extends BroadcastReceiver {

    private List<DeviceModel> mList = new ArrayList<>();
    public static final String MAC_ADDRESS = "A0:E6:F8:4F:C0:15";
    private Handler mHandler;
    private Activity mActivity;

    public BlueToothBroadcastReceiver(Activity activity, Handler handler) {
        this.mHandler = handler;
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (!mList.contains(device)) {
                DeviceModel model = new DeviceModel().setName(device.getName()).setAddress(device.getAddress()).setDevice(device);
                mList.add(model);
                if (model.getAddress().equals(MAC_ADDRESS)) {
                    BlueToothUtils.getInstance().pair(device.getAddress(),device);
                }
            }
            Log.d("TAG", "discovery device:" + device.getName() + "\n" + device.getAddress());
        }
    }
}
