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
 * @Data: 2022/5/19 1:27 下午
 * @Version: 1.0
 */
@Data
@ToString
@TableName("app_base")
public class AppBaseDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    String name;

    String pictureUrl;

    String packageName;

    Date gmtCreate;

    Date gmtModified;
}
