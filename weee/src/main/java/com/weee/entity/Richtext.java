package com.weee.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Richtext)实体类
 *
 * @author makejava
 * @since 2025-04-14 15:43:55
 */
public class Richtext implements Serializable {
//    private static final long serialVersionUID = 363001600613376781L;

    private Integer id;

    private String title;

    private String content;

    private Date createdAt;

    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

