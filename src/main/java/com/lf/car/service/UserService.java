package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.controller.requs.LoginUserBody;
import com.lf.car.controller.requs.RegisterUserBody;
import com.lf.car.controller.requs.UpdateUserInfoArgs;
import com.lf.car.controller.resps.UserLoginInfo;
import com.lf.car.entity.User;
import com.lf.car.entity.UserToken;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.UserRepository;
import com.lf.car.util.DateUtil;
import com.lf.car.util.MD5Util;
import com.lf.car.util.VerificationCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

        userRepository.saveAndFlush(user);

        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setPassword("(  ૢ⁼̴̤̆ ㉨ ⁼̴̤̆ ૢ)♡ 约吗？");
        return user1;
    }

    private User buildUser(RegisterUserBody registerBody) {
        User user = new User();
        Date now = new Date();
        user.setRegisterTime(now);
        user.setPhone(registerBody.getPhone());
        user.setNickname("用户" + DateUtil.format("yyMMddhhmm", now));

        switch (registerBody.getForm()) {
            case "phone":
                String phone = registerBody.getPhone();
                user.setUsername(phone);//用户名字=手机号
                user.setPassword(MD5Util.md5(MD5Util.md5(phone.substring(phone.length() - 6, phone.length()))));//手机号后六位
                break;

            default:

        }
        user.setStatus(0);

        return user;
    }

    private void verifyRegisterArgs(RegisterUserBody registerBody) {
        if (registerBody == null || StringUtils.isEmpty(registerBody.getForm()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        switch (registerBody.getForm()) {
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

    public UserLoginInfo login(LoginUserBody loginUserBody) {
        logger.info("登陆一个用户：{}", JSONObject.toJSONString(loginUserBody));

        verifyLoginArgs(loginUserBody);

        User user = getLoginUser(loginUserBody);

        UserToken userToken = userTokenService.saveToken(user);

        return buildLoginInfo(user, userToken);
    }

    private UserLoginInfo buildLoginInfo(User user, UserToken userToken) {
        if (user == null || userToken == null)
            throw new CarException(ErrorCode.USER_LOGIN_ERROR);
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setPassword("(  ૢ⁼̴̤̆ ㉨ ⁼̴̤̆ ૢ)♡ 约吗？");

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUser(user1);
        userLoginInfo.setUserToken(userToken);
        return userLoginInfo;
    }

    private User getLoginUser(LoginUserBody loginUserBody) {
        switch (loginUserBody.getForm()) {
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

        switch (loginUserBody.getForm()) {
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

    public UserLoginInfo registerAndLogin(LoginUserBody loginUserBody) {
        logger.info("使用手机登陆和注册一个用户:{}", JSONObject.toJSONString(loginUserBody));
        if (loginUserBody == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(loginUserBody.getPhone()) ||
                StringUtils.isEmpty(loginUserBody.getCode()))
            throw new CarException(ErrorCode.PARAM_ERROR);

        User user = userRepository.findByUsername(loginUserBody.getPhone());
        if (user == null)
            user = userRepository.findByPhone(loginUserBody.getPhone());

        if (user == null) {
            boolean check = verificationCodeService.verifyCode(loginUserBody.getPhone(), VerificationCodeEnum.USER_PHONE_LOGIN_AND_REGISTER, loginUserBody.getCode());
            if (!check)
                throw new CarException(ErrorCode.SMS_CODE_ERROR);
            RegisterUserBody registerBody = new RegisterUserBody();
            registerBody.setForm("phone");
            registerBody.setCode(loginUserBody.getCode());
            registerBody.setPhone(loginUserBody.getPhone());

            user = userRepository.saveAndFlush(buildUser(registerBody));
        } else {//登陆
            boolean check = verificationCodeService.verifyCode(loginUserBody.getPhone(), VerificationCodeEnum.USER_PHONE_LOGIN_AND_REGISTER, loginUserBody.getCode());
            if (!check)
                throw new CarException(ErrorCode.SMS_CODE_ERROR);
        }
        //token
        UserToken userToken = userTokenService.saveToken(user);

        //return
        return buildLoginInfo(user, userToken);
    }

    public void updateUserInfo(long id, UpdateUserInfoArgs args) {
        if (args == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        User user = userRepository.findOneById(id);

        if (!StringUtils.isEmpty(args.getName()))
            user.setName(args.getName());
        if (!StringUtils.isEmpty(args.getAddress()))
            user.setAddress(args.getAddress());
        if (!StringUtils.isEmpty(args.getBirthday()))
            user.setBirthday(DateUtil.parse("yyyy-MM-dd", args.getBirthday()));
        if (!StringUtils.isEmpty(args.getHeadPic()))
            user.setHeadPic(args.getHeadPic());
        if (!StringUtils.isEmpty(args.getNickname()))
            user.setNickname(args.getNickname());

        userRepository.saveAndFlush(user);
    }
}
