package com.code.space.androidlib.hardware.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by shangxuebin on 16-8-19.
 */
public class BluetoothConnecter implements Runnable{
    private BluetoothSocket mSocket;

    //蓝牙串口服务
    public static final String SerialPortServiceClass_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private boolean flagRead;

    private ConnectListener mConnectListener;

    private byte[] buf;
    private int readLength;
    private StringBuilder mStringBuilder;
    private Handler mHandler;


    public BluetoothConnecter(BluetoothDevice device) throws BluetoothConnectException{
        // 固定的UUID
        try {
            UUID uuid = UUID.fromString(SerialPortServiceClass_UUID);
            mSocket = device.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    if(mConnectListener!=null)mConnectListener.onBluetoothReceive((String) msg.obj);
                }
            };
            flagRead = true;
            buf = new byte[256];
            mStringBuilder = new StringBuilder();
            new Thread(this).start();
        }catch (Exception e){
            throw new BluetoothConnectException();
        }
    }

    public void setConnectListener(ConnectListener listener){
        this.mConnectListener = listener;
    }

    public void send(byte[] data){
        try {
            mSocket.getOutputStream().write(data);
            mSocket.getOutputStream().flush();
        }catch (Exception e){

        }
    }

    public void stop(){
        flagRead = false;
        try{
            mSocket.close();
        }catch (Exception e){
        }
    }


    public void run(){
        while (flagRead){
            try{
                while((readLength = mSocket.getInputStream().read(buf))>0){
                    mStringBuilder.append(new String(buf,0,readLength));
                }
                if(mConnectListener!=null) mHandler.sendMessage(mHandler.obtainMessage(1,mStringBuilder.toString()));
            }catch (Exception e){

            }
        }
    }
}
