package com.meitu.ablum.entity;

import lombok.Data;

@Data
public class ResponseData {
    private int code;
    private String msg;
    private Object data;
}
