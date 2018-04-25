package com.lf.car.controller.open;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.util.QiNiuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qiniu")
public class QiNiuApi {

    private static Logger logger = LoggerFactory.getLogger(QiNiuApi.class);

    @Autowired
    private QiNiuUtil qiNiuUtil;

    @GetMapping(value = "/token")
    @ResponseBody
    public BaseResponse getUpToken() {
        logger.info("获取qiniu上传token");
        try {
            return BaseResponse.success(qiNiuUtil.getUploadToken());
        } catch (CarException e) {
            logger.error("获取qiniu上传token", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("获取qiniu上传token", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @GetMapping(value = "/photo")
    @ResponseBody
    public BaseResponse getPhotoUrl(@Param("key") String key) {
        logger.info("获取qiniu图片地址");
        try {
            return BaseResponse.success(qiNiuUtil.getPhotoUrl(key));
        } catch (CarException e) {
            logger.error("获取qiniu图片地址", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("获取qiniu图片地址", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
