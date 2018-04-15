package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CarSeries {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "varchar(225) comment '车系列名'")
    private String name;
    @Column(columnDefinition = "varchar(225) comment '车系类型'")
    private String type;
    @Column(columnDefinition = "varchar(225) comment '车系列主图'")
    private String mainPic;
    @Column(columnDefinition = "varchar(225) comment '车系列简介'")
    private String introduction;
    @Column(columnDefinition = "int(5) comment '状态【-1=下架，0=正常】'")
    private Integer status;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
