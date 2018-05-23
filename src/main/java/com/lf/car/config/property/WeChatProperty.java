package com.lf.car.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperty {

    private String appid;
    private String secret;
    private String token;
    private String aeskey;

    //对接
    private String accessTokenUrl;
    //jsapi_ticket
    private String jsapiTicketUrl;
    //菜单管理
    private String menuGetUrl;
    private String menuCreateUrl;
    private String menuDeleteUrl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAeskey() {
        return aeskey;
    }

    public void setAeskey(String aeskey) {
        this.aeskey = aeskey;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getMenuGetUrl() {
        return menuGetUrl;
    }

    public void setMenuGetUrl(String menuGetUrl) {
        this.menuGetUrl = menuGetUrl;
    }

    public String getMenuCreateUrl() {
        return menuCreateUrl;
    }

    public void setMenuCreateUrl(String menuCreateUrl) {
        this.menuCreateUrl = menuCreateUrl;
    }

    public String getMenuDeleteUrl() {
        return menuDeleteUrl;
    }

    public void setMenuDeleteUrl(String menuDeleteUrl) {
        this.menuDeleteUrl = menuDeleteUrl;
    }

    public String getJsapiTicketUrl() {
        return jsapiTicketUrl;
    }

    public void setJsapiTicketUrl(String jsapiTicketUrl) {
        this.jsapiTicketUrl = jsapiTicketUrl;
    }
}
