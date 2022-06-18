package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/17 4:09 下午
 * @Version: 1.0
 */
@Data
@Builder
@ToString
public class NetInfoResult {

    long id;

    long date;

    String host;

    String method;

    String url;

    int statusCode;

    String requestHeaders;

    String requestBody;

    String responseHeaders;

    String responseBody;
}
