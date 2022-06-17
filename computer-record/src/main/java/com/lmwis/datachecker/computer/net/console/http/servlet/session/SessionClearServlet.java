package com.lmwis.datachecker.computer.net.console.http.servlet.session;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.service.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http://localhost:8080/session/list
 */
public class SessionClearServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SessionService sessionService = new SessionService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		resp.getWriter().print(JSON.toJSONString(sessionService.clearAll()));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
