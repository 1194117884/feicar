package com.lf.car.controller.resps;

import com.lf.car.entity.Admin;
import com.lf.car.entity.AdminToken;

public class AdminLoginInfo {

    private Admin admin;
    private AdminToken adminToken;

    public AdminToken getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(AdminToken adminToken) {
        this.adminToken = adminToken;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
