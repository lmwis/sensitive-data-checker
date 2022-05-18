package com.lmwis.datachecker.proxy.handler;

import com.lmwis.datachecker.computer.pojo.HttpRequestInfo;
import com.lmwis.datachecker.computer.pojo.HttpResponseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/11 10:22 下午
 * @Version: 1.0
 */
public class ContextMap {

    static Map<Long, HttpRequestInfo> requestInfoMap = new HashMap<>();
    static Map<Long, HttpResponseInfo> responseInfoMap = new HashMap<>();

    public static HttpRequestInfo getRequest(Long traceId){
        if (traceId!=null && traceId!=0){
            return requestInfoMap.get(traceId);
        }
        return null;
    }
    public static HttpResponseInfo getResponse(Long traceId){
        if (traceId!=null && traceId!=0){
            return responseInfoMap.get(traceId);
        }
        return null;
    }

    public static void putRequest(Long traceId, HttpRequestInfo requestInfo){
        requestInfoMap.putIfAbsent(traceId,requestInfo);
    }
    public static void putResponse(Long traceId, HttpResponseInfo responseInfo){
        responseInfoMap.putIfAbsent(traceId,responseInfo);
    }
}
