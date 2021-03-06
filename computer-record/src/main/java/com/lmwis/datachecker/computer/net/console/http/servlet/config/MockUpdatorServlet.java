package com.lmwis.datachecker.computer.net.console.http.servlet.config;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.vo.ServletResult;
import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfigProvider;
import com.lmwis.datachecker.computer.net.proxy.mock.Mock;
import com.lmwis.datachecker.computer.net.proxy.mock.MockStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * http://localhost:8080/config/mock/update
 */
public class MockUpdatorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mockId = req.getParameter("id");
		String mockStatus = req.getParameter("status");
		
		List<Mock> mockList = JmitmCoreConfigProvider.get().getMockList();
		if (mockList != null && !mockList.isEmpty()) {
			mockList.forEach(mock -> {
				if (mock.getId().equals(mockId)) {
					mock.status(MockStatus.getEnum(Integer.parseInt(mockStatus)));
				}
			});
		}
		
		resp.getWriter().print(JSON.toJSONString(new ServletResult<Boolean>(true)));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
