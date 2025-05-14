package com.weee.entity;

import lombok.Data;

@Data
public class SensitiveWord {
    private Long id;
    private String word;
    private java.util.Date createdAt;
}


