package com.lmwis.datachecker.computer.net.proxy.mock.wiredog;

public interface HttpResponseInterceptor {
	
	public boolean isHit(HttpResponse response);
}
