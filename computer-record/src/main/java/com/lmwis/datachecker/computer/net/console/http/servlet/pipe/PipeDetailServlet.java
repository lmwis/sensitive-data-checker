package com.lmwis.datachecker.computer.net.console.http.servlet.pipe;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.service.PipeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http://localhost:8080/pipe/get
 */
public class PipeDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PipeService pipeService = new PipeService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		resp.getWriter().print(JSON.toJSONString(pipeService.getById(Long.parseLong(req.getParameter("id")))));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
