package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/29 10:36 上午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class DashboardInitChartResult {

    List<Integer> xLabel;
    List<Integer> locationCount;
    List<Integer> appCount;
    List<Integer> keyboardCount;
    List<Integer> mouseCount;
    List<Integer> netCount;

}
