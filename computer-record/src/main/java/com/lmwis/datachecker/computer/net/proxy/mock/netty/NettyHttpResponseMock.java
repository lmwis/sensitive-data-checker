package com.lmwis.datachecker.computer.net.proxy.mock.netty;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface NettyHttpResponseMock {

	public FullHttpResponse eval(FullHttpRequest httpRequest);
}
