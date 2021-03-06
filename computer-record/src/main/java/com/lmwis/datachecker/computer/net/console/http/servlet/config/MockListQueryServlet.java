package com.lmwis.datachecker.computer.net.console.http.servlet.config;

import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.http.vo.MockListVO;
import com.lmwis.datachecker.computer.net.proxy.config.JmitmCoreConfigProvider;
import com.lmwis.datachecker.computer.net.proxy.mock.Mock;
import com.lmwis.datachecker.computer.net.proxy.mock.MockStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * http://localhost:8080/config/mock/list
 */
public class MockListQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Mock> mockList = JmitmCoreConfigProvider.get().getMockList();
		List<MockListVO> mockVoList = new ArrayList<MockListVO>();
		if (mockList != null && !mockList.isEmpty()) {
			mockList.forEach(item -> {
				mockVoList.add(new MockListVO(item.getId(), item.getName(), item.getDesc(), item.getStatus() == MockStatus.Enabled, item.getStatus().getCode() + ""));
			});
		}
		resp.setHeader("Content-Type", "application/json; charset=utf-8");
		resp.getWriter().print(JSON.toJSONString(mockVoList));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
