package com.lf.car.util;

public class TokenUtil {

    public static String getLoginToken(){
        int random = (int) Math.random() * 1000000;
        long timeMillis = System.currentTimeMillis();
        return MD5Util.md5(random + ":" + timeMillis);
    }

}