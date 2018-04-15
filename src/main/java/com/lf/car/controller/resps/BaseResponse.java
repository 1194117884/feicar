package com.lf.car.controller.resps;

import com.lf.car.exception.ErrorCode;

public class BaseResponse {


    private Integer code;
    private String msg;
    private Object data;


    public BaseResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //---------------------------------------
    public static BaseResponse success() {
        return new BaseResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), null);
    }

    public static BaseResponse success(ErrorCode errorCode) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static BaseResponse success(Object data) {
        return new BaseResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), data);
    }

    public static BaseResponse fail(ErrorCode errorCode) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

}
