package com.lmwis.datachecker.computer.net.ssl;

import com.lmwis.datachecker.computer.net.ssl.common.HttpsKeyStore;
import com.lmwis.datachecker.computer.net.ssl.common.SSLException;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.security.KeyStore;

@Slf4j
public class HttpSslContextFactory {

	private static final String PROTOCOL = "TLSv1.2";// 客户端可以指明为SSLv3或者TLSv1.2
	/** 针对于服务器端配置 */
	private static SSLContext sslContext = null;

	public static SSLEngine createSSLEngine(String domain) {
		SSLContext serverContext = null;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(HttpsKeyStore.getKeyStoreStream(domain), HttpsKeyStore.getKeyStorePassword());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, HttpsKeyStore.getCertificatePassword());
			serverContext = SSLContext.getInstance(PROTOCOL);
			serverContext.init(kmf.getKeyManagers(), null, null);
		} catch (Exception e) {
			log.error("Failed to initialize the server SSLContext", e);
			throw new SSLException("Failed to initialize the server SSLContext", e);
		}
		sslContext = serverContext;
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(false);
        return sslEngine ;
    }
}
