package com.lf.car.service;

import com.lf.car.config.property.WeChatProperty;
import com.lf.car.model.WeChatSignNature;
import com.lf.car.util.SHA1Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author:Claire
 * Date:2017/8/27 14:21
 * Description:
 */
@Service
public class WeChatJsSdkService {
    private Logger logger = LoggerFactory.getLogger(WeChatJsSdkService.class);

    private static final String NONCE_STR = "dingzhouroewe";
    @Autowired
    private WeChatProperty weChatProperty;
    @Autowired
    private WeChatJsapiTicketService weChatJsapiTicketService;

    public WeChatSignNature getSignNature(String url) {
        WeChatSignNature signNature = new WeChatSignNature();
        signNature.setNonceStr(NONCE_STR);
        signNature.setTimestamp(System.currentTimeMillis() / 1000);
        signNature.setAppId(weChatProperty.getAppid());

        Map<String, String> sortMap = new TreeMap<String, String>();
        sortMap.put("jsapi_ticket", weChatJsapiTicketService.getJsapiTicket().getTicket());
        sortMap.put("noncestr", NONCE_STR);
        sortMap.put("timestamp", String.valueOf(signNature.getTimestamp()));
        sortMap.put("url", url);
        // 以k1=v1&k2=v2...方式拼接参数
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> s : sortMap.entrySet()) {
            String k = s.getKey();
            String v = s.getValue();
            if (StringUtils.isEmpty(v)) {// 过滤空值
                continue;
            }
            builder.append(k).append("=").append(v).append("&");
        }
        if (!sortMap.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        signNature.setSignature(SHA1Util.getSHA1StrJava(builder.toString()));
        return signNature;
    }


}
