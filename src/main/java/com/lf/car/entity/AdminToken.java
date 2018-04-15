package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AdminToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "bigint(19) comment '管理员ID'")
    private Long adminId;
    @Column(columnDefinition = "varchar(50) comment 'token类型【login=登陆】'")
    private String type;
    @Column(columnDefinition = "varchar(50) comment 'token值'")
    private String token;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
