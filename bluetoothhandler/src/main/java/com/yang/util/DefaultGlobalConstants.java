package com.yang.util;

import android.content.Context;

import java.util.UUID;

/**
 * Created by yang on 2017/6/6.
 * 用于对内保护的所需对象或常数
 */

class DefaultGlobalConstants {

    // 蓝牙UUID
    static UUID UUID_SPP = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    // application - this
    static Context context = null;

    // thread
    static DefaultThread defaultThread = null;

    // handler
    static DefaultHandler defaultHandler = null;

    // 广播接收者
    static DefaultBroadcaseReceiver defaultBroadcaseReceiver = null;

    // Handler返回类型
    class HandlerState {
        // 蓝牙打开
        public static final int BLUETOOTH_SWITCH_OPEN = 2006;
        // 蓝牙关闭
        public static final int BLUETOOTH_SWITCH_CLOSE = 2007;
        // 蓝牙搜索开启
        public static final int BLUETOOTH_SEARCH_OPEN = 2008;
        // 蓝牙搜索关闭
        public static final int BLUETOOTH_SEARCH_CLOSE = 2009;
        // 蓝牙连接成功
        public static final int BLUETOOTH_CONNECT_SUCCESS = 3001;
        // 蓝牙连接失败
        public static final int BLUETOOTH_CONNECT_FAIL = 3002;
        // 蓝牙连接断开
        public static final int BLUETOOTH_CONNECT_BREAK = 3003;
        // 读取数据
        public static final int BLUETOOTH_CONNECT_READ = 3004;
    }

}
