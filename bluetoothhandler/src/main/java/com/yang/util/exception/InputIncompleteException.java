package com.yang.util.exception;

/**
 * Created by shiq_yang on 2017/7/4.
 * 输入不求全
 */

public class InputIncompleteException extends Exception {
    public InputIncompleteException() {
        super("数据残缺");
    }
}
