package com.yang.util.interf;

/**
 * Created by shiq_yang on 2017/6/8.
 * 蓝牙连接及数据读取接口
 */

public interface BluetoothDateCallBack {
    // 蓝牙连接状态
    void bluetoothConnectCallBack(boolean flag);

    // 数据读取
    void readBluetoothDate(byte[] bytes, boolean flag);

}
