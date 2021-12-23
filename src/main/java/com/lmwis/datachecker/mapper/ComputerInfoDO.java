package com.lmwis.datachecker.mapper;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lmwis.datachecker.computer.MyComputerInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/23 5:12 下午
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("computer_info")
@Data
@ToString
public class ComputerInfoDO extends MyComputerInfo {

    @TableId
    Long id;
}
