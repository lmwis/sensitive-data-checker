package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/16 3:51 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class NetInfoDTO {

    private RequestInfoDTO request;

    private ResponseInfoDTO response;

}
