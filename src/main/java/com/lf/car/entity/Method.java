package com.lf.car.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Method {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "bigint(19) default 0 comment '菜单ID'")
    private Long menuId;
    @Column(columnDefinition = "varchar(100) comment '方法名'")
    private String name;
    @Column(columnDefinition = "varchar(100) comment '系统内部路径'")
    private String path;
    @Column(columnDefinition = "integer(5) default 0 comment '状态【-1=停用，0=正常】'")
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
