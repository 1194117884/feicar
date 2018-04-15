package com.lf.car.service;

import com.lf.car.entity.*;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.*;
import com.lf.car.util.CookieUtil;
import com.lf.car.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminTokenService {

    private static final String LOGIN_TOKEN = "algv";
    private static final String LOGIN_TOKEN_TYPE = "login";
    private static final int LOGTIN_TOKEN_TIMEOUT_MINUTE = 1 * 60;

    private static Logger logger = LoggerFactory.getLogger(AdminTokenService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MethodRepository methodRepository;
    @Autowired
    private AdminTokenRepository adminTokenRepository;

    public Admin validToken(HttpServletRequest request, String path) {
        if (request == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        logger.info("认证接口访问权限");

        //获取token
        AdminToken adminToken = findAdminToken(request);
        //获取用户信息
        Admin admin = findAdmin(adminToken);
        //获取角色信息
        List<Role> roleList = findRoles(admin);
        //获取菜单信息
        List<Menu> menuList = findMenus(roleList);
        //获取所有方法
        List<Method> methodList = findMethods(menuList);
        //验证是否存在
        boolean contain = isContainsMethod(methodList, path);

        if (!contain)
            throw new CarException(ErrorCode.ADMIN_NO_AUTH_ERROR);
        return admin;
    }

    private boolean isContainsMethod(List<Method> methodList, String path) {
        if (methodList == null || methodList.size() < 1) return false;
        for (Method method : methodList) {
            if (method == null) continue;
            if (method.getStatus() == -1) continue;
            if (StringUtils.isEmpty(method.getPath())) continue;

            if (method.getPath().equals(path))
                return true;

        }
        return false;
    }

    private List<Method> findMethods(List<Menu> menuList) {
        if (menuList == null || menuList.size() < 1) return null;

        List<Long> list = new ArrayList<>();
        for (Menu menu : menuList) {
                Long lid = menu.getId();
                if (list.contains(lid)) continue;

                list.add(lid);
        }

        List<Method> methodList = methodRepository.findByStatusAndMenuIdIn(0, list);
        if (methodList == null || methodList.size() < 1) return null;

        return methodList;
    }

    private List<Menu> findMenus(List<Role> roleList) {
        if (roleList == null || roleList.size() < 1) return null;

        List<Long> list = new ArrayList<>();
        for (Role role : roleList) {
            String menuIds = role.getMenuIds();//1,3,4,5,6,
            if (StringUtils.isEmpty(menuIds)) continue;

            for (String id : menuIds.split(",")) {
                if (StringUtils.isEmpty(id)) continue;

                Long lid = Long.valueOf(id);
                if (list.contains(lid)) continue;

                list.add(lid);
            }
        }

        //菜单
        List<Menu> menuList = menuRepository.findByStatusAndTypeAndIdIn(0, 1, list);
        if (menuList == null || menuList.size() < 1) return null;

        return menuList;
    }

    private List<Role> findRoles(Admin admin) {
        if (admin == null || StringUtils.isEmpty(admin.getRoleIds())) return null;

        String roleIds = admin.getRoleIds();//1,2,2,4,5,6,
        List<Long> list = new ArrayList<>();
        for (String id : roleIds.split(",")) {
            if (StringUtils.isEmpty(id)) continue;

            Long lid = Long.valueOf(id);
            if (list.contains(lid)) continue;

            list.add(lid);
        }

        List<Role> roleList = roleRepository.findByStatusAndIdIn(0, list);//启用的角色
        if (roleList == null || roleList.size() < 1) return null;

        return roleList;
    }

    private Admin findAdmin(AdminToken adminToken) {
        if (adminToken == null) return null;

        Admin admin = adminRepository.getOne(adminToken.getId());
        if (admin == null)
            throw new CarException(ErrorCode.ADMIN_LOGIN_TIMEOUT_ERROR);
        if (admin.getStatus() == -1)
            throw new CarException(ErrorCode.ADMIN_FREEZE_ERROR);

        return admin;
    }

    private AdminToken findAdminToken(HttpServletRequest request) {

        String token = getTokenFromCookie(request);
        if (StringUtils.isEmpty(token))
            throw new CarException(ErrorCode.ADMIN_LOGIN_TIMEOUT_ERROR);
        AdminToken adminToken = adminTokenRepository.findByTokenAndType(token, LOGIN_TOKEN_TYPE);
        if (adminToken == null || new Date().after(DateUtil.addMinute(adminToken.getCreateTime(), LOGTIN_TOKEN_TIMEOUT_MINUTE)))
            throw new CarException(ErrorCode.ADMIN_LOGIN_TIMEOUT_ERROR);

        return adminToken;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request == null) return null;
        return CookieUtil.getCookieValue(request, LOGIN_TOKEN);
    }
}
