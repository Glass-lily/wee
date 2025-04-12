package com.weee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


@Data
public class User {
    private int id;
    private String no;
    private String name;
    private String password;
    private int age;
    private String sex;
    private String phone;
    private int roleId;
    @TableField("isValid")
    private String isValid;
}
