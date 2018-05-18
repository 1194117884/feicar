package com.lf.car.exception;

public enum ErrorCode {
    SUCCESS(0, "成功！"),
    NET_ERROR(400, "网络出错！"),
    SYS_ERROR(500, "系统错误！"),
    PARAM_ERROR(1, "参数异常！"),

    //sms
    SMS_PHONE_ERROR(1001, "请填写正确手机号！"),
    SMS_MAX_SEND_ERROR(1002, "短信发送次数过多！"),
    SMS_CODE_TIMEOUT_ERROR(1003, "短信验证码失效！"),
    SMS_CODE_ERROR(1004, "短信验证码错误！"),
    SMS_TOKEN_ERROR(1005, "图片验证码错误！"),

    //admin
    ADMIN_LOGIN_TIMEOUT_ERROR(100001, "登陆超时，请重新登陆！"),
    ADMIN_FREEZE_ERROR(100002, "管理员已被冻结，无法登陆！"),
    ADMIN_NO_AUTH_ERROR(100003, "没有此权限!"),
    ADMIN_NOT_EXISTS(100004, "账户不存在！"),
    ADMIN_PASSWORD_ERROR(100005, "登录密码错误！"),

    //user
    USER_REGISTER_PHONE_EXISTS_ERROR(200005, "手机号已被占用！"),
    USER_REGISTER_USERNAME_EXISTS_ERROR(200006, "用户名已被占用！"),
    USER_LOGIN_TIMEOUT_ERROR(200007, "登陆超时，请重新登陆！"),
    USER_LOGIN_ERROR(200008, "登陆出错，请重新登陆！"),
    USER_FREEZE_ERROR(200009, "您的账号已被冻结，无法登陆！"),
    USER_LOGIN_USERNAME_NOT_EXISTS_ERROR(200010, "用户未注册，请先注册！"),
    USER_LOGIN_PHONE_NOT_EXISTS_ERROR(200011, "手机未注册，请先注册！"),
    USER_LOGIN_USERNAME_OR_PASSWORD_ERROR(200012, "用户名或密码错误！");

    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
