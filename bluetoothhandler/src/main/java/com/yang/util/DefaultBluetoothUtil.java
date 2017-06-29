package com.yang.util;

import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/**
 * Created by shiq_yang on 2017/6/8.
 * 内部帮助类
 */

public class DefaultBluetoothUtil {

    // 设备是否支持蓝牙
    public static boolean isBluetoothSupported() {
        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
    }

    // 蓝牙是否开启
    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }

        return false;
    }

    // 蓝牙断言
    public static boolean isBluetootoReadly() {
        // 是否支持蓝牙
        if (DefaultBluetoothUtil.isBluetoothSupported()) {
            DefaultGlobalConstants.bluetoothSupported = true;
            // 是否打开蓝牙
            if (DefaultBluetoothUtil.isBluetoothEnabled()) {
                DefaultGlobalConstants.bluetoothSwitch = true;
            } else {
                DefaultGlobalConstants.bluetoothSwitch = false;
            }
        } else {
            DefaultGlobalConstants.bluetoothSupported = false;
            Toast.makeText(DefaultGlobalConstants.context, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
        }
        if (DefaultGlobalConstants.bluetoothSupported && DefaultGlobalConstants.bluetoothSwitch)
            return true;
        else
            return false;
    }

    // 得到蓝牙适配器
    public static BluetoothAdapter getBluetoothAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }
    /**
     * 将16进制数据字符串转换成数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            d[i] = (byte) Integer.parseInt(d[i]+"", 10);
        }
        return d;
    }

    /**
     * 16进制查找
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
