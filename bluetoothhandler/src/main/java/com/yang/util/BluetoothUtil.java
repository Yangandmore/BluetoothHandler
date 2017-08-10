package com.yang.util;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import com.yang.util.exception.BluetoothSupportedException;
import com.yang.util.exception.BluetoothSwitchCloseException;
import com.yang.util.exception.InputIncompleteException;
import com.yang.util.interf.BluetoothDateCallBack;
import com.yang.util.interf.BluetoothSearchCallBack;
import com.yang.util.interf.BluetoothSwitchCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by shiq_yang on 2017/6/8.
 * 对外提供的功能接口
 */

public class BluetoothUtil {

    // 监听蓝牙开关
    public static void listenerBluetoothSwitch(BluetoothSwitchCallBack bluetoothSwitchCallBack) {
        DefaultGlobalConstants.defaultHandler.setBluetoothSwitchCallBack(bluetoothSwitchCallBack);
    }

    // 得到蓝牙一配对过的对象
    public static List<BlueInfo> getBondedDevices() throws BluetoothSupportedException, BluetoothSwitchCloseException {
        List<BlueInfo> list = new ArrayList<BlueInfo>();

        // 确保打开蓝牙功能
        // 设备蓝牙是否支持
        if (!DefaultBluetoothUtil.isBluetoothSupported())
            throw new BluetoothSupportedException();

        // 蓝牙功能未打开
        if (!DefaultBluetoothUtil.isBluetoothEnabled())
            throw new BluetoothSwitchCloseException();

        Set<BluetoothDevice> pairedDevices = DefaultBluetoothUtil
                .getBluetoothAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            BlueInfo info = null;
            for (BluetoothDevice device : pairedDevices) {
                info = new BlueInfo();
                info.name = TextUtils.isEmpty(device.getName()) ? "未知蓝牙"
                        : device.getName();
                info.address = device.getAddress();
                list.add(info);
            }
        }

        return list;
    }

    // 打开蓝牙搜索
    public static void openSearchBluetooth(BluetoothSearchCallBack bluetoothSDKSearchCallBack) throws BluetoothSwitchCloseException, BluetoothSupportedException {
        // 设备蓝牙是否支持
        if (!DefaultBluetoothUtil.isBluetoothSupported())
            throw new BluetoothSupportedException();

        // 蓝牙功能未打开
        if (!DefaultBluetoothUtil.isBluetoothEnabled())
            throw new BluetoothSwitchCloseException();
        if (DefaultBluetoothUtil.getBluetoothAdapter().isDiscovering())
            closeSearchBluetooth();
        DefaultBluetoothUtil.getBluetoothAdapter().startDiscovery();
        DefaultGlobalConstants.defaultHandler.setBluetoothSearchCallBack(bluetoothSDKSearchCallBack);
    }

    // 关闭蓝牙搜索
    public static void closeSearchBluetooth() {
        DefaultBluetoothUtil.getBluetoothAdapter().cancelDiscovery();
    }

    // 开启连接
    public static void connect(BlueInfo blueInfo, BluetoothDateCallBack bluetoothDateCallBack) throws BluetoothSupportedException, BluetoothSwitchCloseException, InputIncompleteException {
        // 设备蓝牙是否支持
        if (!DefaultBluetoothUtil.isBluetoothSupported())
            throw new BluetoothSupportedException();

        // 蓝牙功能未打开
        if (!DefaultBluetoothUtil.isBluetoothEnabled())
            throw new BluetoothSwitchCloseException();

        if (blueInfo == null && TextUtils.isEmpty(blueInfo.address) && TextUtils.isEmpty(blueInfo.name) && bluetoothDateCallBack == null)
            throw new InputIncompleteException();

        // 得到相应蓝牙
        BluetoothDevice device = DefaultBluetoothUtil.getBluetoothAdapter().getRemoteDevice(blueInfo.address);

        // 开启线程
        close();
        DefaultGlobalConstants.defaultThread = new DefaultThread(device);
        DefaultGlobalConstants.defaultHandler.setBluetoothDateCallBack(bluetoothDateCallBack);
        DefaultGlobalConstants.defaultThread.start();

    }

    // 写内容
    public static void write(String string) throws IOException {
        if (DefaultGlobalConstants.defaultThread != null) {
            DefaultGlobalConstants.defaultThread.write(string);
        }
    }

    // 关闭连接
    public static void close() {
        if (DefaultGlobalConstants.defaultThread != null) {
            DefaultGlobalConstants.defaultThread.close();
            DefaultGlobalConstants.defaultThread = null;
        }
    }

}
