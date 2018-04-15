package com.lf.car.controller.front;

import com.lf.car.controller.requs.LoginUserBody;
import com.lf.car.controller.requs.RegisterUserBody;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.controller.resps.LoginInfo;
import com.lf.car.entity.User;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usc")
public class UserServiceController {

    private static Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/{id}")
    public BaseResponse findUser(@PathVariable(required = false) Long id) {
        try {
            User user = userService.findOneUser(id);
            return BaseResponse.success(user);
        } catch (CarException e) {
            logger.error("查找一个用户", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找一个用户", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public BaseResponse register(@RequestBody(required = false) RegisterUserBody registerBody) {
        try {
            User user = userService.register(registerBody);
            return BaseResponse.success(user);
        } catch (CarException e) {
            logger.error("添加一个用户", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("添加一个用户", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody(required = false) LoginUserBody loginUserBody) {
        try {
            LoginInfo loginInfo = userService.login(loginUserBody);
            return BaseResponse.success(loginInfo);
        } catch (CarException e) {
            logger.error("登陆用户", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("登陆用户", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
