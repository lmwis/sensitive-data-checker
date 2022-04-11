package com.lmwis.datachecker.pratice.setting;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/9 9:33 下午
 * @Version: 1.0
 */
public class ProxySetCommand {

    private static final String  HTTP_PROTOCOL = "http";
    private static final String  HTTPS_PROTOCOL = "https";

    private static final String[] SET_HTTP_PROXY_COMMAND = {
            "networksetup",
            "-setwebproxy",
            "Wi-Fi",
            "127.0.0.1",
            "8888"
    };

    private static final String[] SET_HTTPS_PROXY_COMMAND ={
            "networksetup",
            "-setsecurewebproxy",
            "Wi-Fi",
            "127.0.0.1",
            "8888"
    };
    private static final String[] SET_OFF_HTTP_PROXY_COMMAND = {
            "networksetup",
            "-setwebproxystate",
            "Wi-Fi",
            "off"
    };
    private static final String[] SET_OFF_HTTPS_PROXY_COMMAND ={
            "networksetup",
            "-setsecurewebproxystate",
            "Wi-Fi",
            "off"
    };

    public static void setProxy(int port) throws IOException {
        setProxy(String.valueOf(port),null);
    }

    public static void setHttpProxy(int port) throws IOException {
        setProxy(String.valueOf(port),HTTP_PROTOCOL);
    }
    public static void setHttpsProxy(int port) throws IOException {
        setProxy(String.valueOf(port),HTTPS_PROTOCOL);
    }

    /**
     * 设置代理
     * @param port
     * @throws IOException
     */
    public static void setProxy(String port,String protocol) throws IOException {
        if (!StringUtils.isBlank(protocol)){
            if (StringUtils.equals(protocol,HTTP_PROTOCOL)){
                SET_HTTP_PROXY_COMMAND[SET_HTTP_PROXY_COMMAND.length-1]=port;
                Runtime.getRuntime().exec(SET_HTTP_PROXY_COMMAND);
            }else {
                SET_HTTPS_PROXY_COMMAND[SET_HTTPS_PROXY_COMMAND.length-1]=port;
                Runtime.getRuntime().exec(SET_HTTPS_PROXY_COMMAND);
            }
        }else {
            SET_HTTP_PROXY_COMMAND[SET_HTTP_PROXY_COMMAND.length-1]=port;
            Runtime.getRuntime().exec(SET_HTTP_PROXY_COMMAND);
            SET_HTTPS_PROXY_COMMAND[SET_HTTPS_PROXY_COMMAND.length-1]=port;
            Runtime.getRuntime().exec(SET_HTTPS_PROXY_COMMAND);
        }
    }

    /**
     * 关闭代理
     * @throws IOException
     */
    public static void setOffProxy() throws IOException {
        Runtime.getRuntime().exec(SET_OFF_HTTP_PROXY_COMMAND);
        Runtime.getRuntime().exec(SET_OFF_HTTPS_PROXY_COMMAND);
    }
}
