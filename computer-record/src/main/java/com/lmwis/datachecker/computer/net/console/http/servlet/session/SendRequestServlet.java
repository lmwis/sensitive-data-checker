package com.lmwis.datachecker.computer.net.console.http.servlet.session;

import com.lmwis.datachecker.common.constant.HttpConstant;
import com.lmwis.datachecker.computer.net.console.common.ConsoleConstant;
import com.lmwis.datachecker.computer.net.console.common.Session;
import com.lmwis.datachecker.computer.net.console.common.chain.SessionManagerInvokeChain;
import com.lmwis.datachecker.computer.util.HttpMessageUtil;
import com.lmwis.datachecker.computer.util.HttpMessageUtil.InetAddress;
import com.lmwis.datachecker.computer.util.HttpRequestCodec;
import com.lmwis.datachecker.computer.util.HttpResponseCodec;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.bouncycastle.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * http://localhost:8080/session/sendRequest
 */
public class SendRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "text/plain; charset=utf-8");
		String httpRequestWithoutBody = req.getParameter("request");
		String requestBody = Objects.toString(req.getParameter("body"), "");
		
		FullHttpRequest fullHttpRequest = HttpRequestCodec.decode(httpRequestWithoutBody);
		fullHttpRequest.headers().set(HttpConstant.ContentLength, requestBody.getBytes().length);
		fullHttpRequest.content().clear();
		fullHttpRequest.content().writeBytes(requestBody.getBytes());
		
		InetAddress remoteAddress = HttpMessageUtil.parse2InetAddress(fullHttpRequest, false);
		
		Session wtSession = new Session(ConsoleConstant.CONSOLE_SESSION_PIPE_ID, fullHttpRequest, System.currentTimeMillis());
		wtSession.setRequestBytes(requestBody.getBytes());
		
		Socket socket = new Socket(remoteAddress.getHost().trim(), remoteAddress.getPort());
		socket.getOutputStream().write(HttpRequestCodec.encodeWithBody(fullHttpRequest, HttpConstant.RETURN_LINE).getBytes());
		socket.getOutputStream().flush();
		
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		
		String responseWithoutBody = readResponseLineAndHeader(dis);
		FullHttpResponse httpResponse = HttpResponseCodec.decode(responseWithoutBody);
		
		byte[] body = readResponseBody(httpResponse, dis);
		wtSession.setResponse(httpResponse, body, System.currentTimeMillis());
		SessionManagerInvokeChain.addSession(wtSession);
		
		socket.close();
		resp.getWriter().print(true);
		resp.getWriter().flush();
		resp.getWriter().close();
	}

	private String readResponseLineAndHeader(DataInputStream inputStream) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sbuilder = new StringBuilder();
		String line = null;
		while (!"".equals((line = inputStream.readLine()))) {
			if (line == null) {
				break;
			}
			sbuilder.append(line).append(HttpConstant.RETURN_LINE);
		}
		return sbuilder.toString();
	}
	
	private byte[] readResponseBody(FullHttpResponse httpResponse, DataInputStream dis) throws IOException {
		// 考虑body读取方式
		// 1.read by content-length
		if (httpResponse.headers().contains(HttpConstant.ContentLength)) {
			Integer contentLength = Integer.parseInt(httpResponse.headers().get(HttpConstant.ContentLength).trim());
			byte[] body = new byte[contentLength];
			dis.read(body);
			return body;
		}
		
		// 2.read by chunked
		if (httpResponse.headers().contains(HttpConstant.TransferEncoding)) {
			// chunked格式：#chunked_size#|\r\n|#chunked_data#|\r\n|#chunked_size#|\r\n|#chunked_data#|\r\n|0|\r\n|\r\n
			byte[] body = new byte[0];
			String chunkedSize = "";
			while (!"".equals((chunkedSize = dis.readLine()))) {
				if (chunkedSize == null) {
					break;
				}
				int len = Integer.parseInt(chunkedSize, 16);
				byte[] chunked = new byte[len];
				dis.read(chunked);
				body = Arrays.concatenate(body, chunked);
			}
			return body;
		}
		return null;
	}
}
