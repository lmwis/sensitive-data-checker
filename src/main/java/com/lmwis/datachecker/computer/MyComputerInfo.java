package com.lmwis.datachecker.computer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: current pc info. exp: width,height,os,name;
 * @Author: lmwis
 * @Data: 2021/12/23 5:06 下午
 * @Version: 1.0
 */
@TableName("user_computer")
@Data
@ToString
public class MyComputerInfo {

    @TableId(type = IdType.AUTO)
    Long id;

    String userName;

    String osName;

    String osVersion;

    int screenWidth;

    int screenHeight;

    Date gmtCreate;

    Date gmtModified;
}
