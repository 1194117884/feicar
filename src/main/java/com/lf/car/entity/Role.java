package com.lf.car.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "varchar(50) comment '角色名'")
    private String name;
    @Column(columnDefinition = "varchar(100) comment '简介'")
    private String introduction;
    @Column(columnDefinition = "int(5) default 0 comment '状态【-1=停用，0=正常】'")
    private Integer status;
    @Column(columnDefinition = "varchar(200)  comment '拥有的菜单，逗号分割'")
    private String menuIds;

    @Transient
    private List<Menu> menuList;

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

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}
