package com.lmwis.datachecker.computer.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/3/19 11:47 上午
 * @Version: 1.0
 */
@Data
@ToString
public class ResponseInfoDTO {

    public ResponseInfoDTO(RequestInfoDTO requestInfo){
        this.requestInfo = requestInfo;
    }

    RequestInfoDTO requestInfo;

    private String httpVersion;

    private int responseCode;

    private Map<String, String> headers;

    private String content;

    private String sourceContent;

    private long gmtCreate;

    private long gmtModified;


}
