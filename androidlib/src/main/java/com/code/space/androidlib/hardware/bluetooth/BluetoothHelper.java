package com.code.space.androidlib.hardware.bluetooth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;

import com.code.space.androidlib.utilities.utils.AppUtils;

import java.util.List;

/**
 * Created by shangxuebin on 16-8-19.
 */
public class BluetoothHelper {

    private static final int REQUEST_CODE_OPEN_BLUETOOTH = "REQUEST_CODE_OPEN_BLUETOOTH".length();

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBlueToothLeScanncer;
    private OpenBluetoothCallback openCallback;
    private boolean flagScannerIdle;
    private Activity activity;
    private ScanCallback api21ScanCallback;
    private BluetoothAdapter.LeScanCallback api18LeScanCallback;
    private BroadcastReceiver api1downResultReceiver;
    private Handler resultHandler;
    private ScanListener mScanListener;
    private static final int SCAN_FIND_DEVICE = 1;
    private static final int SCAN_ERROR_OCCUR = 2;




    public BluetoothHelper(Activity context) throws BluetoothInitException {
        mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            throw new BluetoothInitException();
        }
        this.activity = context;
        if (AppUtils.checkAPI(21)) {
            mBlueToothLeScanncer = mBluetoothAdapter.getBluetoothLeScanner();
            api21ScanCallback = new ScanCallback() {
                @TargetApi(21)
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    if (result == null) return;
                    onDeviceFind(result.getDevice(), result.getRssi());
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    for (ScanResult result : results) {
                        this.onScanResult(-1, result);
                    }
                }

                @Override
                public void onScanFailed(int errorCode) {
                    resultHandler.sendMessage(resultHandler.obtainMessage(SCAN_ERROR_OCCUR, errorCode, 1));
                }
            };
        } else if (AppUtils.checkAPI(18)) {
            api18LeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    onDeviceFind(device, rssi);
                }
            };
        } else {
            api1downResultReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    onDeviceFind(device, -1);
                }
            };
            IntentFilter filterDeviceFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            IntentFilter filterScanFinish = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            context.registerReceiver(api1downResultReceiver, filterDeviceFound);
            context.registerReceiver(api1downResultReceiver, filterScanFinish);
        }
        resultHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(mScanListener==null) return;
                switch (msg.what) {
                    case SCAN_FIND_DEVICE:
                        mScanListener.onDeviceFind((BluetoothDevice) msg.obj, msg.arg1);
                        break;
                    case SCAN_ERROR_OCCUR:
                        mScanListener.onDeviceFindError(msg.arg1);
                        break;
                }

            }
        };
    }

    private void onDeviceFind(BluetoothDevice device, int rssi) {
        if (device == null) return;
        resultHandler.sendMessage(resultHandler.obtainMessage(SCAN_FIND_DEVICE, rssi, 1, device));
    }

    public void setScanListener(ScanListener listener) {
        this.mScanListener = listener;
    }

    public boolean available() {
        return mBluetoothAdapter != null;
    }

    public boolean isOpen() {
        return mBluetoothAdapter.isEnabled();
    }

    public boolean stateIdle(){
        return flagScannerIdle;
    }


    public void openBluetooth(OpenBluetoothCallback callback) {
        if (isOpen()) {
            callback.openBluetoothResult(OpenBluetoothCallback.ALREADY_OPENNED);
        }
        this.openCallback = callback;
        try {//尝试直接打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, REQUEST_CODE_OPEN_BLUETOOTH);
        } catch (Exception e) {
            try {//尝试打开蓝牙设置界面
                activity.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                openCallback.openBluetoothResult(OpenBluetoothCallback.RESULT_USER);
                openCallback = null;
            } catch (Exception e2) {

            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPEN_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                openCallback.openBluetoothResult(OpenBluetoothCallback.RESULT_OK);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                openCallback.openBluetoothResult(OpenBluetoothCallback.RESULT_CANCEL);
            }
            openCallback = null;
        }
    }

    public void startScan() {
        if (mScanListener == null) {
            if (AppUtils.debugging()) throw new NullPointerException("Have not set ScanListener");
            return;
        }
        //正在扫描
        if (!flagScannerIdle) {
            this.mScanListener.onControlScanResult(ScanListener.MANI_REPEAT, ScanListener.SCAN_START);
            return;
        }
        boolean success = false;
        if (AppUtils.checkAPI(21)) {
            try {
                mBlueToothLeScanncer.startScan(api21ScanCallback);
                success = true;
            } catch (Exception e) {
                success = false;
            }
        } else if (AppUtils.checkAPI(18)) {
            try {
                success = mBluetoothAdapter.startLeScan(api18LeScanCallback);
            } catch (Exception e) {
                success = false;
            }
        } else {
            success = mBluetoothAdapter.startDiscovery();
        }
        if (success) flagScannerIdle = false;
        this.mScanListener.onControlScanResult(success ? ScanListener.MANI_SUCCESS : ScanListener.MANI_FAILED, ScanListener.SCAN_START);
    }

    public void stopScan() {
        if (mScanListener == null) {
            if (AppUtils.debugging()) throw new NullPointerException("Have not set ScanListener");
            return;
        }
        //正在扫描
        if (!flagScannerIdle) {
            this.mScanListener.onControlScanResult(ScanListener.MANI_REPEAT, ScanListener.SCAN_START);
            return;
        }
        boolean success = false;
        if (AppUtils.checkAPI(21)) {
            try {
                mBlueToothLeScanncer.stopScan(api21ScanCallback);
                success = true;
            } catch (Exception e) {
                success = false;
            }
        } else if (AppUtils.checkAPI(18)) {
            try {
                mBluetoothAdapter.stopLeScan(api18LeScanCallback);
                success = true;
            } catch (Exception e) {
                success = false;
            }
        } else {
            success = mBluetoothAdapter.cancelDiscovery();
        }
        if (success) flagScannerIdle = true;
        this.mScanListener.onControlScanResult(success ? ScanListener.MANI_SUCCESS : ScanListener.MANI_FAILED, ScanListener.SCAN_STOP);
    }




}
