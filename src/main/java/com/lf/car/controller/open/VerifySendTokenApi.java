package com.lf.car.controller.open;

import com.lf.car.service.VerifySendTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/vst")
public class VerifySendTokenApi {

    private static Logger logger = LoggerFactory.getLogger(VerifySendTokenApi.class);

    @Autowired
    private VerifySendTokenService verifySendTokenService;

    @GetMapping(value = "")
    @ResponseBody
    public String getNewSendToken(HttpServletRequest request, HttpServletResponse response) {
        logger.info("获取最新验证码");
        return verifySendTokenService.getSendTokenImg(request, response);
    }

}
