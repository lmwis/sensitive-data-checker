package com.lmwis.datachecker.pojo;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 9:49 下午
 * @Version: 1.0
 */
@Data
public class KeyboardRecordDTO {

    String keyText;

    Integer keyCode;

    /**
     * 枚举值
     * 1-按下 2-释放
     */
    Integer action;

    long gmtCreate;

    long gmtModified;
}
