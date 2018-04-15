package com.lf.car.controller.admin;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.Method;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.AdminTokenService;
import com.lf.car.service.MethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/method")
public class MethodController {

    private static Logger logger = LoggerFactory.getLogger(MethodController.class);

    @Autowired
    private MethodService methodService;
    @Autowired
    private AdminTokenService adminTokenService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse create(HttpServletRequest request, @RequestBody(required = false) Method method) {
        try {
            adminTokenService.validToken(request, "POST:/method");

            Method save = methodService.createOneMethod(method);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("保存一个方法出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("保存一个方法出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("")
    public BaseResponse update(HttpServletRequest request, @RequestBody(required = false) Method method) {
        try {
            adminTokenService.validToken(request, "PUT:/method");

            Method save = methodService.updateOneMethod(method);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("修改一个方法出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("修改一个方法出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public BaseResponse delete(HttpServletRequest request, @RequestBody(required = false) Method method) {
        try {
            adminTokenService.validToken(request, "DELETE:/method");

            methodService.deleteOneMethod(method);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("删除一个方法出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("删除一个方法出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public BaseResponse find(HttpServletRequest request, @PathVariable(required = false) Long id) {
        try {
            adminTokenService.validToken(request, "GET:/method/{id}");

            Method method = methodService.findOneMethod(id);
            return BaseResponse.success(method);
        } catch (CarException e) {
            logger.error("查询一个方法出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询一个方法出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }


}
