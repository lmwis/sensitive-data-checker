package com.lmwis.datachecker.computer.net.console.http.servlet.pipe;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.service.PipeService;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeListQueryVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

;

/**
 * http://localhost:8080/pipe/list
 */
public class PipeListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PipeService pipeService = new PipeService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		JmitmPipeListQueryVO queryVo = new JmitmPipeListQueryVO();
		String isActive = req.getParameter("active") ;
		if (isActive != null) {
			queryVo.setActive(Boolean.parseBoolean(isActive));
		}
		resp.getWriter().print(JSON.toJSONString(pipeService.list(queryVo)));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
