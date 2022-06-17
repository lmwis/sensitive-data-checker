package com.lmwis.datachecker.computer.net.console.http.servlet.config;

import com.lmwis.datachecker.computer.net.console.common.chain.PipeManagerInvokeChain;
import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfigProvider;
import com.lmwis.datachecker.computer.net.proxy.pipe.enumtype.PipeEventType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http://localhost:8080/config/https_proxy_update
 */
@Slf4j
public class HttpsProxySwitchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JmitmCoreConfigProvider.get().setParseHttps("true".equals(req.getParameter("switcher")));

		// 当切换代理模式时，需要将已建立的连接全部断掉，这样才能保证新的请求采用最新的代理模式。
		PipeManagerInvokeChain.getAll().forEach(context -> {
			if (context.getClientChannel() != null && context.getClientChannel().isActive()) {
				context.addEvent(PipeEventType.ClientClosed, "http-proxy swtich changed");
				context.getClientChannel().close();
				log.info("close client channel");
			}
		});
		
		resp.getWriter().print("true");
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
