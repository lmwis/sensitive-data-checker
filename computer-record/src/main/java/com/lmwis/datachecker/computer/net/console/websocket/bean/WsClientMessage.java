package com.lmwis.datachecker.computer.net.console.websocket.bean;


import com.alibaba.fastjson.JSONObject;
import com.lmwis.datachecker.computer.net.console.websocket.enumtype.MessageTypeEnum;
import lombok.Data;

@Data
public class WsClientMessage {

	// 消息类型
	private MessageTypeEnum type;
	// 消息内容
	private JSONObject data;
}
