package com.lf.car.service;

import com.lf.car.controller.resps.AdminLoginInfo;
import com.lf.car.entity.Admin;
import com.lf.car.entity.AdminToken;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.AdminRepository;
import com.lf.car.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminTokenService adminTokenService;

    public AdminLoginInfo loginByUsernameAndPassword(String username, String password) {
        logger.info("管理员：{},登录", username);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new CarException(ErrorCode.PARAM_ERROR);

        //admin info
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null)
            throw new CarException(ErrorCode.ADMIN_NOT_EXISTS);
        if (!MD5Util.md5(password).equals(admin.getPassword()))
            throw new CarException(ErrorCode.ADMIN_PASSWORD_ERROR);
        Admin admin1 = new Admin();
        BeanUtils.copyProperties(admin, admin1);
        admin1.setPassword("(  ૢ⁼̴̤̆ ㉨ ⁼̴̤̆ ૢ)♡ 约吗？");
        //admin login token
        AdminToken adminToken = adminTokenService.saveAdminToken(admin1);
        if (adminToken == null)
            throw new CarException(ErrorCode.SYS_ERROR);

        AdminLoginInfo loginInfo = new AdminLoginInfo();
        loginInfo.setAdmin(admin1);
        loginInfo.setAdminToken(adminToken);
        return loginInfo;
    }

}
