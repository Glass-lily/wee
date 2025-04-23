package com.weee.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Record)实体类
 *
 * @author makejava
 * @since 2025-04-23 13:58:50
 */
public class Record implements Serializable {

/**
     * 主键
     */
    private Integer id;
/**
     * 物品id
     */
    private Integer goods;
/**
     * 使用人
     */
    private Integer userid;
/**
     * 操作人id
     */
    private Integer adminId;
/**
     * 数量
     */
    private Integer count;
/**
     * 操作时间
     */
    private Date createtime;
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

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

