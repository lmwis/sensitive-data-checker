package com.lmwis.datachecker.computer.net.proxy.server;

import io.netty.channel.ChannelFuture;

public interface Server {

	public ChannelFuture start();
	
	public void onClose(Object hook);
}
