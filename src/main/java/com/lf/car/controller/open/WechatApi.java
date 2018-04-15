package com.lf.car.controller.open;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.service.WeChatAccessTokenService;
import com.lf.car.util.WeChatSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wechat")
public class WechatApi {

    private static Logger logger = LoggerFactory.getLogger(WechatApi.class);

    @Autowired
    private WeChatSignatureUtil weChatSignatureUtil;
    @Autowired
    private WeChatAccessTokenService weChatAccessTokenService;


    @GetMapping(value = "/ping")
    public String weChatCheck(String signature, String timestamp, String nonce, String echostr) {
        logger.info("对接服务器认证接口");
        if (weChatSignatureUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "error";
    }

    @GetMapping(value = "/access_token")
    public BaseResponse getAccessToken() {
        logger.info("获取微信accessToken");
        return BaseResponse.success(weChatAccessTokenService.getAccessToken().getAccess_token());
    }

}
