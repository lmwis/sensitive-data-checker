package com.lmwis.datachecker.common.error;

import com.fehead.lang.error.CommonError;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 4:10 下午
 * @Version: 1.0
 */
public enum BusinessErrorEnum implements CommonError {


    /**
     * 9开头为用户相关
     */
    USER_TOKEN_PARAM_INVALID(90001, "用户token参数无效"),


    /**
     * 8开头为app使用情况相关
     */
    USER_APP_BASE_ALREADY_EXIST(80001, "用户app已经存在"),
    ;

    BusinessErrorEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;



    @Override
    public int getErrorCode() {
        return errCode;
    }

    @Override
    public String getErrorMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
