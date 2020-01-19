package com.meitu.ablum.entity;

public class Result {


    private static final String SUCCESS = "成功";
    private static final String ERROR = "失败";

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 响应状态code，因为前台layui默认0为响应成功，所以此处默认为0
     */
    private Integer code = 0;

    /**
     * 数据总条数
     */
    private Long count = 0L;

    /**
     * 返回是否成功
     */
    private Boolean result = false;

    /**
     * 返回提示信息
     */
    private String msg = "";

    /**
     * 返回数据信息
     */
    private Object data;

    public Result() {
    }
}
