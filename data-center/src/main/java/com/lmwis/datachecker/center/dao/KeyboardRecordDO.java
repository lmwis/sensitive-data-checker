package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:00 下午
 * @Version: 1.0
 */
@TableName("keyboard_record")
@Data
@ToString
public class KeyboardRecordDO {

    @TableId(type = IdType.AUTO)
    Long id;

    @TableField("owner_id")
    Long uid;

    String keyText;

    Integer keyCode;

    /**
     * 枚举值
     * 1-按下 2-释放
     */
    Integer action;

    /**
     * 枚举值
     * mac windows
     */
    Long ownerId;

    Date gmtCreate;

    Date gmtModified;


}
