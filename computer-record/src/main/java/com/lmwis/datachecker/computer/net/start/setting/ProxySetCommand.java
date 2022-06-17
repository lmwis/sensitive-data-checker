package com.lmwis.datachecker.computer.net.start.setting;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/9 9:33 下午
 * @Version: 1.0
 */
public class ProxySetCommand {

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
        setProxy(port + "");
    }
    public static void setProxy(String port) throws IOException {
        SET_HTTP_PROXY_COMMAND[SET_HTTP_PROXY_COMMAND.length-1]=port;
        SET_HTTPS_PROXY_COMMAND[SET_HTTPS_PROXY_COMMAND.length-1]=port;
        Runtime.getRuntime().exec(SET_HTTP_PROXY_COMMAND);
        Runtime.getRuntime().exec(SET_HTTPS_PROXY_COMMAND);
    }

    public static void setOffProxy() throws IOException {
        Runtime.getRuntime().exec(SET_OFF_HTTP_PROXY_COMMAND);
        Runtime.getRuntime().exec(SET_OFF_HTTPS_PROXY_COMMAND);
    }
}
