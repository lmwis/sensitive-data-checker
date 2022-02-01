package com.lmwis.datachecker.init;

import org.junit.Test;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/7 2:18 下午
 * @Version: 1.0
 */
public class ChangeSystemProxySettingTest {

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

    @Test
    public void doChange() throws IOException {
        System.out.println("turn off system proxy");

        Process ls = Runtime.getRuntime().exec(SET_HTTP_PROXY_COMMAND);
        Runtime.getRuntime().exec(SET_HTTPS_PROXY_COMMAND);

//        Process ls = Runtime.getRuntime().exec("ls");
//        InputStream inputStream = ls.getInputStream();
//        IOUtils.readLines(inputStream).stream().forEach(System.out::println);

    }

    @Test
    public void setOffProxy() throws IOException {
        System.out.println(11);

        Process ls = Runtime.getRuntime().exec(SET_OFF_HTTP_PROXY_COMMAND);
        Runtime.getRuntime().exec(SET_OFF_HTTPS_PROXY_COMMAND);

//        Process ls = Runtime.getRuntime().exec("ls");
//        InputStream inputStream = ls.getInputStream();
//        IOUtils.readLines(inputStream).stream().forEach(System.out::println);

    }
}
