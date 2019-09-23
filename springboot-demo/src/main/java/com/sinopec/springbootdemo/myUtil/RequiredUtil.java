package com.sinopec.springbootdemo.myUtil;

public class RequiredUtil {

    public Boolean Required(int i) {
        Boolean flag = false;
        if (i > 0) {
            flag = true;
        }
        return flag;
    }

    public Boolean Required(String str) {
        Boolean flag = false;
        if (str.length() > 0 || str != null || str != "") {
            flag = true;
        }
        return flag;
    }
}
