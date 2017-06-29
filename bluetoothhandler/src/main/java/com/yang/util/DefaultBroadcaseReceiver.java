package com.yang.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;

/**
 * Created by yang on 2017/6/6.
 * 蓝牙广播接收者
 */

class DefaultBroadcaseReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            // 状态
            int blueState = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_STATE, 0);
            switch (blueState) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    break;
                case BluetoothAdapter.STATE_ON:
                    // 蓝牙打开
                    Message msg1 = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_SWITCH_OPEN);
                    DefaultGlobalConstants.defaultHandler.sendMessage(msg1);

                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    break;
                case BluetoothAdapter.STATE_OFF:
                    // 蓝牙关闭
                    Message msg2 = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_SWITCH_CLOSE);
                    DefaultGlobalConstants.defaultHandler.sendMessage(msg2);

                    break;
            }

        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // 开始搜索
            BluetoothDevice device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                BlueInfo blueInfo = new BlueInfo();
                blueInfo.name = TextUtils.isEmpty(device.getName()) ? "未知蓝牙"
                        : device.getName();
                blueInfo.address = device.getAddress();
                // 发送搜索蓝牙对象
                Message msg1 = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_SEARCH_OPEN);
                msg1.obj = blueInfo;
                DefaultGlobalConstants.defaultHandler.sendMessage(msg1);
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                .equals(action)) {
            // 搜索结束
            Message msg1 = DefaultGlobalConstants.defaultHandler.obtainMessage(DefaultGlobalConstants.HandlerState.BLUETOOTH_SEARCH_CLOSE);
            DefaultGlobalConstants.defaultHandler.sendMessage(msg1);
        }
    }
}
