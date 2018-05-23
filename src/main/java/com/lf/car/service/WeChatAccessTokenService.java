package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.config.property.WeChatProperty;
import com.lf.car.entity.WeChatAccessToken;
import com.lf.car.repository.WeChatAccessTokenRepository;
import com.lf.car.util.DateUtil;
import com.lf.car.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WeChatAccessTokenService {

    private static Logger logger = LoggerFactory.getLogger(WeChatAccessTokenService.class);

    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private WeChatProperty weChatProperty;
    @Autowired
    private WeChatAccessTokenRepository weChatAccessTokenRepository;

    public WeChatAccessToken getAccessToken() {
        logger.info("获取wechat访问的accessToken");
        WeChatAccessToken accessToken = weChatAccessTokenRepository.findFirstByOrderByIdDesc();
        if (accessToken == null || accessToken.getExpiresTime().before(new Date())) {
            accessToken = findNewAccessToken();
        }
        return accessToken;
    }

    private WeChatAccessToken findNewAccessToken() {
        logger.info("重新获取wechat的accessToken");
        String result = httpUtil.getResponse(buildGetAccessTokenUrl(), "", 3000);
        logger.info("重新获取wechat的accessToken结果：{}", result);
        if (!result.contains("errcode")) {
            WeChatAccessToken accessToken = JSONObject.parseObject(result, WeChatAccessToken.class);
            accessToken.setCreateTime(new Date());
            accessToken.setExpiresTime(DateUtil.addSecond(accessToken.getCreateTime(), accessToken.getExpires_in()));
            return weChatAccessTokenRepository.saveAndFlush(accessToken);
        }
        return null;
    }

    private String buildGetAccessTokenUrl() {
        return weChatProperty.getAccessTokenUrl() +
                "?grant_type=client_credential" +
                "&appid=" + weChatProperty.getAppid() +
                "&secret=" + weChatProperty.getSecret();
    }

}
