package com.lf.car.model;

import java.util.Map;

public class CarModelFeatureMapping {

    private Long seriesId;
    private String feature;
    private String featureType;

    private Map<Long, String> modelInfos;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public Map<Long, String> getModelInfos() {
        return modelInfos;
    }

    public void setModelInfos(Map<Long, String> modelInfos) {
        this.modelInfos = modelInfos;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
