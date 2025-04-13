package com.weee.entity;

import java.io.Serializable;

/**
 * (Menu)实体类
 *
 * @author makejava
 * @since 2025-04-13 14:10:22
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 976828112175479086L;

    private Integer id;
    /**
     * 菜单编码
     */
    private String menucode;
    /**
     * 菜单名字
     */
    private String menuname;
    /**
     * 菜单级别
     */
    private String menulevel;
    /**
     * 菜单的父code
     */
    private String menuparentcode;
    /**
     * 点击触发的函数
     */
    private String menuclick;
    /**
     * 权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用
     */
    private String menuright;

    private String menucomponent;

    private String menuicon;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenucode() {
        return menucode;
    }

    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenulevel() {
        return menulevel;
    }

    public void setMenulevel(String menulevel) {
        this.menulevel = menulevel;
    }

    public String getMenuparentcode() {
        return menuparentcode;
    }

    public void setMenuparentcode(String menuparentcode) {
        this.menuparentcode = menuparentcode;
    }

    public String getMenuclick() {
        return menuclick;
    }

    public void setMenuclick(String menuclick) {
        this.menuclick = menuclick;
    }

    public String getMenuright() {
        return menuright;
    }

    public void setMenuright(String menuright) {
        this.menuright = menuright;
    }

    public String getMenucomponent() {
        return menucomponent;
    }

    public void setMenucomponent(String menucomponent) {
        this.menucomponent = menucomponent;
    }

    public String getMenuicon() {
        return menuicon;
    }

    public void setMenuicon(String menuicon) {
        this.menuicon = menuicon;
    }

}

