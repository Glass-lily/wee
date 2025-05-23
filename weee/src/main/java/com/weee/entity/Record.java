package com.weee.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.sql.Timestamp;
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
    private Timestamp createtime;
/**
     * 备注
     */
    private String remark;


    @TableField(exist = false)
    private String action;


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

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

