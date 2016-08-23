package com.example.bluetooth.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluetooth.R;
import com.example.bluetooth.base.BaseActivity;
import com.example.bluetooth.utils.BLEUtils;
import com.example.bluetooth.utils.BlueToothUtils;
import com.google.zxing.activity.CaptureActivity;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btn_scan;
    Button btn_scan_q_code;
    Button btn_disconnect;
    Button txt_connect_mac;
    RecyclerView mRecylervIew;
    private SoftReference<BlueToothHandler> mSoftHandler;
    private BlueToothHandler mHandler;
    public static final int REQUEST_CODE_SCANNER_Q_CODE = 0x100;
    public static final int RESULT_CODE = REQUEST_CODE_SCANNER_Q_CODE + 1;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new BlueToothHandler();
        mSoftHandler = new SoftReference<>(mHandler);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan_q_code = (Button) findViewById(R.id.btn_san_q_code);
        btn_disconnect = (Button) findViewById(R.id.btn_disconnect);
        txt_connect_mac = (Button) findViewById(R.id.txt_connect_by_mac);
//        mRecylervIew = (RecyclerView) findViewById(R.id.recylerview);
        btn_scan.setOnClickListener(this);
        btn_scan_q_code.setOnClickListener(this);
        btn_disconnect.setOnClickListener(this);
        txt_connect_mac.setOnClickListener(this);
        BLEUtils.getInstance().initBlueToothAdapter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                BLEUtils.getInstance().openBlueTooth(this);
                BLEUtils.getInstance().scan(true, getHandler());
                break;
            case R.id.btn_san_q_code:
                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE_SCANNER_Q_CODE);
//                BLEUtils.getInstance().openBlueTooth(this, null);
                break;
            case R.id.btn_disconnect:
                BLEUtils.getInstance().close();
                break;
            case R.id.txt_connect_by_mac:
                String macAddress = "A0:E6:F8:4F:C0:15";
                connectWithMac(macAddress);
                break;
        }
    }

    /**
     * 通过macaddress连接设备
     *
     * @param macAddress
     */
    private void connectWithMac(String macAddress) {
        Toast.makeText(MainActivity.this, macAddress, Toast.LENGTH_SHORT).show();
        BLEUtils.getInstance().openBlueTooth(this);
        BLEUtils.getInstance().connect(BLEUtils.getInstance().getDeviceByMacAddress(macAddress));
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
            case REQUEST_CODE_SCANNER_Q_CODE:
                if (resultCode == RESULT_CODE) {
                    if (data != null) {
                        String macAddress = data.getStringExtra("data");
                        Log.d(TAG, "Q code:" + macAddress);
                        connectWithMac(macAddress);
                    }
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


}
