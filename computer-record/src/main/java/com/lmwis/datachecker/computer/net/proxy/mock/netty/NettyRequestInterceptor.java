package com.lmwis.datachecker.computer.net.proxy.mock.netty;

import io.netty.handler.codec.http.FullHttpRequest;

public interface NettyRequestInterceptor {

	public boolean isHit(FullHttpRequest request);
}
