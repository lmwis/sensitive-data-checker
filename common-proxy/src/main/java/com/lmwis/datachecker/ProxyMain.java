package com.lmwis.datachecker;

import com.lmwis.datachecker.setting.JvmHookSetting;
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
        // 为网络设置代理
        ProxySetCommand.setProxy(port);
        // 注册jvm关闭回调
        JvmHookSetting.registerShutdownHook();


        EasyHttpProxyServer.getInstance().start(port);
        Runtime.getRuntime().exit(0);
    }
}
