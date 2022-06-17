package com.lmwis.datachecker.computer.net.start.provider;

import com.lmwis.datachecker.computer.net.console.common.chain.PipeManagerInvokeChain;
import com.lmwis.datachecker.computer.net.console.common.chain.SessionManagerInvokeChain;
import com.lmwis.datachecker.computer.net.console.http.ConsoleServer;
import com.lmwis.datachecker.computer.net.console.http.config.JmitmConsoleConfig;
import com.lmwis.datachecker.computer.net.console.websocket.WebSocketServer;
import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfig;
import com.lmwis.datachecker.computer.net.proxy.mock.MockHandler;
import com.lmwis.datachecker.computer.net.proxy.server.DefaultServer;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmitmServerProvider {
	
	private DefaultServer proxyServer;
	private ConsoleServer consoleServer;
	private WebSocketServer webSocketServer;
	
	public JmitmServerProvider(JmitmCoreConfig coreConfig, JmitmConsoleConfig consoleConfig) {
		PipeManagerInvokeChain pipeManagerInvokeChain = new PipeManagerInvokeChain(null, consoleConfig.getPipeHistory());
		// proxy-server
		this.proxyServer = new DefaultServer(coreConfig);
		this.proxyServer.setMockHandler(new MockHandler(coreConfig.getMockList()));
		this.proxyServer.setInvokeChainInit(()-> {
			return new SessionManagerInvokeChain(pipeManagerInvokeChain, consoleConfig.getSessionHistory());
		});
		
		// console HTTP-server
		if (consoleConfig.getHttpPort() != null) {
			this.consoleServer = new ConsoleServer(consoleConfig);
		}
		
		// console WebSocket-server
		if (consoleConfig.getWebSocketPort() != null) {
			this.webSocketServer = new WebSocketServer(consoleConfig.getWebSocketPort());
		}
	}

	public void start() throws InterruptedException {
		ChannelFuture proxyStartFuture = proxyServer.start();
		if (consoleServer != null) {
			try {
				// 不启动jetty
				consoleServer.startJetty();
			} catch (Exception e) {
				log.error("console-server start error", e);
			}
		}
		ChannelFuture wsStartFuture = null;
		if (webSocketServer != null) {
			wsStartFuture = webSocketServer.start();
		}
		proxyStartFuture.sync();
		log.info("proxy server started, listening port:" + proxyServer.getListeningPort());
		if (wsStartFuture != null) {
			wsStartFuture.sync();
			log.info("console-ws_server started, listening port:" + webSocketServer.getPort());
		}
	}

}
