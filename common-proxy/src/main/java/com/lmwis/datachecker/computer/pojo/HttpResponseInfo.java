package com.lmwis.datachecker.computer.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/19 11:47 上午
 * @Version: 1.0
 */
@Data
@ToString
public class HttpResponseInfo {

    public HttpResponseInfo(HttpRequestInfo requestInfo){
        this.requestInfo = requestInfo;
    }

    HttpRequestInfo requestInfo;

    private String httpVersion;

    private int responseCode;

    private Map<String, String> headers;

    private String content;

    private Date gmtCreate;

    private Date gmtModify;


}
