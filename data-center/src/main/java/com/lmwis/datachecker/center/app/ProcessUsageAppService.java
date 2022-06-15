package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.pojo.BatchQueryProcessUsageResult;
import com.lmwis.datachecker.center.pojo.BatchUploadProcessUsageDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:10 下午
 * @Version: 1.0
 */
public interface ProcessUsageAppService {

    boolean batchUploadProcessUsage(BatchUploadProcessUsageDTO batchUploadProcessUsageDTO) throws BusinessException;


    BatchQueryProcessUsageResult batchQueryProcessUsageRangeTime(long startTime, long endTime);


    int queryCountADay(String day);
}
