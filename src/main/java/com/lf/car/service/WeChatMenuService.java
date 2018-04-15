package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.config.property.WeChatProperty;
import com.lf.car.entity.WeChatAccessToken;
import com.lf.car.model.WeChatMenu;
import com.lf.car.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeChatMenuService {

    private static Logger logger = LoggerFactory.getLogger(WeChatMenuService.class);

    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private WeChatProperty weChatProperty;
    @Autowired
    private WeChatAccessTokenService weChatAccessTokenService;


    public void createMenu(WeChatMenu weChatMenu) throws IOException {
        logger.error("创建微信菜单信息");
        WeChatAccessToken accessToken = weChatAccessTokenService.getAccessToken();
        String result = httpUtil.getPost(weChatProperty.getMenuCreateUrl() + "?access_token=" + accessToken.getAccess_token(),
                "UTF-8", JSONObject.toJSONString(weChatMenu), 3000);
        logger.info("创建微信菜单信息:{}", result);
        return;
    }

    public WeChatMenu queryMenu() throws IOException {
        logger.error("查询微信菜单信息");
        WeChatAccessToken accessToken = weChatAccessTokenService.getAccessToken();
        String result = httpUtil.getResponse(weChatProperty.getMenuGetUrl() + "?access_token=" + accessToken.getAccess_token(), "", 3000);
        logger.info("查询微信菜单信息:{}", result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.get("errcode").equals("0")) {
            return JSONObject.parseObject(jsonObject.get("menu").toString(), WeChatMenu.class);
        }
        return null;
    }

    public void deleteMenu() throws IOException {
        logger.error("删除微信菜单信息");
        WeChatAccessToken accessToken = weChatAccessTokenService.getAccessToken();
        String result = httpUtil.getResponse(weChatProperty.getMenuDeleteUrl() + "?access_token=" + accessToken.getAccess_token(), "", 3000);
        logger.info("删除微信菜单信息:{}", result);
        return;
    }

}
