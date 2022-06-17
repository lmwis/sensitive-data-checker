package com.lmwis.datachecker.computer.net.console.websocket.service;


import com.lmwis.datachecker.computer.net.console.common.Session;
import com.lmwis.datachecker.computer.net.console.http.helper.ConsoleHelper;
import com.lmwis.datachecker.computer.net.console.http.service.SessionService;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmSessionListVO;
import com.lmwis.datachecker.computer.net.console.websocket.ConsoleManager;
import com.lmwis.datachecker.computer.net.console.websocket.bean.WsServerMessage;
import com.lmwis.datachecker.computer.net.console.websocket.enumtype.MessageTypeEnum;
import com.lmwis.datachecker.computer.net.proxy.facade.PipeContext;

public class WsSessionService {

	private static final ConsoleManager CM = ConsoleManager.get();

	public void sendNewSessionMsg(PipeContext pipe, Session session) {
		CM.getAll().forEach(channel -> {
			if (!SessionService.isMatch(session)) {
				return ;
			}
			WsServerMessage<JmitmSessionListVO> msg = new WsServerMessage<>(MessageTypeEnum.NewSession);
			msg.setData(ConsoleHelper.parse2WtSessionListVO(session));
			channel.writeAndFlush(msg);
		});
	}
	
}
