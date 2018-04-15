package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.controller.requs.LoginUserBody;
import com.lf.car.controller.requs.RegisterUserBody;
import com.lf.car.controller.resps.LoginInfo;
import com.lf.car.entity.User;
import com.lf.car.entity.UserToken;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.UserRepository;
import com.lf.car.util.MD5Util;
import com.lf.car.util.VerificationCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private VerificationCodeService verificationCodeService;


    public User register(RegisterUserBody registerBody) {
        logger.info("注册一个用户：{}", JSONObject.toJSONString(registerBody));

        verifyRegisterArgs(registerBody);

        User user = buildUser(registerBody);

        return userRepository.saveAndFlush(user);
    }

    private User buildUser(RegisterUserBody registerBody) {
        User user = new User();
        user.setRegisterTime(new Date());
        user.setPhone(registerBody.getPhone());
        user.setUsername(registerBody.getPhone());
        user.setPassword(MD5Util.md5(registerBody.getPassword()));
        user.setStatus(0);

        return user;
    }

    private void verifyRegisterArgs(RegisterUserBody registerBody) {
        if (registerBody == null || StringUtils.isEmpty(registerBody.getForm()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        switch (registerBody.getForm()){
            case "phone":
                if (StringUtils.isEmpty(registerBody.getPhone()) ||
                        StringUtils.isEmpty(registerBody.getPassword()) ||
                        StringUtils.isEmpty(registerBody.getCode()))
                    throw new CarException(ErrorCode.PARAM_ERROR);
                User user = userRepository.findByUsername(registerBody.getPhone());
                if (user != null)
                    throw new CarException(ErrorCode.USER_REGISTER_PHONE_EXISTS_ERROR);
                user = userRepository.findByPhone(registerBody.getPhone());
                if (user != null)
                    throw new CarException(ErrorCode.USER_REGISTER_PHONE_EXISTS_ERROR);
                boolean check = verificationCodeService.verifyCode(registerBody.getPhone(), VerificationCodeEnum.USER_PHONE_REGISTER, registerBody.getCode());
                if (!check)
                    throw new CarException(ErrorCode.SMS_CODE_ERROR);

                break;
            default:
                    throw new CarException(ErrorCode.PARAM_ERROR);
        }


    }

    public LoginInfo login(LoginUserBody loginUserBody) {
        logger.info("登陆一个用户：{}", JSONObject.toJSONString(loginUserBody));

        verifyLoginArgs(loginUserBody);

        User user = getLoginUser(loginUserBody);

        UserToken userToken = userTokenService.saveToken(user);

        return buildLoginInfo(user, userToken);
    }

    private LoginInfo buildLoginInfo(User user, UserToken userToken) {
        if (user == null || userToken == null)
            throw new CarException(ErrorCode.USER_LOGIN_ERROR);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(user);
        loginInfo.setUserToken(userToken);
        return loginInfo;
    }

    private User getLoginUser(LoginUserBody loginUserBody) {
        switch (loginUserBody.getForm()){
            case "phone":
                if (StringUtils.isEmpty(loginUserBody.getPhone()) ||
                        StringUtils.isEmpty(loginUserBody.getCode()))
                    throw new CarException(ErrorCode.PARAM_ERROR);
                User user = userRepository.findByPhone(loginUserBody.getPhone());
                if (user == null)
                    throw new CarException(ErrorCode.USER_LOGIN_PHONE_NOT_EXISTS_ERROR);
                boolean check = verificationCodeService.verifyCode(loginUserBody.getPhone(), VerificationCodeEnum.USER_PHONE_LOGIN, loginUserBody.getCode());
                if (!check)
                    throw new CarException(ErrorCode.SMS_CODE_ERROR);

                return user;

            case "username":
                if (StringUtils.isEmpty(loginUserBody.getUsername()) ||
                        StringUtils.isEmpty(loginUserBody.getPassword()))
                    throw new CarException(ErrorCode.PARAM_ERROR);
                User user1 = userRepository.findByUsername(loginUserBody.getUsername());
                if (user1 == null)
                    throw new CarException(ErrorCode.USER_LOGIN_USERNAME_NOT_EXISTS_ERROR);
                if (!user1.getPassword().equals(MD5Util.md5(loginUserBody.getPassword())))
                    throw new CarException(ErrorCode.USER_LOGIN_USERNAME_OR_PASSWORD_ERROR);
                return user1;
            default:
                return null;
        }
    }

    private void verifyLoginArgs(LoginUserBody loginUserBody) {
        if (loginUserBody == null || StringUtils.isEmpty(loginUserBody.getForm()))
            throw new CarException(ErrorCode.PARAM_ERROR);

        switch (loginUserBody.getForm()){
            case "phone":
                if (StringUtils.isEmpty(loginUserBody.getPhone()) ||
                        StringUtils.isEmpty(loginUserBody.getCode()))
                    throw new CarException(ErrorCode.PARAM_ERROR);
                break;
            case "username":
                if (StringUtils.isEmpty(loginUserBody.getUsername()) ||
                        StringUtils.isEmpty(loginUserBody.getPassword()))
                    throw new CarException(ErrorCode.PARAM_ERROR);
                break;
            default:
                throw new CarException(ErrorCode.PARAM_ERROR);
        }
    }

    public User findOneUser(Long id) {
        logger.info("查找用户：{}的信息", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return userRepository.findOneById(id);
    }
}
