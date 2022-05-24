package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/24 8:20 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class LocationResult {

    String latitude;

    String longitude;

    long timeStamp;

}

