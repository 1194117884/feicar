package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CarModelFeature {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "bigint(19) comment '车型ID'")
    private Long modelId;
    @Column(columnDefinition = "bigint(19) comment '车系列ID'")
    private Long seriesId;
    @Column(columnDefinition = "varchar(225) comment '特性'")
    private String feature;
    @Column(columnDefinition = "varchar(225) comment '特性分类'")
    private String featureType;
    @Column(columnDefinition = "varchar(225) comment '车型特性信息'")
    private String modelInfo;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
