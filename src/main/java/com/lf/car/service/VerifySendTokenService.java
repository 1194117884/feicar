package com.lf.car.service;

import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.util.RandomValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class VerifySendTokenService {

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(VerifySendTokenService.class);


    public void validSendToken(HttpServletRequest request, String token) {
        logger.info("验证图片验证码");
        if (StringUtils.isEmpty(token) || request == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        //1:获取session验证码的信息
        String id = request.getSession().getId();
        String code = (String) request.getSession().getAttribute(id + "imagecode");
        //2:判断验证码是否正确
        if (StringUtils.isEmpty(token) || !token.equals(code))
            throw new CarException(ErrorCode.SMS_TOKEN_ERROR);
    }

    public String getSendTokenImg(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response, "imagecode");// 输出图片方法
        } catch (Exception e) {
            logger.error("获取验证码出错", e);
        }
        return "";
    }
}
