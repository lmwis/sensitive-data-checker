package com.lmwis.datachecker.computer.net.start.main;

import com.lmwis.datachecker.computer.net.console.common.codec.impl.CodecFactory;
import com.lmwis.datachecker.computer.net.proxy.mock.CatchRequest;
import com.lmwis.datachecker.computer.net.proxy.mock.CatchResponse;
import com.lmwis.datachecker.computer.net.proxy.mock.Mock;
import com.lmwis.datachecker.computer.net.proxy.mock.wiredog.HttpResponse;
import com.lmwis.datachecker.computer.net.start.provider.JmitmBuilder;
import com.lmwis.datachecker.computer.net.start.setting.JvmHookSetting;
import com.lmwis.datachecker.computer.net.start.setting.ProxySetCommand;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class ProxyServerRun {

	final static int CONSOLE_PORT = 8080;
	final static int CONSOLE_WS_PORT = 52996;
	
	public static void run(int port) throws Exception {
		JmitmBuilder wtBuilder = new JmitmBuilder();
		wtBuilder.parseHttps(true);
		wtBuilder.proxyPort(port).threads(100);
		wtBuilder.consoleHttpPort(CONSOLE_PORT).consoleWsPort(CONSOLE_WS_PORT);
		wtBuilder.pipeHistory(10).sessionHistory(200);
		wtBuilder.build().start();
		ProxySetCommand.setProxy(port);
		JvmHookSetting.registerShutdownHook();
	}

	private static Mock mockDemo4() {
		return new CatchRequest().eval(request -> {
			return "www.baidu.com".equals(request.host()) && "/".equals(request.uri());
		}).rebuildResponse(response -> {
			log.info("inject js...");
			// 注入的JS代码
			String json = "<!--add by wiretigher--><script type='text/javascript'>alert('wiredog say hello');</script>";
			String outBody = "";
			try {
				// 因为响应头是gzip进行压缩，因此无法直接将ASCII串追加到内容末尾，需要先将原响应报文解压，在将JS追加到末尾
				outBody = new String(CodecFactory.create("gzip").decompress(response.body())) + json;
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 解压后为了省事，就不再进行压缩
			return response.removeHeader("Content-Encoding").body(outBody.getBytes());
		}).mock();
	}
	
	private static Mock mockDemo3() {
		return new CatchResponse().eval(response -> {
			return true;
		}).rebuildResponse(response -> {
			return response.header("signby", "hudaming");
		}).mock();
	}
	
	private static Mock mockDemo2() {
		return new CatchRequest().eval(request -> {
			return "www.baidu.com".equals(request.host()) &&
					("/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png".equals(request.uri()) || "/img/flexible/logo/pc/result.png".equals(request.uri()) || "/img/flexible/logo/pc/result@2.png".equals(request.uri()));
		}).mockResponse(httpRequest -> {
			byte[] googleLogo = readFile("/mock/google.png");
			HttpResponse response = new HttpResponse();
			return response.body(googleLogo).header("Content-Type", "image/gif");
		}).mock();
	}
	
	private static byte[] readFile(String file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(ProxyServerRun.class.getResource(file).getFile()));
			byte[] bytes = new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
			fileInputStream.close();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Mock mockDemo1() {
		// 将wiredog.com重定向到localhost:8080
		return new CatchRequest().eval(request -> {
			return "wiredog.com".equals(request.host());
		}).rebuildRequest(request -> {
			return request.header("Host", "localhost:8080");
		}).mock();
	}
	
	private static Mock mockRMGS() {
		return new CatchRequest().eval(request -> {
			return "datatest.mijiaoyu.cn".equals(request.host()) && "/teachingplan/teaching/aimdatasave".equals(request.uri()) && HttpMethod.POST.equals(request.method());
		}).rebuildRequest(request -> {
			System.out.println("mock");
			return request.header("Host", "localhost:2100").uri("/teaching/aimdatasave");
		}).mock();
	}
}


