package com.lf.car.entity;

import java.util.Date;
import java.util.List;

public class LcConversation {

    private Object attr;
    private String objectId;
    private String c;
    private Date lm;
    private List<String> m;
    private List<String> mu;
    private String name;
    private Boolean tr;
    private Boolean sys;
    private Boolean unique;

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Date getLm() {
        return lm;
    }

    public void setLm(Date lm) {
        this.lm = lm;
    }

    public List<String> getM() {
        return m;
    }

    public void setM(List<String> m) {
        this.m = m;
    }

    public List<String> getMu() {
        return mu;
    }

    public void setMu(List<String> mu) {
        this.mu = mu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTr() {
        return tr;
    }

    public void setTr(Boolean tr) {
        this.tr = tr;
    }

    public Boolean getSys() {
        return sys;
    }

    public void setSys(Boolean sys) {
        this.sys = sys;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }
}
