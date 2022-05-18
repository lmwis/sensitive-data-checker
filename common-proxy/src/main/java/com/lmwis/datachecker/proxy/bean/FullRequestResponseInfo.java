package com.lmwis.datachecker.proxy.bean;

import com.lmwis.datachecker.computer.pojo.HttpRequestInfo;
import com.lmwis.datachecker.computer.pojo.HttpResponseInfo;
import lombok.Data;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/11 10:38 下午
 * @Version: 1.0
 */
@Data
public class FullRequestResponseInfo {

    private HttpRequestInfo requestInfo;
    private HttpResponseInfo responseInfo;
}
