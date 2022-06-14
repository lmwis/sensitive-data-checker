package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/6 8:49 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class IphonePostureDTO {

    double azimuth;

    double pitch;

    double roll;

    long gmtCreate;

    long gmtModified;
}
