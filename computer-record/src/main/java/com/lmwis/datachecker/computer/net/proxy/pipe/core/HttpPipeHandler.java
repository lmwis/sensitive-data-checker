package com.lmwis.datachecker.computer.net.proxy.pipe.core;

import com.lmwis.datachecker.computer.net.proxy.facade.PipeContext;
import com.lmwis.datachecker.computer.net.proxy.facade.PipeInvokeChain;
import com.lmwis.datachecker.computer.net.proxy.mock.MockHandler;
import com.lmwis.datachecker.computer.util.HttpMessageUtil;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpPipeHandler extends StandardPipeHandler {

	public HttpPipeHandler(FrontPipe front, PipeInvokeChain fullPipeHandler, PipeContext wtContext,
						   MockHandler mockHandler) {
		super(front, fullPipeHandler, wtContext, mockHandler);
	}

	@Override
	protected void connect(EventLoopGroup eventLoopGroup, FullHttpRequest request) throws InterruptedException {
		HttpMessageUtil.InetAddress inetAddress = HttpMessageUtil.parse2InetAddress(request, false);
		if (inetAddress == null) {
			close();
			return ;
		}
		
		wtContext.appendRequest(request);

		currentBack = super.select(inetAddress.getHost(), inetAddress.getPort());
		if (currentBack == null) {
			currentBack = initBackpipe(eventLoopGroup, inetAddress);
		}
		if (!currentBack.isActive()) {
			currentBack.connect().addListener(f -> {
				if (!f.isSuccess()) {
					log.error("[" + wtContext.getId() + "] server connect error. cause={}", f.cause().getMessage());
					fullPipeHandler.serverConnectFailed(wtContext, f.cause());
					close();
					return;
				}
				wtContext.registServer(currentBack.getChannel());
				fullPipeHandler.serverConnect(wtContext, inetAddress);
				currentBack.getChannel().pipeline().addLast(this);
			}).sync();
		}
	}

	@Override
	protected BackPipe initBackpipe0(EventLoopGroup eventLoopGroup, HttpMessageUtil.InetAddress InetAddress) {
		return new BackPipe(eventLoopGroup, InetAddress.getHost(), InetAddress.getPort(), false);
	}
}
