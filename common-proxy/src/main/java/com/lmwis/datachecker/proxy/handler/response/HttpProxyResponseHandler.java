package com.lmwis.datachecker.proxy.handler.response;

import com.lmwis.datachecker.proxy.bean.Constans;
import com.lmwis.datachecker.proxy.bean.FullRequestResponseInfo;
import com.lmwis.datachecker.computer.pojo.HttpRequestInfo;
import com.lmwis.datachecker.computer.pojo.HttpResponseInfo;
import com.lmwis.datachecker.computer.service.NetInfoService;
import com.lmwis.datachecker.proxy.utils.SpringContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * https代理responseHandler
 * created on 2019/10/28 15:00
 *
 * @author puhaiyang
 */
public class HttpProxyResponseHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(HttpProxyResponseHandler.class);
    @Setter
    private Channel clientChannel;

    final NetInfoService netInfoService;

    public HttpProxyResponseHandler(Channel clientChannel){
        this.clientChannel = clientChannel;
        netInfoService = SpringContextUtil.getApplicationContext().getBean(NetInfoService.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;

            FullRequestResponseInfo fullRequestResponseInfo = clientChannel.attr(Constans.TRACE_CLIENT).get();
            doSaveHttpInfo(fullRequestResponseInfo.getRequestInfo(),convertHttpResponse(response, fullRequestResponseInfo.getRequestInfo()));
        } else if (msg instanceof DefaultHttpResponse) {
            DefaultHttpResponse response = (DefaultHttpResponse) msg;
            logger.debug("[channelRead][DefaultHttpResponse] 接收到远程的数据 content:{}", response.toString());
        } else if (msg instanceof DefaultHttpContent) {
            DefaultHttpContent httpContent = (DefaultHttpContent) msg;
            logger.debug("[channelRead][DefaultHttpContent] 接收到远程的数据 content:{}", httpContent.content().toString(Charset.defaultCharset()));
        } else {
            logger.debug("[channelRead] 接收到远程的数据 " + msg.toString());
        }
        //发送给客户端
        clientChannel.writeAndFlush(msg);
    }
    private HttpResponseInfo convertHttpResponse(FullHttpResponse oResponse, HttpRequestInfo requestInfo)  {
        HttpResponseInfo httpInfo = new HttpResponseInfo(requestInfo);
        Map<String, String> headers = new HashMap<>();
        Arrays.stream(oResponse.headers().names().toArray(new String[0])).forEach(k -> {
            headers.put(k, oResponse.headers().get(k));
        });
        httpInfo.setHeaders(headers);
        httpInfo.setHttpVersion(oResponse.protocolVersion().toString());
        httpInfo.setResponseCode(oResponse.status().code());
        httpInfo.setContent(oResponse.content().toString(StandardCharsets.UTF_8));
        httpInfo.setGmtCreate(new Date());
        httpInfo.setGmtModify(new Date());
        logger.debug("[saveHttpResponse] debug responseInfo:{}", httpInfo);
        return httpInfo;
    }

    /**
     * db写入记录数据
     * @param requestInfo
     * @param responseInfo
     */
    private void doSaveHttpInfo(HttpRequestInfo requestInfo, HttpResponseInfo responseInfo){
        netInfoService.saveHttpInfo(requestInfo,responseInfo);
    }
}
