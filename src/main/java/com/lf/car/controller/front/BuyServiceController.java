package com.lf.car.controller.front;

import com.lf.car.controller.requs.FindCarModelArgs;
import com.lf.car.controller.requs.FindCarSeriesArgs;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.*;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/bsc")
public class BuyServiceController {

    private static Logger logger = LoggerFactory.getLogger(BuyServiceController.class);

    @Autowired
    private CarSeriesService carSeriesService;
    @Autowired
    private HotSliderService hotSliderService;
    @Autowired
    private CarModelService carModelService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private InquiryPriceRecordService inquiryPriceRecordService;
    @Autowired
    private ReserveDriveRecordService reserveDriveRecordService;

    @ResponseBody
    @RequestMapping("/hot_slider")
    public BaseResponse getHotSlider() {
        try {
            return BaseResponse.success(hotSliderService.getHotSliderList());
        } catch (CarException e) {
            logger.error("获取首页热门滚动", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("获取首页热门滚动", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }

    }

    @ResponseBody
    @PostMapping("/car_series")
    public BaseResponse findCarSeries(@RequestBody(required = false) FindCarSeriesArgs args) {
        try {
            List<CarSeries> list = carSeriesService.findCarsSeries(args);
            return BaseResponse.success(list);
        } catch (CarException e) {
            logger.error("查找车系列表", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找车系列表", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/car_models")
    public BaseResponse findCarModels(@RequestBody(required = false) FindCarModelArgs args) {
        try {
            List<CarModel> list = carModelService.findCarModels(args);
            return BaseResponse.success(list);
        } catch (CarException e) {
            logger.error("查找车型列表", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找车型列表", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/car_model/{id}")
    public BaseResponse findCarModel(@PathVariable(required = false) Long id) {
        try {
            CarModel model = carModelService.findCarModelInfoWithFeature(id);
            return BaseResponse.success(model);
        } catch (CarException e) {
            logger.error("查找车型信息", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找车型信息", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }


    @ResponseBody
    @GetMapping("/car_link")
    public BaseResponse findCarLink() {
        try {
            List<CarSeries> series = carSeriesService.findCarSeriesWithModel();
            return BaseResponse.success(series);
        } catch (CarException e) {
            logger.error("查找车折叠列表", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找车折叠列表", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/reserve")
    public BaseResponse reserveDrive(HttpServletRequest request, @RequestBody(required = false) ReserveDriveRecord record) {
        try {
            try{
                User user = userTokenService.validToken(request);
                record.setUserId(user.getId());
            }catch (Exception e){
                   logger.info("登陆消息失效，盲人预约");
            } finally {
                reserveDriveRecordService.reserveOneDrive(record);
            }
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("发起一次试驾", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("发起一次试驾", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/inquiry")
    public BaseResponse inquiryPrice(HttpServletRequest request, @RequestBody(required = false) InquiryPriceRecord record) {
        try {
            try{
                User user = userTokenService.validToken(request);
                record.setUserId(user.getId());
            }catch (Exception e){
                   logger.info("登陆消息失效，盲人询价");
            } finally {
                inquiryPriceRecordService.inquiryOnePrice(record);
            }
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("发起一次询价", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("发起一次询价", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
