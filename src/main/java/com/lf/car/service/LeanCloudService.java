package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.config.property.LeanCloudProperty;
import com.lf.car.entity.LcConversation;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class LeanCloudService {

    private static Logger logger = LoggerFactory.getLogger(LeanCloudService.class);

    @Autowired
    private LeanCloudProperty leanCloudProperty;

    public LeanCloudProperty getLeanCloudInfo() {
        return leanCloudProperty;
    }


    public void createOneConversations(LcConversation conversation) {
        logger.info("创建一个对话", JSONObject.toJSONString(conversation));
        validCreateArgs(conversation);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(conversation));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(leanCloudProperty.getCreateConversationsUrl())
                .addHeader("X-LC-Id", leanCloudProperty.getAppId())
                .addHeader("X-LC-Key", leanCloudProperty.getAppKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("创建一个对话:{}", JSONObject.toJSONString(request));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    private void validCreateArgs(LcConversation conversation) {
        if (conversation == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (conversation.getM() == null || StringUtils.isEmpty(conversation.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
    }

    public void findOneConversations(LcConversation conversation) {
        logger.info("查询一个对话", JSONObject.toJSONString(conversation));
        validCreateArgs(conversation);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(conversation));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(leanCloudProperty.getCreateConversationsUrl())
                .addHeader("X-LC-Id", leanCloudProperty.getAppId())
                .addHeader("X-LC-Key", leanCloudProperty.getAppKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("查询一个对话:{}", JSONObject.toJSONString(request));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
