package com.lf.car.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "bigint(19) comment '父亲菜单ID'")
    private Long pmId;
    @Column(columnDefinition = "varchar(50) comment '菜单名'")
    private String name;
    @Column(columnDefinition = "int(5) default 0 comment '状态【-1=关闭，0=开启】'")
    private Integer status;
    @Column(columnDefinition = "int(5) default 0 comment '类型【0=目录，1=菜单】'")
    private Integer type;

    @Transient
    private List<Menu> subMenuList;//目录下的菜单

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
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

    public List<Menu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<Menu> subMenuList) {
        this.subMenuList = subMenuList;
    }
}
