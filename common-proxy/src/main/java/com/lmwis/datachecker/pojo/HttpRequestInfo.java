package com.lmwis.datachecker.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/17 10:20 下午
 * @Version: 1.0
 */
@Data
@Builder
@ToString
public class HttpRequestInfo {

    private String hostname;

    private int port;

    private String protocol;

    private String method;

    private String url;

    private Map<String, String> headers;

    private String content;

    private Date gmtCreate;

    private Date gmtModify;

}
