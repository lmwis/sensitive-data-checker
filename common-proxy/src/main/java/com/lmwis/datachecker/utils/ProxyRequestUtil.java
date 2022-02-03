package com.lmwis.datachecker.utils;

import com.lmwis.datachecker.bean.ClientRequest;
import com.lmwis.datachecker.bean.Constans;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/2/3 6:36 下午
 * @Version: 1.0
 */
public class ProxyRequestUtil {

    private ProxyRequestUtil() {
    }

    /**
     * 从请求中获取host跟端口
     * @param httpRequest
     * @return
     */
    public static ClientRequest getClientRequest(HttpRequest httpRequest){
        //从header中获取出host
        String host = httpRequest.headers().get("host");
        //从host中获取出端口
        String[] hostStrArr = host.split(":");
        int port = 80;
        // port填充
        if (hostStrArr.length > 1) {
            port = Integer.parseInt(hostStrArr[1]);
        } else if (httpRequest.uri().startsWith(Constans.HTTPS_PROTOCOL_NAME)) {
            port = 443;
        }
        return new ClientRequest(hostStrArr[0], port);
    }

    public static ClientRequest getClientRequest(Channel channel){
        channel.
    }
}
