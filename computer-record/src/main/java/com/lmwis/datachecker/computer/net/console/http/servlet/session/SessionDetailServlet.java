package com.lmwis.datachecker.computer.net.console.http.servlet.session;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.service.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http://localhost:8080/session/get
 */
public class SessionDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final SessionService sessionService = new SessionService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		resp.getWriter().print(JSON.toJSONString(sessionService.getById(Long.parseLong(req.getParameter("id")))));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
