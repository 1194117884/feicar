package com.lf.car.model;

import java.util.List;

public class WeChatButton {

    //click.view,scancode_push,scancode_waitmsg,
    //pic_sysphoto,pic_photo_or_album,pic_weixin,location_select,media_id,view_limited
    private String type;
    private String name;

    private String key;//click

    private String url;//view。miniprogram

    private String appid;//miniprogram

    private String media_id;//miniprogram
    private String pagepath;//miniprogram
    private List<WeChatButton> sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeChatButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WeChatButton> sub_button) {
        this.sub_button = sub_button;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
