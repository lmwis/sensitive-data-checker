package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:27 下午
 * @Version: 1.0
 */
@Data
@ToString
public class AppBaseDTO implements Serializable {

    private static final long serialVersionUID = 2025632506631432382L;

    String name;

    String pictureUrl;

    String packageName;

    String version;
}
