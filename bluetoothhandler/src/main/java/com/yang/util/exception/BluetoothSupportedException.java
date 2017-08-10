package com.yang.util.exception;

/**
 * Created by shiq_yang on 2017/8/10.
 * 设备是否支持蓝牙
 */

public class BluetoothSupportedException extends Exception {

    public BluetoothSupportedException() {
        super("设备不支持蓝牙");
    }

}
