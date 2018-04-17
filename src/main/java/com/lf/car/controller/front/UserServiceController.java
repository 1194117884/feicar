package com.lf.car.controller.front;

import com.lf.car.controller.requs.*;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.controller.resps.LoginInfo;
import com.lf.car.entity.InquiryPriceRecord;
import com.lf.car.entity.ReserveDriveRecord;
import com.lf.car.entity.User;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.InquiryPriceRecordService;
import com.lf.car.service.ReserveDriveRecordService;
import com.lf.car.service.UserService;
import com.lf.car.service.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/usc")
public class UserServiceController {

    private static Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private InquiryPriceRecordService inquiryPriceRecordService;
    @Autowired
    private ReserveDriveRecordService reserveDriveRecordService;

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

    @ResponseBody
    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest request, @RequestBody(required = false) BaseRequest args) {
        try {
            userTokenService.deleteToken(request);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("登出用户", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("登出用户", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/reserve/list")
    public BaseResponse findReserves(HttpServletRequest request, @RequestBody(required = false) FindReservesArgs args) {
        try {
            User user = userTokenService.validToken(request);
            List<ReserveDriveRecord> records = reserveDriveRecordService.findUserReserveRecords(user.getId(), args);
            return BaseResponse.success(records);
        } catch (CarException e) {
            logger.error("查询用户的试驾记录", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询用户的试驾记录", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }
    @ResponseBody
    @PostMapping("/inquiry/list")
    public BaseResponse findInquiries(HttpServletRequest request, @RequestBody(required = false) FindInquiriesArgs args) {
        try {
            User user = userTokenService.validToken(request);
            List<InquiryPriceRecord> records = inquiryPriceRecordService.findUserInquiryRecords(user.getId(), args);
            return BaseResponse.success(records);
        } catch (CarException e) {
            logger.error("查询用户的询价记录", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询用户的询价记录", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
