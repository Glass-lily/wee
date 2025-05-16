package com.weee.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Goods)实体类
 *
 * @author hk
 * @since 2025-04-14 12:20:47
 */
@Data
public class Goods implements Serializable {

/**
     * 主键
     */
    private Integer id;
/**
     * 名字
     */
    private String name;
/**
     * 知识大类
     */
    private Integer storage;
/**
     * 标签关键词
     */
    private Integer goodstype;
/**
     * 审核状态 (0: 未审核, 1: 通过, 2: 未通过)
     */
    private Integer count;
/**
     * 备注:上传图片用的
     */
    private String remark;


}

