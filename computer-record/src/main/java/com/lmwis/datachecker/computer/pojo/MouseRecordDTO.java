package com.lmwis.datachecker.computer.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/18 4:40 下午
 * @Version: 1.0
 */
@Data
@ToString
public class MouseRecordDTO {
    /**
     * 1-move
     * 2-click
     */
    Integer mode;

    /**
     * move生效
     */
    Integer x;

    /**
     * move生效
     */
    Integer y;

    /**
     * click时生效
     * 枚举值
     * 按钮类型，左键，右键，中键
     * 1-左键
     * 2-右键
     */
    Integer button;
    /**
     * 1-pressed
     * 2-released
     */
    Integer action;

    long gmtCreate;

    long gmtModified;
}
