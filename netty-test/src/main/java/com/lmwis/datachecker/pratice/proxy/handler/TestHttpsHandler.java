package com.lmwis.datachecker.pratice.proxy.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

/**
 * @Description: HTTPS处理
 * @Author: lmwis
 * @Data: 2022/3/20 11:42 上午
 * @Version: 1.0
 */
@Slf4j
public class TestHttpsHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("[channelActive] ctx:{}",ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("[channelRead] msg class:{}",msg.getClass());
        if (msg instanceof HttpRequest){
            HttpRequest req = (HttpRequest) msg;
            HttpMethod method = req.method();
            log.info("[channelRead] msg content:{}",req);
            if (method.equals(HttpMethod.CONNECT)) {
                // connect请求直接返回
                HttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), new HttpResponseStatus(HttpStatus.SC_OK, "Connection Established"));
                ctx.writeAndFlush(response);
                return;
            }
//            sendToServer();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("[exceptionCaught] exception:{}",cause.getMessage());
    }
}
