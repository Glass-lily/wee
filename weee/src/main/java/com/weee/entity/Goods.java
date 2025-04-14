package com.weee.entity;

import java.io.Serializable;

/**
 * (Goods)实体类
 *
 * @author hk
 * @since 2025-04-14 12:20:47
 */
public class Goods implements Serializable {

/**
     * 主键
     */
    private Integer id;
/**
     * 货名
     */
    private String name;
/**
     * 仓库
     */
    private Integer storage;
/**
     * 分类
     */
    private Integer goodstype;
/**
     * 数量
     */
    private Integer count;
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

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Integer getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(Integer goodstype) {
        this.goodstype = goodstype;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

