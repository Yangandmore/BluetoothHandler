package com.yang.util.interf;

import com.yang.util.BlueInfo;

/**
 * Created by shiq_yang on 2017/6/8.
 */

public interface BluetoothSearchCallBack {

    // 蓝牙搜索数据
    void searchStateAndDate(boolean flag, BlueInfo blueInfo);

}
