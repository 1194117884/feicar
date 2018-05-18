package com.lf.car.controller.admin;

import com.lf.car.controller.resps.AdminLoginInfo;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @GetMapping("/login")
    public BaseResponse loginByUsernameAndPassword(String username, String password) {
        try {
            AdminLoginInfo loginInfo = adminService.loginByUsernameAndPassword(username, password);
            return BaseResponse.success(loginInfo);
        } catch (CarException e) {
            logger.error("登录管理员", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("登录管理员", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }


}