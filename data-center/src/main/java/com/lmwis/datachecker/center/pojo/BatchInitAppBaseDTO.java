package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/22 4:07 下午
 * @Version: 1.0
 */
@Data
@ToString
public class BatchInitAppBaseDTO {
    List<AppBaseDTO> list;
}
