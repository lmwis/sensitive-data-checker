package com.lmwis.datachecker.computer.net.console.http.helper;


import com.lmwis.datachecker.computer.net.console.common.Session;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeListVO;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmSessionListVO;
import com.lmwis.datachecker.computer.net.proxy.facade.PipeContext;
import com.lmwis.datachecker.computer.net.proxy.pipe.enumtype.Protocol;

public class ConsoleHelper {

	public static JmitmPipeListVO parse2WtPipeListVO(PipeContext item) {
		JmitmPipeListVO vo = new JmitmPipeListVO();
		vo.setPipeId(item.getId() + "");
		vo.setName(item.getSourceHost() + ":" + item.getSourcePort() + "->" + item.getTargetHost() + ":" + item.getTargetPort());
		vo.setProtocol(item.getProtocol() == null ? Protocol.UNKNOW.getDesc() : item.getProtocol().getDesc());
		vo.setStatus(item.getCurrentStatus().getDesc());
		return vo;
	}

	public static JmitmSessionListVO parse2WtSessionListVO(Session session) {
		JmitmSessionListVO listVO = new JmitmSessionListVO();
		listVO.setSessionId(session.getId() + "");
		listVO.setUri(session.getRequest().uri());
		listVO.setResponseCode(session.getResponse() == null ? "PENDING..." : session.getResponse().status().code() + "");
		return listVO;
	}

}
