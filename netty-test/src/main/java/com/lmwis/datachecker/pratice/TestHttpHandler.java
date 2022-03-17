package com.lmwis.datachecker.pratice;

import cn.hutool.http.HttpUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.nio.charset.StandardCharsets;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/27 5:00 下午
 * @Version: 1.0
 */
public class TestHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {

        System.out.println("class:" +fullHttpRequest.getClass().getName());
        System.out.println("uri: "+fullHttpRequest.uri());
        System.out.println("method: "+fullHttpRequest.method());
        System.out.println("content: "+ fullHttpRequest.content().toString(StandardCharsets.UTF_8));

        String originData = "test";
        if (fullHttpRequest.method().equals(HttpMethod.GET)){
            originData= HttpUtil.get(fullHttpRequest.uri());
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(originData.getBytes())); // 2

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);



        channelHandlerContext.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete:"+ctx.toString());

        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if (cause!=null) cause.printStackTrace();
        if (ctx!= null) ctx.close();
    }
}
