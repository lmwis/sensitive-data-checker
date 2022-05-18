package com.lmwis.datachecker.proxy.utils;

import com.google.gson.Gson;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 11:17 下午
 * @Version: 1.0
 */
public class GsonUtil {
    static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }
    
}
