package com.lmwis.datachecker.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.common.config.CommonConfigProperties;
import com.lmwis.datachecker.computer.MyComputerInfo;
import com.lmwis.datachecker.dao.mapper.KeyboardRecordDO;
import com.lmwis.datachecker.dao.mapper.MouseRecordDO;
import com.lmwis.datachecker.pojo.KeyboardRecordDTO;
import com.lmwis.datachecker.pojo.MouseRecordDTO;
import com.lmwis.datachecker.pojo.MyComputerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:00 下午
 * @Version: 1.0
 */
@Component
@Slf4j
@AllArgsConstructor
public class DataCenterClient {

    static final String KEYBOARD_URL = "http://127.0.0.1:9001/keyboardRecord";

    static final String MOUSE_URL = "http://127.0.0.1:9001/mouseRecord";

    static final String COMPUTER_INFO_URL = "http://127.0.0.1:9001/userInfo/computerInfo";

    final CommonConfigProperties properties;
    final ObjectMapper objectMapper;

    /**
     * 上报当前电脑基本信息
     * @param myComputerInfo
     * @return
     */
    public MyComputerInfo uploadComputerInfo(MyComputerInfoDTO myComputerInfo){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        MyComputerInfo res = null;
        try{
            HttpPost httpPost = buildHttpPost(COMPUTER_INFO_URL, myComputerInfo);
            response = httpClient.execute(httpPost);
            // 请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                CommonReturnType commonReturnType = GsonUtil.readObject(readResponseBody(response), CommonReturnType.class);
                if (commonReturnType.getData()!=null){
                    res = GsonUtil.readObject(commonReturnType.getData().toString(),MyComputerInfo.class);
                }
            }
        }catch (IOException e){
            log.error("[registerComputerInfo] invoke http error, msg:{}",e.getMessage());
        }
        return res;
    }

    public int saveKeyboardRecord(KeyboardRecordDTO keyboardRecordDTO){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        int res = -1;
        try{
            HttpPost httpPost = buildHttpPost(KEYBOARD_URL, keyboardRecordDTO);
            response = httpClient.execute(httpPost);
            // 请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                CommonReturnType commonReturnType = GsonUtil.readObject(readResponseBody(response), CommonReturnType.class);
                if (commonReturnType.getData()!=null){
                    res = (int)Double.parseDouble(commonReturnType.getData().toString());
                }
            }
        }catch (IOException e){
            log.error("[saveKeyboardRecord] invoke http error, msg:{}",e.getMessage());
        }
        return res;
    }

    public KeyboardRecordDO selectKeyboardById(Long id){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(this.urlIdAppend(KEYBOARD_URL,id));
        KeyboardRecordDO keyboardRecordDO = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            CommonReturnType commonReturnType = GsonUtil.readObject(readResponseBody(response), CommonReturnType.class);
            if (commonReturnType.getData()!=null){
                keyboardRecordDO = (KeyboardRecordDO)commonReturnType.getData();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyboardRecordDO;
    }

    public int saveMouseRecord(MouseRecordDTO mouseRecordDTO) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        int res = -1;
        try{
            HttpPost httpPost = buildHttpPost(MOUSE_URL, mouseRecordDTO);
            response = httpClient.execute(httpPost);
            // 请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                CommonReturnType commonReturnType = GsonUtil.readObject(readResponseBody(response), CommonReturnType.class);
                if (commonReturnType.getData()!=null){
                    res = (int)Double.parseDouble(commonReturnType.getData().toString());
                }
            }
        }catch (IOException e){
            log.error("[saveKeyboardRecord] invoke http error, msg:{}",e.getMessage());
        }
        return res;
    }

    public MouseRecordDO selectMouseRecordById(Long id) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(this.urlIdAppend(KEYBOARD_URL,id));
        MouseRecordDO mouseRecordDO = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            mouseRecordDO = GsonUtil.readObject(readResponseBody(response), MouseRecordDO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mouseRecordDO;
    }

    /**
     * 拼装url参数
     * @param url
     * @param id
     * @return
     */
    private String urlIdAppend(String url, long id){
        return url + "/" + id;
    }

    /**
     * 从响应流中获取String形式响应体
     * @param response
     * @return
     * @throws IOException
     */
    private String readResponseBody(HttpResponse response) throws IOException {
        String res = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        log.info("[readResponseBody] response receive:{}",res);
        return res;
    }

    /**
     * 创建post请求
     * @param url
     * @param param
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpPost buildHttpPost(String url, Object param) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        // 设置token
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, properties.getUser().getToken());

        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String content = GsonUtil.toString(param);
        // 设置编码，否特特殊字符会乱码
        StringEntity stringEntity = new StringEntity(content, CharEncoding.UTF_8);
        stringEntity.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);
        return httpPost;
    }
}
