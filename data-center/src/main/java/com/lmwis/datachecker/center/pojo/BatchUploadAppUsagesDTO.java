package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 6:28 下午
 * @Version: 1.0
 */
@Data
@ToString
public class BatchUploadAppUsagesDTO {

    List<AppUsagesDTO> list;

    long startTime;

    long endTime;
}
