package com.yang.util.exception;

/**
 * Created by shiq_yang on 2017/7/5.
 */

public class NoConnectException extends Exception {

    public NoConnectException() {
        super("未找到指定连接");
    }

}
