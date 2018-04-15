package com.lf.car.controller.admin;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.model.WeChatMenu;
import com.lf.car.service.AdminTokenService;
import com.lf.car.service.WeChatMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat/menu")
public class WeChatMenuController {

    private static Logger logger = LoggerFactory.getLogger(WeChatMenuController.class);

    @Autowired
    private AdminTokenService adminTokenService;
    @Autowired
    private WeChatMenuService weChatMenuService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse create(HttpServletRequest request, @RequestBody(required = false) WeChatMenu menu) {
        try {
            adminTokenService.validToken(request, "POST:/wechat/menu");

            weChatMenuService.createMenu(menu);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("保存菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("保存菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public BaseResponse delete(HttpServletRequest request) {
        try {
            adminTokenService.validToken(request, "DELETE:/wechat/menu");

            weChatMenuService.deleteMenu();
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("删除菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("删除菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse find(HttpServletRequest request) {
        try {
            adminTokenService.validToken(request, "GET:/wechat/menu");

            WeChatMenu menu = weChatMenuService.queryMenu();
            return BaseResponse.success(menu);
        } catch (CarException e) {
            logger.error("查询菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }


}
