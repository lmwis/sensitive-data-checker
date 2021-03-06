package com.lmwis.datachecker.computer.net.ssl.common;

import com.lmwis.datachecker.computer.net.ssl.CA_Station;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class HttpsKeyStore {

	public static InputStream getKeyStoreStream(String domain) {
		try {
			// 创建证书
			return CA_Station.createWithCache(domain);
		} catch (Exception e) {
			log.error("init key_store failure, domain=" + domain, e);
			throw new SSLException("init key store failed", e);
		}
	}

	public static char[] getCertificatePassword() {
		return Arguments.certificatePassword.toCharArray();
	}

	public static char[] getKeyStorePassword() {
		return Arguments.keystorePassword.toCharArray();
	}
}

class Arguments {
	public static String certificatePassword = "wiretiger@123";
	public static String keystorePassword = "wiretiger@123";
}
