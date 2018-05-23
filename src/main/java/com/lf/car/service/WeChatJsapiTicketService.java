package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.config.property.WeChatProperty;
import com.lf.car.entity.WeChatJsapiTicket;
import com.lf.car.repository.WeChatJsapiTicketRepository;
import com.lf.car.util.DateUtil;
import com.lf.car.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WeChatJsapiTicketService {

    private static Logger logger = LoggerFactory.getLogger(WeChatJsapiTicketService.class);

    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private WeChatProperty weChatProperty;
    @Autowired
    private WeChatJsapiTicketRepository weChatJsapiTicketRepository;
    @Autowired
    private WeChatAccessTokenService weChatAccessTokenService;

    public WeChatJsapiTicket getJsapiTicket() {
        logger.info("获取wechat访问的jsapiTicket");
        WeChatJsapiTicket jsapiTicket = weChatJsapiTicketRepository.findFirstByOrderByIdDesc();
        if (jsapiTicket == null || jsapiTicket.getExpiresTime().before(new Date())) {
            jsapiTicket = findNewAccessToken();
        }
        return jsapiTicket;
    }

    private WeChatJsapiTicket findNewAccessToken() {
        logger.info("重新获取wechat的jsapiTicket");
        String result = httpUtil.getResponse(buildGetAccessTokenUrl(), "", 3000);
        logger.info("重新获取wechat的jsapiTicket结果：{}", result);
        if (result.contains("\"errcode\":0,")) {
            WeChatJsapiTicket jsapiTicket = JSONObject.parseObject(result, WeChatJsapiTicket.class);
            jsapiTicket.setCreateTime(new Date());
            jsapiTicket.setExpiresTime(DateUtil.addSecond(jsapiTicket.getCreateTime(), jsapiTicket.getExpires_in()));
            return weChatJsapiTicketRepository.saveAndFlush(jsapiTicket);
        }
        return null;
    }

    private String buildGetAccessTokenUrl() {
        return weChatProperty.getJsapiTicketUrl() +
                "?access_token=" + weChatAccessTokenService.getAccessToken().getAccess_token() +
                "&type=jsapi";
    }

}
