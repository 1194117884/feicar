package com.lf.car.controller.resps;

import com.lf.car.entity.User;
import com.lf.car.entity.UserToken;

public class LoginInfo {

    private User user;
    private UserToken userToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
