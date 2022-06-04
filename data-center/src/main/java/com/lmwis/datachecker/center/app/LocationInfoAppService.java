package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.LocationInfoDO;
import com.lmwis.datachecker.center.pojo.BatchQueryLocationResult;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:34 下午
 * @Version: 1.0
 */
public interface LocationInfoAppService {

    boolean saveLocationInfo(LocationInfoDTO dto) throws BusinessException;

    int batchSaveLocationInfo(List<LocationInfoDTO> dtos);

    BatchQueryLocationResult batchQueryLocationRangTime(long startTime, long endTime) throws BusinessException;

    LocationInfoDO selectLastDO();

    int queryCountADay(String day);
}
