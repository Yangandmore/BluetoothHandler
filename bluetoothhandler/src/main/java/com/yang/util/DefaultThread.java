package com.yang.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by yang on 2017/6/6.
 * 对内线程，读取蓝牙数据
 */

class DefaultThread extends Thread {

    private BluetoothDevice device;
    private BluetoothSocket bluetoothSocket;
    private InputStream is;
    private OutputStream os;
    private boolean flag = true;

    public DefaultThread(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public void run(){
        // 暂停蓝牙搜索
        BluetoothUtil.closeSearchBluetooth();

        // 开启蓝牙连接
        if (bluetoothSocket == null) {
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(DefaultGlobalConstants.UUID_SPP);
                bluetoothSocket.connect();
            } catch (IOException e) {
                try {
                    bluetoothSocket = (BluetoothSocket) device
                            .getClass()
                            .getMethod("createRfcommSocket",
                                    new Class[] { int.class }).invoke(device, 1);
                    bluetoothSocket.connect();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        //判断蓝牙连接是否成功
            if (bluetoothSocket.isConnected()) {
                if (flag) {
                    // 蓝牙配对成功
                    Message message = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_SUCCESS);
                    DefaultGlobalConstants.defaultHandler.sendMessage(message);
                    flag = false;
                }
            } else {
                if (flag) {
                    // 蓝牙配对失败
                    Message message = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_FAIL);
                    DefaultGlobalConstants.defaultHandler.sendMessage(message);
                    flag = false;
                    return ;
                }
            }

            // 读取数据
            try {
                is = bluetoothSocket.getInputStream();
                os = bluetoothSocket.getOutputStream();
                int l = 0;
                byte[] buf = new byte[2048];
                while (true) {
                    if (bluetoothSocket.isConnected()) {
                        // 读取数据
                        l = is.read(buf);
                        if (l > 0) {
                            byte[] result = Arrays.copyOf(buf, l);
                            Message message = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_READ);
                            message.obj = result;
                            DefaultGlobalConstants.defaultHandler.sendMessage(message);
                        }

                    } else {
                        // 连接断开
                        Message message = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_BREAK);
                        DefaultGlobalConstants.defaultHandler.sendMessage(message);
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
                BluetoothUtil.close();
            }
        }

    }

    // 写内容
    public void write(String string) throws IOException {
        // 转16进制写
        byte[] bytes = DefaultBluetoothUtil.hexStringToBytes(string);
        os.write(bytes);
    }

    // 关闭线程
    public void close() {
        if (bluetoothSocket != null)
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (is != null)
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (os != null)
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
