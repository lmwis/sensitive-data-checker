package com.lmwis.datachecker;

import com.lmwis.datachecker.setting.ProxySetCommand;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/9 9:23 下午
 * @Version: 1.0
 */
public class ProxyMain {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ProxySetCommand.setProxy(port);
        EasyHttpProxyServer.getInstace().start(port);
    }
}
