package com.lf.car.controller.open;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.VerificationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/sms")
public class SmsApi {

    private static Logger logger = LoggerFactory.getLogger(SmsApi.class);

    @Autowired
    private VerificationCodeService verificationCodeService;

    @ResponseBody
    @PostMapping("/code")
    public BaseResponse sendCode(HttpServletRequest request, String token, String phone, String type) {
        try {
            verificationCodeService.sendCode(request, token, phone, type);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("短信验证码", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("短信验证码", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
