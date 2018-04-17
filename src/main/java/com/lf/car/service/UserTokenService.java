package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.entity.User;
import com.lf.car.entity.UserToken;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.UserRepository;
import com.lf.car.repository.UserTokenRepository;
import com.lf.car.util.CookieUtil;
import com.lf.car.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserTokenService {

    private static Logger logger = LoggerFactory.getLogger(UserTokenService.class);

    private static final String LOGIN_TOKEN = "ulgv";
    private static final String LOGIN_TOKEN_TYPE = "login";


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    public User validToken(HttpServletRequest request) {
        logger.info("登录用户认证");
        if (request == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        //获取token
        UserToken userToken = findUserToken(request);
        //获取用户信息
        User user = findUser(userToken);

        return user;
    }


    private User findUser(UserToken userToken) {
        if (userToken == null) return null;

        User user = userRepository.findOneById(userToken.getUserId());
        if (user == null)
            throw new CarException(ErrorCode.USER_LOGIN_TIMEOUT_ERROR);
        if (user.getStatus() == -1)
            throw new CarException(ErrorCode.USER_FREEZE_ERROR);

        return user;
    }

    private UserToken findUserToken(HttpServletRequest request) {

        String token = getTokenFromCookie(request);
        if (StringUtils.isEmpty(token))
            throw new CarException(ErrorCode.USER_LOGIN_TIMEOUT_ERROR);
        UserToken userToken = userTokenRepository.findByTokenAndType(token, LOGIN_TOKEN_TYPE);
        if (userToken == null)
            throw new CarException(ErrorCode.USER_LOGIN_TIMEOUT_ERROR);

        return userToken;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request == null) return null;
        return CookieUtil.getCookieValue(request, LOGIN_TOKEN);
    }

    public UserToken saveToken(User user) {
        logger.info("用户登陆保存token：{}", JSONObject.toJSONString(user));
        if (user == null) return null;
        UserToken userToken = userTokenRepository.findByUserIdAndType(user.getId(), LOGIN_TOKEN_TYPE);
        if (userToken == null){
            userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(TokenUtil.getLoginToken());
            userToken.setType(LOGIN_TOKEN_TYPE);
            userToken.setCreateTime(new Date());
        } else {
            userToken.setCreateTime(new Date());
        }
        return userTokenRepository.saveAndFlush(userToken);
    }

    @Transactional
    public void deleteToken(HttpServletRequest request) {
        logger.info("退出登陆");
        if (request == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        //获取token
        String token = getTokenFromCookie(request);
        if (!StringUtils.isEmpty(token)){
            userTokenRepository.deleteByToken(token);
        }
    }
}
