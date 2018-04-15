package com.lf.car.controller.admin;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.Menu;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.AdminTokenService;
import com.lf.car.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private AdminTokenService adminTokenService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse create(HttpServletRequest request, @RequestBody(required = false) Menu menu) {
        try {
            adminTokenService.validToken(request, "POST:/menu");

            Menu save = menuService.createOneMenu(menu);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("保存一个菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("保存一个菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("")
    public BaseResponse update(HttpServletRequest request, @RequestBody(required = false) Menu menu) {
        try {
            adminTokenService.validToken(request, "PUT:/menu");

            Menu save = menuService.updateOneMenu(menu);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("修改一个菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("修改一个菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public BaseResponse delete(HttpServletRequest request, @RequestBody(required = false) Menu menu) {
        try {
            adminTokenService.validToken(request, "DELETE:/menu");

            menuService.deleteOneMenu(menu);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("删除一个菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("删除一个菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public BaseResponse find(HttpServletRequest request, @PathVariable(required = false) Long id) {
        try {
            adminTokenService.validToken(request, "GET:/menu/{id}");

            Menu menu = menuService.findOneMenu(id);
            return BaseResponse.success(menu);
        } catch (CarException e) {
            logger.error("查询一个菜单出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询一个菜单出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/link")
    public BaseResponse findMenuLink(HttpServletRequest request) {
        try {
            adminTokenService.validToken(request, "GET:/menu/link");

            List<Menu> menuList = menuService.findMenuLink();
            return BaseResponse.success(menuList);
        } catch (CarException e) {
            logger.error("查询当前菜单链", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询当前菜单链", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
