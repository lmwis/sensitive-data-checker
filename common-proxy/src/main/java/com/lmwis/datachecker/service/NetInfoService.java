package com.lmwis.datachecker.service;

import com.lmwis.datachecker.pojo.HttpRequestInfo;
import com.lmwis.datachecker.pojo.HttpResponseInfo;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 9:59 下午
 * @Version: 1.0
 */
public interface NetInfoService {

    public Boolean saveHttpInfo(HttpRequestInfo requestInfo, HttpResponseInfo responseInfo);

}
