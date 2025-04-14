package com.weee.entity;

import java.io.Serializable;

/**
 * (Goodstype)实体类
 *
 * @author hk
 * @since 2025-04-14 11:57:21
 */
public class Goodstype implements Serializable {

/**
     * 主键
     */
    private Integer id;
/**
     * 分类名
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

