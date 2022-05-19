package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/22 4:00 下午
 * @Version: 1.0
 */
@Data
@TableName("mouse_record")
@ToString
public class MouseRecordDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

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

    Date gmtCreate;

    Date gmtModified;
}
