package com.lmwis.datachecker.computer.net.console.websocket.service;


import com.lmwis.datachecker.computer.net.console.http.helper.ConsoleHelper;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeListVO;
import com.lmwis.datachecker.computer.net.console.websocket.ConsoleManager;
import com.lmwis.datachecker.computer.net.console.websocket.bean.WsServerMessage;
import com.lmwis.datachecker.computer.net.console.websocket.enumtype.MessageTypeEnum;
import com.lmwis.datachecker.computer.net.proxy.facade.PipeContext;

public class WsPipeService {

	private static final ConsoleManager CM = ConsoleManager.get();
	
	public void sendConnectMsg(PipeContext ctx) {
		CM.getAll().forEach(channel -> {
			WsServerMessage<JmitmPipeListVO> msg = new WsServerMessage<>(MessageTypeEnum.PipeConnect);
			msg.setData(ConsoleHelper.parse2WtPipeListVO(ctx));
			channel.writeAndFlush(msg);
		});
	}

	public void sendStatusChangeMsg(PipeContext ctx) {
		CM.getAll().forEach(channel -> {
			WsServerMessage<JmitmPipeListVO> msg = new WsServerMessage<>(MessageTypeEnum.PipeUpdate);
			msg.setData(ConsoleHelper.parse2WtPipeListVO(ctx));
			channel.writeAndFlush(msg);
		});
	}

	public void sendDisConnectMsg(PipeContext ctx) {
		CM.getAll().forEach(channel -> {
			WsServerMessage<JmitmPipeListVO> msg = new WsServerMessage<>(MessageTypeEnum.PipeDisconnect);
			msg.setData(ConsoleHelper.parse2WtPipeListVO(ctx));
			channel.writeAndFlush(msg);
		});
	}
}
