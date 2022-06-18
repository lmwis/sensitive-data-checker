package com.lmwis.datachecker.computer.net.console.http;

import com.lmwis.datachecker.computer.net.console.http.config.JmitmConsoleConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Objects;


public class ConsoleServer {

	private static Server server;
	public static JmitmConsoleConfig wiredogConsoleConfig;
	
	public ConsoleServer(JmitmConsoleConfig config) {
		wiredogConsoleConfig = config;
		server = new Server(config.getHttpPort());
		server.setHandler(getWebAppContext(config));
	}

	public void startJetty() throws Exception {
		server.start();
	}

	public static void stopJetty() throws Exception {
		server.stop();
	}

	private static WebAppContext getWebAppContext(JmitmConsoleConfig config) {
		WebAppContext context = new WebAppContext();
		context.setResourceBase(Objects.toString(config.getWebRoot(), ConsoleServer.class.getResource("/static/webroot").getFile()));
		context.setDescriptor(Objects.toString(config.getWebXmlPath(), ConsoleServer.class.getResource("/static/webroot/WEB-INF/web.xml").getFile()));
		context.setParentLoaderPriority(true);
		return context;
	}

}
