package com.lf.car.controller.front;

import com.lf.car.controller.requs.FindCarModelArgs;
import com.lf.car.controller.requs.FindCarSeriesArgs;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.CarModel;
import com.lf.car.entity.CarSeries;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.CarModelService;
import com.lf.car.service.CarSeriesService;
import com.lf.car.service.HotSliderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
