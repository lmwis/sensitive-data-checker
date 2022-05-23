package com.lmwis.datachecker.center.constant;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 3:31 下午
 * @Version: 1.0
 */
public enum UsageEventType {
    TO_FRONT(1,"切换到前台"),
    TO_BACK(2,"切换到后台")

    ;

    private int code;

    private String desc;

    UsageEventType(int code, String desc) {
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
