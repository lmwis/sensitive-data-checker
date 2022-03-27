package com.lmwis.datachecker.pratice.proxy.handler;

import com.lmwis.datachecker.pratice.pojo.HttpRequestInfo;
import com.lmwis.datachecker.pratice.pojo.HttpResponseInfo;
import com.lmwis.datachecker.pratice.util.INetStringUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/27 5:00 下午
 * @Version: 1.0
 */
@Slf4j
public class TestHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {

        if (StringUtils.equals(INetStringUtil.HTTPS_FLAG,INetStringUtil.resolveProtocolFromUrl(fullHttpRequest.uri()))){
            channelHandlerContext.fireChannelRead(fullHttpRequest);
        }else {
            HttpRequestInfo requestInfo = convertHttpRequest(channelHandlerContext, fullHttpRequest);

            // 执行代理请求
            HttpResponseInfo responseInfo = doProxyRequest(requestInfo);

            String originData = responseInfo.getContent();
            Map<String, String> headers = responseInfo.getHeaders();
            log.debug("<originResponse> responseInfo:{}", responseInfo);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.valueOf(responseInfo.getHttpVersion()),
                    HttpResponseStatus.valueOf(responseInfo.getResponseCode()),
                    Unpooled.copiedBuffer(originData.getBytes()));
            HttpHeaders heads = response.headers();
            headers.forEach((k, v) -> {
                if (k != null) {
                    heads.add(k, v);
                }
            });
            channelHandlerContext.write(response);
        }
    }

    /**
     * 代理request进行请求并封装返回结果
     * @param requestInfo
     * @return
     * @throws IOException
     */
    private HttpResponseInfo doProxyRequest(HttpRequestInfo requestInfo) throws IOException {
        // 拼接时间戳是为了解决当缓存错误数据导致304请求没数据的问题
        String preRequestUrl = requestInfo.getUrl() + "?t=" + new Date().getTime();
        // 通过requestInfo 构建请求的request
        requestInfo.setUrl(preRequestUrl);

        org.apache.http.HttpRequest nRequest = new BasicHttpRequest(requestInfo.getMethod()
                , requestInfo.getUrl());
        requestInfo.getHeaders().forEach(nRequest::addHeader);
        HttpClient httpClient = HttpClientBuilder.create().build();
        org.apache.http.HttpResponse oResponse = httpClient.execute(new HttpHost(requestInfo.getHostname(), requestInfo.getPort()), nRequest);
        log.debug("[doProxyRequest] nRequest:{}", nRequest);
        return convertHttpResponse(oResponse, requestInfo);
    }

    private HttpResponseInfo convertHttpResponse(org.apache.http.HttpResponse oResponse, HttpRequestInfo requestInfo) throws IOException {
        HttpResponseInfo httpInfo = new HttpResponseInfo(requestInfo);
        Map<String, String> headers = new HashMap<>();
        Arrays.stream(oResponse.getAllHeaders()).forEach(k -> {
            headers.put(k.getName(), k.getValue());
        });
        httpInfo.setHeaders(headers);
        httpInfo.setHttpVersion(oResponse.getStatusLine().getProtocolVersion().toString());
        httpInfo.setResponseCode(oResponse.getStatusLine().getStatusCode());
        httpInfo.setContent("");
        HttpEntity entity = oResponse.getEntity();
        if (entity!=null){
            httpInfo.setContent(IOUtils.toString(oResponse.getEntity().getContent(), StandardCharsets.UTF_8));
        }
        log.debug("[saveHttpResponse] debug responseInfo:{}", httpInfo);
        return httpInfo;
    }

    private HttpRequestInfo convertHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) {
        HttpRequestInfo httpInfo = HttpRequestInfo.builder()
                .protocol("HTTP")
                .method(request.method().name())
                .url(request.uri())
                .content(request.content().toString(StandardCharsets.UTF_8))
                .hostname(INetStringUtil.resolveHostnameFromUrl(request.uri()))
                .port(INetStringUtil.resolvePorFromUrl(request.uri()))
                .build();
        List<Map.Entry<String, String>> entries = request.headers().entries();
        Map<String, String> headers = new HashMap<>();
        entries.forEach(k -> {
            headers.put(k.getKey(), k.getValue());
        });
        httpInfo.setHeaders(headers);
        log.debug("[saveHttpRequest] debug requestInfo:{}", httpInfo);
        return httpInfo;
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
