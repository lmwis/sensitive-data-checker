package com.lmwis.datachecker.computer.net.console.http.servlet.config;

import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfigProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * http://localhost:8080/config/mock_update
 */
public class MockMasterSwitchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JmitmCoreConfigProvider.get().setOpenMasterMockStwich("true".equals(req.getParameter("switcher")));
		resp.getWriter().print("true");
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
