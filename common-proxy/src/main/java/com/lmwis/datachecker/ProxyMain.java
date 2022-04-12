package com.lmwis.datachecker;

import com.lmwis.datachecker.setting.JvmHookSetting;
import com.lmwis.datachecker.setting.ProxySetCommand;

import java.io.IOException;

/**
 * @Description: 启动代理服务器
 * @Author: lmwis
 * @Data: 2022/1/9 9:23 下午
 * @Version: 1.0
 */
public class ProxyMain {
    public static void starterServer(String[] args) throws IOException {
        int port = 8888;
        // 为网络设置代理
        ProxySetCommand.setProxy(port);
        // 注册jvm关闭回调
        JvmHookSetting.registerShutdownHook();

        EasyHttpProxyServer.getInstance().start(port);
        Runtime.getRuntime().exit(0);
    }
}
