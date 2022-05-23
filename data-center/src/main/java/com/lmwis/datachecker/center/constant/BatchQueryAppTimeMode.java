package com.lmwis.datachecker.center.constant;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 2:53 下午
 * @Version: 1.0
 */
public enum BatchQueryAppTimeMode {

    LAST_HOUR(1,"最近一小时"),
    CURRENT_DAY(2,"今天"),
    CUSTOMIZE(3,"自定义")

    ;

    private int code;

    private String desc;

    BatchQueryAppTimeMode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
