package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:10 下午
 * @Version: 1.0
 */
@Data
@ToString
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
