package com.lmwis.datachecker.center.dao;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:08 下午
 * @Version: 1.0
 */
@Data
public class ProcessBaseDTO {

    String processName;

    String processFile;

    String processDescription;

    long gmtCreate;

    long gmtModified;
}
