package com.lmwis.datachecker.computer.net.console.http.servlet.session;

import com.lmwis.datachecker.common.constant.HttpConstant;
import com.lmwis.datachecker.computer.util.HttpRequestCodec;
import io.netty.handler.codec.http.FullHttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * http://localhost:8080/session/rebuild
 */
public class SessionRebuildServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String RETURN_LINE = "\r\n";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "text/plain; charset=utf-8");
		String httpRequestWithoutBody = req.getParameter("request");
		String requestBody = Objects.toString(req.getParameter("body"), "");
		
		FullHttpRequest fullHttpRequest = HttpRequestCodec.decode(httpRequestWithoutBody);
		fullHttpRequest.headers().set(HttpConstant.ContentLength, requestBody.getBytes().length);
		fullHttpRequest.content().clear();
		fullHttpRequest.content().writeBytes(requestBody.getBytes());
		
		resp.getWriter().print(HttpRequestCodec.encodeWithBody(fullHttpRequest, RETURN_LINE));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
}
