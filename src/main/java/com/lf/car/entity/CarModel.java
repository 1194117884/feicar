package com.lf.car.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
public class CarModel {

    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar(225) comment '车型名'")
    private String name;
    @Column(columnDefinition = "bigint(16) comment '车系列ID'")
    private Long seriesId;
    @Column(columnDefinition = "int(5) comment '上市年份'")
    private Integer year;
    @Column(columnDefinition = "int(5) comment '上市月份'")
    private Integer month;
    @Column(columnDefinition = "int(5) comment '库存量'")
    private Integer inventory;
    @Column(columnDefinition = "decimal(19,2) comment '厂家指导价格'")
    private BigDecimal guidePrice;
    @Column(columnDefinition = "decimal(19,2) comment '政府优惠价格'")
    private BigDecimal policyPrice;
    private Date createTime;
    @Column(columnDefinition = "int(5) comment '状态【-1=下架，0=正常】'")
    private Integer status;


    @Transient
    private CarSeries seriesInfo;
    @Transient
    private Map<String, List<CarModelFeature>> features;

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

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(BigDecimal policyPrice) {
        this.policyPrice = policyPrice;
    }

    public CarSeries getSeriesInfo() {
        return seriesInfo;
    }

    public void setSeriesInfo(CarSeries seriesInfo) {
        this.seriesInfo = seriesInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, List<CarModelFeature>> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, List<CarModelFeature>> features) {
        this.features = features;
    }
}
