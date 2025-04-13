package com.weee.entity;

import java.io.Serializable;

/**
 * (Storage)实体类
 *
 * @author makejava
 * @since 2025-04-13 23:02:21
 */
public class Storage implements Serializable {
    private static final long serialVersionUID = 744818835339882326L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 仓库名
     */
    private String name;
    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

