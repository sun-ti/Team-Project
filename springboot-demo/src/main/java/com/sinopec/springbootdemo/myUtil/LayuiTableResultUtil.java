package com.sinopec.springbootdemo.myUtil;

public class LayuiTableResultUtil<T> {
    private String msg;
    private T data;
    private int code;
    private int count;

    @Override
    public String toString() {
        return "LayuiTableResultUtil [" +
                "msg=" + msg +
                ", data=" + data +
                ", code=" + code +
                ", count=" + count +
                ']';
    }

    public LayuiTableResultUtil(String msg, T data, int code, int count) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
