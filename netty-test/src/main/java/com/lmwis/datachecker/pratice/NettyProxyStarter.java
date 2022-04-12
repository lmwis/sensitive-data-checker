package com.lmwis.datachecker.pratice;

import com.lmwis.datachecker.pratice.setting.JvmHookSetting;
import com.lmwis.datachecker.pratice.setting.ProxySetCommand;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/10 5:55 下午
 * @Version: 1.0
 */
public class NettyProxyStarter {
    private static final int HTTP_PORT = 8888;
    private static final int HTTPS_PORT = 8889;
    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, InterruptedException, KeyManagementException {
        // 为网络设置代理
        ProxySetCommand.setHttpProxy(HTTP_PORT);
        ProxySetCommand.setHttpsProxy(HTTPS_PORT);
        // 注册jvm关闭回调
        JvmHookSetting.registerShutdownHook();

        new HttpNettyServer().start(HTTP_PORT);
        new HttpsNettyServer().start(HTTPS_PORT);
    }
}
