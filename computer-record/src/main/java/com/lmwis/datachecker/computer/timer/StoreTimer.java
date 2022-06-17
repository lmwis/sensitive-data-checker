package com.lmwis.datachecker.computer.timer;

import com.lmwis.datachecker.computer.client.DataStore;
import com.lmwis.datachecker.computer.net.console.common.chain.SessionManagerInvokeChain;
import com.lmwis.datachecker.computer.pojo.NetInfoDTO;
import com.lmwis.datachecker.computer.pojo.RequestInfoDTO;
import com.lmwis.datachecker.computer.pojo.ResponseInfoDTO;
import com.lmwis.datachecker.computer.util.INetStringUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/16 1:12 下午
 * @Version: 1.0
 */
@Component
@Slf4j
public class StoreTimer {

    final DataStore dataStore;

    public StoreTimer(DataStore dataStore) {
        this.dataStore = dataStore;
        initTimer();
    }

    private void initTimer() {
        final long timeInterval = 5000;
        final long lastTime = 0;
        Runnable runnable = () -> {
            while (true) {
//                dataStore.flush();
                List<NetInfoDTO> netInfoDTOList = new ArrayList<>();
                SessionManagerInvokeChain.getAll().stream()
                        .filter(k-> k.getResponseTime()>lastTime)
                        .forEach(k->{
                            RequestInfoDTO requestInfoDTO = convertRequestInfo(k.getRequest());
                            requestInfoDTO.setGmtCreate(k.getRequestTime());
                            requestInfoDTO.setGmtModified(k.getRequestTime());
                            requestInfoDTO.setSourceContent(Arrays.toString(k.getRequestBytes()));
                            ResponseInfoDTO responseInfoDTO = convertHttpResponse(k.getResponse(), requestInfoDTO);
                            responseInfoDTO.setGmtCreate(k.getResponseTime());
                            responseInfoDTO.setGmtModified(k.getResponseTime());
                            responseInfoDTO.setSourceContent(Arrays.toString(k.getResponseBytes()));
                            NetInfoDTO netInfoDTO = NetInfoDTO.builder()
                                    .request(requestInfoDTO)
                                    .response(responseInfoDTO).build();
                            netInfoDTOList.add(netInfoDTO);
                        });
                dataStore.saveNetInfo(netInfoDTOList);
                try {
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private RequestInfoDTO convertRequestInfo(FullHttpRequest request) {
        RequestInfoDTO requestInfoDTO = RequestInfoDTO.builder()
                .protocol(request.protocolVersion().protocolName())
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
        requestInfoDTO.setHeaders(headers);
        log.debug("[convertRequestInfo] requestInfo:{}",requestInfoDTO);
        return requestInfoDTO;
    }
    private ResponseInfoDTO convertHttpResponse(FullHttpResponse oResponse, RequestInfoDTO requestInfoDTO)  {
        ResponseInfoDTO httpInfo = new ResponseInfoDTO(requestInfoDTO);
        Map<String, String> headers = new HashMap<>();
        Arrays.stream(oResponse.headers().names().toArray(new String[0])).forEach(k -> {
            headers.put(k, oResponse.headers().get(k));
        });
        httpInfo.setHeaders(headers);
        httpInfo.setHttpVersion(oResponse.protocolVersion().toString());
        httpInfo.setResponseCode(oResponse.status().code());
        httpInfo.setContent(oResponse.content().toString(StandardCharsets.UTF_8));
        log.debug("[convertHttpResponse] debug responseInfo:{}", httpInfo);
        return httpInfo;
    }
}
