package com.meitu.ablum.entity;

import lombok.Data;

@Data
public class BugEntity {
    private String type;  //类型
    private String keyWord; //关键字
    private String theme;   //主题
    private String bugResult; //bug解决结果
    private String status;  //状态
    private String bugFind; //bug如何发现
    private String bugGrade; //严重程度
    private String reporter; //报告人
    private String agent; //经办人
    private String affectVersion; //影响版本
    private String createDate; //创建日期
    private String updateDate; //更新日期
    private String bugLabel; //标签

}
