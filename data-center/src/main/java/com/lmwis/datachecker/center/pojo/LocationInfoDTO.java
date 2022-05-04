package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: for locatino info dto
 * @Author: lmwis
 * @Data: 2022/4/30 5:32 下午
 * @Version: 1.0
 */
@Data
@ToString
public class LocationInfoDTO implements Serializable {

    private static final long serialVersionUID = 3123121386447824112L;

    String latitude;

    String longitude;

}
