package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar(100) comment '用户名字'")
    private String username;
    @Column(columnDefinition = "varchar(50) comment '密码MD5（MD5（））'")
    private String password;
    @Column(columnDefinition = "varchar(11) comment '手机号'")
    private String phone;
    @Column(columnDefinition = "varchar(50) comment '昵称'")
    private String nickname;
    private Date registerTime;
    @Column(columnDefinition = "varchar(200) comment '地址$分割'")
    private String address;
    @Column(columnDefinition = "datetime comment '用户生日'")
    private Date birthday;
    @Column(columnDefinition = "int(5) comment '状态【-1=禁用，0=正常】'")
    private Integer status;//-1=禁止,0=正常

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
