package com.lf.car.controller.requs;

public class FindCarModelArgs extends BaseRequest {

    private Integer status;
    private Long seriesId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
