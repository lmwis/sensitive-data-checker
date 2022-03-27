package com.lmwis.datachecker.pratice.proxy.handler;

import com.lmwis.datachecker.pratice.util.INetStringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/20 11:42 上午
 * @Version: 1.0
 */
@Slf4j
public class TestHttpsHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (StringUtils.equals(INetStringUtil.HTTP_FLAG,INetStringUtil.resolveProtocolFromUrl(fullHttpRequest.uri()))){
            channelHandlerContext.fireChannelRead(fullHttpRequest);
        }else {

        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if (cause != null) cause.printStackTrace();
        if (ctx != null) ctx.close();
    }

}
