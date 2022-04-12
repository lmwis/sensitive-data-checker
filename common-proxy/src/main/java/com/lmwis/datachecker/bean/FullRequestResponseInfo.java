package com.lmwis.datachecker.bean;

import com.lmwis.datachecker.pojo.HttpRequestInfo;
import com.lmwis.datachecker.pojo.HttpResponseInfo;
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
