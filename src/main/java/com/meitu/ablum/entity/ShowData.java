package com.meitu.ablum.entity;

import com.meitu.ablum.controller.IndexController;
import lombok.Data;

/**
 * @author yanrangnan
 * @date 2019/12/17
 */
@Data
public class ShowData {
    private Integer id;          //id
    private String username;    //用户名
    private Integer bug;         //漏测bug数
    private Integer historyMissingRate; //历史漏测数
    private Integer validBug;   //有效bug总数
    private Float totalLogged;
    private Float forecastTime;

}
