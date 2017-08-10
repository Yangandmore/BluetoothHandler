package com.yang.util.exception;

/**
 * Created by shiq_yang on 2017/7/4.
 * 蓝牙未打开
 */

public class BluetoothSwitchCloseException extends Exception {

    public BluetoothSwitchCloseException() {
        super("蓝牙未打开");
    }

}
