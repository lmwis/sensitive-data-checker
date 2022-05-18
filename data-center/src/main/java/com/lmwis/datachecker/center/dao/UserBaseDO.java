package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 3:37 下午
 * @Version: 1.0
 */
@Data
@TableName("user_base")
public class UserBaseDO {

    @TableId(type = IdType.AUTO)
    Long id;

    String token;

    @TableField("user_name")
    String username;

    Date gmtCreate;

    Date gmtModified;
}
