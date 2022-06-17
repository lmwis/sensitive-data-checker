package com.lmwis.datachecker.computer.net.console.websocket.handler;


import com.alibaba.fastjson.JSON;
import com.lmwis.datachecker.computer.net.console.websocket.bean.WsServerMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketEncoder extends MessageToByteEncoder<WsServerMessage<?>> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, WsServerMessage<?> msg, ByteBuf out) throws Exception {
		ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
	}
}
