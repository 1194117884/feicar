package com.lf.car.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lean.cloud")
public class LeanCloudProperty {

    private String appId;
    private String appKey;
    private String masterKey;
    private String region;
    //debug模式
    private Boolean debug;
    //restful api
    private String createConversationsUrl;
    private String findConversationsUrl;
    private String sendMessageUrl;
    private String queryMessageUrl;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public String getCreateConversationsUrl() {
        return createConversationsUrl;
    }

    public void setCreateConversationsUrl(String createConversationsUrl) {
        this.createConversationsUrl = createConversationsUrl;
    }

    public String getFindConversationsUrl() {
        return findConversationsUrl;
    }

    public void setFindConversationsUrl(String findConversationsUrl) {
        this.findConversationsUrl = findConversationsUrl;
    }

    public String getSendMessageUrl() {
        return sendMessageUrl;
    }

    public void setSendMessageUrl(String sendMessageUrl) {
        this.sendMessageUrl = sendMessageUrl;
    }

    public String getQueryMessageUrl() {
        return queryMessageUrl;
    }

    public void setQueryMessageUrl(String queryMessageUrl) {
        this.queryMessageUrl = queryMessageUrl;
    }
}
