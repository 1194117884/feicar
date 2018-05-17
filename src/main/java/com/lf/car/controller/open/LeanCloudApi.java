package com.lf.car.controller.open;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.service.LeanCloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lc/rtm")
public class LeanCloudApi {

    private static Logger logger = LoggerFactory.getLogger(LeanCloudApi.class);

    @Autowired
    private LeanCloudService leanCloudService;

    @GetMapping("")
    public BaseResponse info() {
        return BaseResponse.success(leanCloudService.getLeanCloudInfo());
    }
}
