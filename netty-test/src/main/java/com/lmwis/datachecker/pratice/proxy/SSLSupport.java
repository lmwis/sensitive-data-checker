package com.lmwis.datachecker.pratice.proxy;

import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/27 4:13 下午
 * @Version: 1.0
 */
public class SSLSupport {

    private static final String PROTOCOL = "TLS";

    private static final String PRIVATE_KEY = "Ak023456";

    private static final String CA_PATH = "";

    private static final String CA_KEY_PATH = "";

    private static final String PK_PATH = "classpath:proxy.keystore";

    private static SSLContext instance;


    public static SSLContext getServerContext() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {

        if (instance!=null){
            return instance;
        }

        // 加载resource下的
        File pkFile = ResourceUtils.getFile(PK_PATH);

        KeyStore ks = KeyStore.getInstance("JKS");
        // 服务端证书
        InputStream in = new FileInputStream(pkFile);
        ks.load(in, PRIVATE_KEY.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        // 初始化密钥管理器
        kmf.init(ks, PRIVATE_KEY.toCharArray());

        // 根据协议创建
        instance = SSLContext.getInstance(PROTOCOL);
        instance.init(kmf.getKeyManagers(), null, null);
        return instance;
    }
}
