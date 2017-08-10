package com.yang.util.exception;

/**
 * Created by shiq_yang on 2017/7/4.
 * 连接已运行
 */

public class ConnectIsRunningException extends Exception {
    public ConnectIsRunningException() {
        super("该连接已存在");
    }
}
