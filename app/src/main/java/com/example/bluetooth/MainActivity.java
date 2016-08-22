package com.example.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluetooth.utils.BLEUtils;
import com.example.bluetooth.utils.BlueToothUtils;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_scan;
    Button btn_connect_mac;
    Button btn_disconnect;
    RecyclerView mRecylervIew;
    private SoftReference<BlueToothHandler> mSoftHandler;
    private BlueToothHandler mHandler;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new BlueToothHandler();
        mSoftHandler = new SoftReference<>(mHandler);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_connect_mac = (Button) findViewById(R.id.btn_connect_with_mac_address);
        btn_disconnect = (Button) findViewById(R.id.btn_disconnect);
//        mRecylervIew = (RecyclerView) findViewById(R.id.recylerview);
        btn_scan.setOnClickListener(this);
        btn_connect_mac.setOnClickListener(this);
        BLEUtils.getInstance().bindActivity(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                BLEUtils.getInstance().openBlueTooth(this, getHandler());
                break;
            case R.id.btn_connect_with_mac_address:
                BLEUtils.getInstance().openBlueTooth(this, null);
                break;
            case R.id.btn_disconnect:
                BLEUtils.getInstance().close();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BlueToothUtils.REQUEST_ENABLE_BT:
                switch (resultCode) {
                    case RESULT_OK:
                        Toast.makeText(MainActivity.this, "蓝牙打开成功!", Toast.LENGTH_SHORT).show();
//                        BlueToothUtils.getInstance().sacnDevice(MainActivity.this);
                        BLEUtils.getInstance().scan(true, getHandler());
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "蓝牙打开失败!", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BlueToothUtils.mReceiver != null)
            unregisterReceiver(BlueToothUtils.mReceiver);
        BLEUtils.getInstance().close();
    }


    public class BlueToothHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public BlueToothHandler getHandler() {
        return mSoftHandler.get();
    }

//    // Demonstrates how to iterate through the supported GATT
//    // Services/Characteristics.
//    // In this sample, we populate the data structure that is bound to the
//    // ExpandableListView on the UI.
//    private void displayGattServices(List<BluetoothGattService> gattServices) {
//        if (gattServices == null) return;
//        String uuid = null;
//        String unknownServiceString = "";
//        String unknownCharaString = "";
////        ArrayList<HashMap<String, String>> gattServiceData =
////                new ArrayList<HashMap<String, String>>();
////        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
////                = new ArrayList<ArrayList<HashMap<String, String>>>();
////        mGattCharacteristics =
////                new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
//
//        // Loops through available GATT Services.
//        for (BluetoothGattService gattService : gattServices) {
//            HashMap<String, String> currentServiceData =
//                    new HashMap<String, String>();
//            uuid = gattService.getUuid().toString();
//            currentServiceData.put(
//                    LIST_NAME, SampleGattAttributes.
//                            lookup(uuid, unknownServiceString));
//            currentServiceData.put(LIST_UUID, uuid);
//            gattServiceData.add(currentServiceData);
//
//            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
//                    new ArrayList<HashMap<String, String>>();
//            List<BluetoothGattCharacteristic> gattCharacteristics =
//                    gattService.getCharacteristics();
//            ArrayList<BluetoothGattCharacteristic> charas =
//                    new ArrayList<BluetoothGattCharacteristic>();
//            // Loops through available Characteristics.
//            for (BluetoothGattCharacteristic gattCharacteristic :
//                    gattCharacteristics) {
//                charas.add(gattCharacteristic);
//                HashMap<String, String> currentCharaData =
//                        new HashMap<String, String>();
//                uuid = gattCharacteristic.getUuid().toString();
//                currentCharaData.put(
//                        LIST_NAME, SampleGattAttributes.lookup(uuid,
//                                unknownCharaString));
//                currentCharaData.put(LIST_UUID, uuid);
//                gattCharacteristicGroupData.add(currentCharaData);
//            }
////            mGattCharacteristics.add(charas);
////            gattCharacteristicData.add(gattCharacteristicGroupData);
//        }
//    }


}
