package com.yang.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yang.util.interf.BluetoothDateCallBack;
import com.yang.util.interf.BluetoothSearchCallBack;
import com.yang.util.interf.BluetoothSwitchCallBack;

/**
 * Created by yang on 2017/6/6.
 * 对内Handler，接收蓝牙的数据
 */

class DefaultHandler extends Handler{

    BluetoothSearchCallBack bluetoothSDKSearchCallBack;

    BluetoothDateCallBack bluetoothDateCallBack;

    BluetoothSwitchCallBack bluetoothSwitchCallBack;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int what = msg.what;
        Log.e("what:", ":"+what);
        switch (what) {
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_SWITCH_OPEN:
                // 蓝牙打开 刷新
                if (bluetoothSwitchCallBack != null)
                    bluetoothSwitchCallBack.bluetoothSwitch(true);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_SWITCH_CLOSE:
                // 蓝牙关闭 保护
                if (bluetoothDateCallBack != null)
                    bluetoothDateCallBack.readBluetoothDate(new byte[]{}, false);
                BluetoothUtil.close();
                if (bluetoothSwitchCallBack != null)
                    bluetoothSwitchCallBack.bluetoothSwitch(false);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_SEARCH_OPEN:
                // 搜索开始
                BlueInfo blueInfo = (BlueInfo) msg.obj;
                if (bluetoothSDKSearchCallBack != null)
                    bluetoothSDKSearchCallBack.searchStateAndDate(true, blueInfo);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_SEARCH_CLOSE:
                // 搜索关闭
                if (bluetoothSDKSearchCallBack != null)
                    bluetoothSDKSearchCallBack.searchStateAndDate(false, new BlueInfo());
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_SUCCESS:
                // 连接成功
                if (bluetoothDateCallBack != null)
                    bluetoothDateCallBack.bluetoothConnectCallBack(true);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_FAIL:
                // 连接失败
                if (bluetoothDateCallBack != null)
                    bluetoothDateCallBack.bluetoothConnectCallBack(false);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_BREAK:
                // 连接断开
                if (bluetoothDateCallBack != null)
                    bluetoothDateCallBack.readBluetoothDate(new byte[]{}, false);
                break;
            case DefaultGlobalConstants.HandlerState.BLUETOOTH_CONNECT_READ:
                // 读取数据
                byte[] bytes = (byte[]) msg.obj;
                if (bluetoothDateCallBack != null)
                    bluetoothDateCallBack.readBluetoothDate(bytes, true);
                break;
        }

    }

    public void setBluetoothSearchCallBack(BluetoothSearchCallBack bluetoothSDKSearchCallBack) {
        this.bluetoothSDKSearchCallBack = bluetoothSDKSearchCallBack;
    }

    public void setBluetoothDateCallBack(BluetoothDateCallBack bluetoothDateCallBack) {
        this.bluetoothDateCallBack = bluetoothDateCallBack;
    }

    public void setBluetoothSwitchCallBack(BluetoothSwitchCallBack bluetoothSwitchCallBack) {
        this.bluetoothSwitchCallBack = bluetoothSwitchCallBack;
    }

}