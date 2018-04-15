package com.lf.car.controller.admin;

import com.lf.car.controller.resps.BaseResponse;
import com.lf.car.entity.Role;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.service.AdminTokenService;
import com.lf.car.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminTokenService adminTokenService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse create(HttpServletRequest request, @RequestBody(required = false) Role role) {
        try {
            adminTokenService.validToken(request, "POST:/role");

            Role save = roleService.createOneRole(role);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("保存一个角色出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("保存一个角色出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("")
    public BaseResponse update(HttpServletRequest request, @RequestBody(required = false) Role role) {
        try {
            adminTokenService.validToken(request, "PUT:/role");

            Role save = roleService.updateOneRole(role);
            return BaseResponse.success(save);
        } catch (CarException e) {
            logger.error("修改一个角色出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("修改一个角色出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public BaseResponse delete(HttpServletRequest request, @RequestBody(required = false) Role role) {
        try {
            adminTokenService.validToken(request, "DELETE:/role");

            roleService.deleteOneRole(role);
            return BaseResponse.success();
        } catch (CarException e) {
            logger.error("删除一个角色出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("删除一个角色出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public BaseResponse find(HttpServletRequest request, @PathVariable(required = false) Long id) {
        try {
            adminTokenService.validToken(request, "GET:/role/{id}");

            Role role = roleService.findOneRole(id);
            return BaseResponse.success(role);
        } catch (CarException e) {
            logger.error("查询一个角色出错", e);
            return BaseResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.error("查询一个角色出错", e);
            return BaseResponse.fail(ErrorCode.SYS_ERROR);
        }
    }

}
