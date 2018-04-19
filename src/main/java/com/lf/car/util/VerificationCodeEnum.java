package com.lf.car.util;

public enum VerificationCodeEnum {
    USER_PHONE_REGISTER("user_phone_register"),
    USER_PHONE_LOGIN_AND_REGISTER("user_phone_login_and_register"),
    USER_PHONE_LOGIN("user_phone_login");

    private String value;

    VerificationCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
