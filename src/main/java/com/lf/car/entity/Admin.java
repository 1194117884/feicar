package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "varchar(50) comment '管理员用户名'")
    private String username;
    @Column(columnDefinition = "varchar(50) comment '管理员密码'")
    private String password;
    @Column(columnDefinition = "varchar(100) comment '管理员头像'")
    private String headPic;
    @Column(columnDefinition = "varchar(50) comment '管理员手机号'")
    private String phone;
    @Column(columnDefinition = "varchar(50) comment '管理员昵称'")
    private String nickname;
    @Column(columnDefinition = "varchar(100) comment '管理员角色ids逗号分隔'")
    private String roleIds;
    @Column(columnDefinition = "int(5) default 0 comment '状态【-1=禁用，0=正常】'")
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
