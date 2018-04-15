package com.lf.car.controller.admin;

import com.lf.car.controller.requs.FindCarSeriesArgs;
import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.CarSeries;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.AdminTokenService;
import com.lf.car.service.CarSeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/car_series")
public class CarSeriesController {

    private static Logger logger = LoggerFactory.getLogger(CarSeriesController.class);

    @Autowired
    private CarSeriesService carSeriesService;
    @Autowired
    private AdminTokenService adminTokenService;

    @ResponseBody
    @GetMapping("/list")
    public BaseResponse findCarSeries(HttpServletRequest request, @RequestBody FindCarSeriesArgs args) {
        try {
            adminTokenService.validToken(request, "GET:/car_series/list");

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
    @GetMapping("/{id}")
    public BaseResponse findOneCarSeries(@PathVariable Long id) {
        try {
            CarSeries carSeries = carSeriesService.findOneCarSeries(id);
            return BaseResponse.success(carSeries);
        } catch (CarException e) {
            logger.error("查找一个车系", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查找一个车系", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse findOneCarSeries(@RequestBody CarSeries carSeries) {
        try {
            CarSeries save = carSeriesService.createOneCarSeries(carSeries);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("添加一个车系", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("添加一个车系", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("")
    public BaseResponse updateOneCarSeries(@RequestBody CarSeries carSeries) {
        try {
            CarSeries save = carSeriesService.updateOneCarSeries(carSeries);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("修改一个车系", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("修改一个车系", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public BaseResponse deleteOneCarSeries(@RequestBody CarSeries carSeries) {
        try {
            carSeriesService.deleteOneCarSeries(carSeries);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("删除一个车系", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("删除一个车系", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}