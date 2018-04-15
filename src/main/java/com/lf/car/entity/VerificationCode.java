package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class VerificationCode {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "varchar(15) comment '手机号'")
    private String phone;
    @Column(columnDefinition = "varchar(50) comment '验证码'")
    private String code;
    @Column(columnDefinition = "varchar(50) comment '验证码类型'")
    private String type;
    @Column(columnDefinition = "int(5) comment '状态【0=未验证，1=已验证】'")
    private Integer status;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
