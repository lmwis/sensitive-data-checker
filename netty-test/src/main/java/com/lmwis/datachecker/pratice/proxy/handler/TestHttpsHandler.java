package com.lmwis.datachecker.pratice.proxy.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObjectAggregator;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: HTTPS处理
 * @Author: lmwis
 * @Data: 2022/3/20 11:42 上午
 * @Version: 1.0
 */
@Slf4j
public class TestHttpsHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
//        if (StringUtils.equals(INetStringUtil.HTTP_FLAG,INetStringUtil.resolveProtocolFromUrl(fullHttpRequest.uri()))){
//            channelHandlerContext.fireChannelRead(fullHttpRequest);
//        }else {
//
//        }
//
//    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("exceptionCaught");
//        if (cause != null) cause.printStackTrace();
//        if (ctx != null) ctx.close();
//    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("[channelActive] ctx:{}",ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("[channelRead] msg class:{}",msg.getClass());
        if (msg instanceof HttpObjectAggregator){
            HttpObjectAggregator aggregator = (HttpObjectAggregator) msg;

            log.info("[channelRead] msg content:{}",aggregator.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("[exceptionCaught] exception:{}",cause.getMessage());
    }
}
