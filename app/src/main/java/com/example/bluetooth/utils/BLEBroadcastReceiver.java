package com.example.bluetooth.utils;

import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BLEBroadcastReceiver extends BroadcastReceiver {

    private boolean mConnected;
    private static final String TAG = "BLEBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d(TAG, "action:" + action);
        if (BLEUtils.ACTION_GATT_CONNECTED.equals(action)) {
            mConnected = true;
//            updateConnectionState(R.string.connected);
//            invalidateOptionsMenu();
            Toast.makeText(context, "连接成功！", Toast.LENGTH_SHORT).show();
        } else if (BLEUtils.ACTION_GATT_DISCONNECTED.equals(action)) {
            mConnected = false;
//            updateConnectionState(R.string.disconnected);
//            invalidateOptionsMenu();
//            clearUI();
        } else if (BLEUtils.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            // Show all the supported services and characteristics on the
            // user interface.
            Toast.makeText(context, "获取数据！", Toast.LENGTH_SHORT).show();
//            BluetoothGattServerCallback mBluetoothLeService = null;
            displayGattServices(BLEUtils.getInstance().getSupportedGattServices());
        } else if (BLEUtils.ACTION_DATA_AVAILABLE.equals(action)) {

//            displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
        }
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        StringBuilder sb = new StringBuilder();
        for (BluetoothGattService service : gattServices) {
            sb.append("uuid:" + service.getUuid().toString() + ",type:" + service.getType() + ",instance id:" + service.getInstanceId() + "\n");
        }
        Log.d(TAG, "result:" + sb.toString());
    }
}
