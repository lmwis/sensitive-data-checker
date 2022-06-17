package com.lmwis.datachecker.computer.net.console.http.servlet.session;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.service.SessionService;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmSessionListQueryVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * http://localhost:8080/session/list
 */
public class SessionListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SessionService sessionService = new SessionService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pipeId = req.getParameter("pipe_id");
		
		JmitmSessionListQueryVO query = new JmitmSessionListQueryVO();
		query.setPipeId(pipeId == null ? null : Long.parseLong(pipeId));
		query.setHost(req.getParameter("host"));
		query.setKeyword(req.getParameter("keyword"));
		
		resp.setHeader("Content-Type", "application/json");
		resp.getWriter().print(JSON.toJSONString(sessionService.list(query)));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
