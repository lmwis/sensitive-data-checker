package com.lmwis.datachecker.computer.net.console.http.servlet.config;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.vo.config.ConsoleConfigVO;
import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfigProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

;

/**
 * http://localhost:8080/config/get
 */
public class ConsoleConfigQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print(JSON.toJSONString(new ConsoleConfigVO(JmitmCoreConfigProvider.get().isParseHttps(), JmitmCoreConfigProvider.get().isOpenMasterMockStwich())));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
