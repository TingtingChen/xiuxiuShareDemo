package com.meitu.ablum.entity;

import lombok.Data;

/**
 * @author yanrangnan
 * @date 2019/12/15
 */
@Data
public class Tester {
    private Integer id;
    private String username;
    private String nickname;
    private String email;
    private int bug;
    /**
     * 预估用时(h)
     */
    private float forecastTime;


}
