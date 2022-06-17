package com.lmwis.datachecker.computer.net.proxy.mock.wiredog;

public interface HttpRequestInterceptor {
	
	public boolean isHit(HttpRequest request);
}
