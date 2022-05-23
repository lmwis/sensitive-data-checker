package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.pojo.BatchQueryUsageEventDTO;
import com.lmwis.datachecker.center.pojo.BatchQueryUsageEventResult;
import com.lmwis.datachecker.center.pojo.BatchUploadAppUsagesDTO;
import com.lmwis.datachecker.center.pojo.BatchUploadUsageEventDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 1:22 下午
 * @Version: 1.0
 */
public interface AppUsageService {

    /**
     * 更新时间范围内的app使用情况
     * @param batchUploadAppUsagesDTO
     * @return
     */
    boolean updateAppUsage(BatchUploadAppUsagesDTO batchUploadAppUsagesDTO) throws BusinessException;

    boolean updateUsageEvent(BatchUploadUsageEventDTO batchUploadUsageEventDTO) throws BusinessException;

    BatchQueryUsageEventResult batchQueryUsageEventRangeTime(BatchQueryUsageEventDTO batchQueryUsageEventDTO);
}
