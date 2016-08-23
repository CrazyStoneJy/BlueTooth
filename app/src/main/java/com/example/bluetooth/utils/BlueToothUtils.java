package com.example.bluetooth.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.example.bluetooth.ui.scan.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BlueToothUtils {

    public static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mAdapter;
    private static final String TAG = "BlueToothUtils";
    public static BlueToothBroadcastReceiver mReceiver;
    /* 控制线程的线程池  */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private volatile static BlueToothUtils mUtils;
    /*蓝牙连接后的socket */
    private BluetoothSocket mScoket = null;
    /* 从socket中获取的输入流 */
    private InputStream mInputStream;
    /* 从socket中获取的输出流 */
    private OutputStream mOutputStream;
    private static final String SPP_UUID = "43A68BA0-63A5-609C-9ACF-E1A97DF17F40";
//    43A68BA0-63A5-609C-9ACF-E1A97DF17F40
//    00001101-0000-1000-8000-00805f9b34fb

    private BlueToothUtils() {
    }

    public static BlueToothUtils getInstance() {
        if (mUtils == null) {
            synchronized (BlueToothUtils.class) {
                if (mUtils == null) {
                    mUtils = new BlueToothUtils();
                }
            }
        }
        return mUtils;
    }


    /**
     * 查看蓝牙设置是否打开，若果没有打开，则请求用户打开
     *
     * @param activity
     */
    public void openBlueTooth(Activity activity) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                sacnDevice(activity);
            }
        } else {
            Toast.makeText(activity, "该手机没有蓝牙功能！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 扫描设备，（以前绑定过的设备，和新搜索的设备）
     */
    public void sacnDevice(Activity activity) {
        if (mAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
            mAdapter.startDiscovery();
            // Register the BlueToothBroadcastReceiver
            MainActivity ref = null;
            if (activity instanceof MainActivity) {
                ref = (MainActivity) activity;
            }
            if (mReceiver == null)
                mReceiver = new BlueToothBroadcastReceiver(activity, ref != null ? ref.getHandler() : null);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            activity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    Log.d(TAG, "already bound device:" + device.getName() + "\n" + device.getAddress());
                }
            }
        }
    }


    public void pair(String macAddress, BluetoothDevice device) {
        // 获取蓝牙设备的连接状态
        int connectState = device.getBondState();
        switch (connectState) {
            // 未配对
            case BluetoothDevice.BOND_NONE:
                // 配对
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    Boolean reultValue = (Boolean) createBondMethod.invoke(device);
                    Log.d(TAG, ">>> pairing:" + reultValue.booleanValue());
//                    connect(macAddress, device);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // 已配对
            case BluetoothDevice.BOND_BONDED:
                // 连接
                Log.d(TAG, ">>>paired");
                connect(macAddress, device);
                break;
        }
    }

    private void getDeviceUUID(BluetoothDevice device) {
        // for apiVer >= 15
        ParcelUuid[] supportedUuids = device.getUuids();
// for apiVer < 15
        try {
            Class cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class[] params = {};
            Method method = cl.getMethod("getUuids", params);
            Object[] args = {};
            supportedUuids = (ParcelUuid[]) method.invoke(device, args);
        } catch (Exception e) {
            // no op
            Log.e("uuids", "Activation of getUuids() via reflection failed: " + e);
        }
        Log.d(TAG, "uuids:" + supportedUuids != null ? supportedUuids.toString() : "");
    }


    /**
     * 将自己的手机作为client端来连接其他设备
     *
     * @param device 其他设备（即搜索到的设备）
     */
    public void connect(String macAddress, BluetoothDevice device) {
        try {
            UUID uuid = UUID.fromString(SPP_UUID);
//            mScoket = device.createRfcommSocketToServiceRecord(uuid);
            Log.d(TAG, device.getName() + ">>>>>prepare to connect,device bind is " + device.getBondState());
//            getDeviceUUID(device);
            try {
                mScoket = device.createRfcommSocketToServiceRecord(uuid);
                Method m = null;
                m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                mScoket = (BluetoothSocket) m.invoke(device, 1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    //将搜索蓝牙的异步线程关闭
                    if (mAdapter != null && mAdapter.isDiscovering()) mAdapter.cancelDiscovery();
                    try {
                        if (!mScoket.isConnected()) {
                            mScoket.connect();
                            manageConnectSocket();
                        }
                        Log.d(TAG, "device is already connected");
                    } catch (IOException e) {
                        e.printStackTrace();
                        close();
                    } finally {
                        close();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * 管理蓝牙的输出输入
     */
    private void manageConnectSocket() {
        if (mScoket != null) {
            try {
                mInputStream = mScoket.getInputStream();
                mOutputStream = mScoket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        read();
                    }
                }
            });
        }
    }


    private void read() {
        if (mInputStream != null) {
            Log.d(TAG, "read data from socket");
            int len = 0;
            byte[] buffer = new byte[1024];  // buffer store for the stream
//            StringBuilder sb = new StringBuilder();
            try {
                while ((len = mInputStream.read(buffer)) != -1) {
                    write(0, len, buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeInputStream();
            } finally {
                closeInputStream();
                closeOutputStream();
            }
        }
    }

    /**
     * 向外写
     *
     * @param bytes
     */
    public void write(int start, int end, byte[] bytes) {
        if (mOutputStream != null) {
            Log.d(TAG, "write data ");
            StringBuilder sb = null;
            try {
                sb = new StringBuilder();
                sb.append(new String(bytes, start, end, "utf-8"));
                mOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                closeOutputStream();
            }
            Log.d(TAG, sb.toString());
        }
    }

    private void closeOutputStream() {
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeInputStream() {
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭socket连接
     */
    private void close() {
        if (mScoket != null) {
            try {
                mScoket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
