package com.lmwis.datachecker.computer.net.proxy.server;


import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfig;
import com.lmwis.datachecker.computer.net.proxy.mock.MockHandler;

public class ServerBuilder {
	
	private JmitmCoreConfig config;
	

	public static ServerBuilder init(JmitmCoreConfig config) {
		ServerBuilder serverBuilder = new ServerBuilder();
		serverBuilder.config = config;
		return serverBuilder;
	}
	
	private ServerBuilder() {
	}
	
	public Server build() {
		DefaultServer server = new DefaultServer(config);
		server.setMockHandler(new MockHandler(config.getMockList()));
		return server;
	}
}
