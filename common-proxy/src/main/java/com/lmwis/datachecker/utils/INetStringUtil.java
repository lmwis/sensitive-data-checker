package com.lmwis.datachecker.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/19 8:40 下午
 * @Version: 1.0
 */
public class INetStringUtil {

    public static final String HTTP_FLAG = "HTTP";
    public static final String HTTPS_FLAG = "HTTPS";

    public static String resolveProtocolFromUrl(String url){

        if (StringUtils.isBlank(url)){
            return null;
        }
        if (url.toUpperCase(Locale.ROOT).contains(HTTP_FLAG)){
            return HTTP_FLAG;
        }else if (url.toUpperCase(Locale.ROOT).contains(HTTPS_FLAG)){
            return HTTPS_FLAG;
        }else {
            if (resolvePorFromUrl(url)==443){
                return HTTPS_FLAG;
            }
        }
        return HTTP_FLAG;
    }

    public static String resolveHostnameFromUrl(String url){
        if (StringUtils.isBlank(url)){
            return null;
        }
        return doResolveUrl(url)[0];
    }
    public static int resolvePorFromUrl(String url){
        if (StringUtils.isBlank(url)){
            return 0;
        }
        return Integer.parseInt(doResolveUrl(url)[1]);
    }
    private static String[] doResolveUrl(String url){
        url = url.toUpperCase(Locale.ROOT);

        String[] res = new String[2];
        String temp = "";
        if (!url.contains("/")){
            temp = url;
        }else {
            temp = url.substring(url.indexOf("//")+2);
            temp = temp.substring(0, temp.indexOf("/"));
        }
        String[] split = temp.split(":");
        if (url.contains(HTTP_FLAG)){
            // http协议
            if (split.length>1){
               res[1] = split[1];
            }else{
                res[1] = "80";
            }
        }else{
            // https协议
            if (split.length>1){
                res[1] = split[1];
            }else{
                res[1] = "443";
            }
        }
        res[0] = split[0].toLowerCase(Locale.ROOT);

        return res;
    }
}
