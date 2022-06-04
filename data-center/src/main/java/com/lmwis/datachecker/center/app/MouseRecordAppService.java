package com.lmwis.datachecker.center.app;


import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.MouseRecordDO;
import com.lmwis.datachecker.center.pojo.BatchQueryMouseRecordResult;
import com.lmwis.datachecker.center.pojo.MouseRecordDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface MouseRecordAppService {

    int insertRecordByTemp(MouseRecordDTO mouseRecordDTO) throws BusinessException;

    int saveTempRecord();

    MouseRecordDO selectMouseById(Long id);

    int queryCountADay(String day);

    BatchQueryMouseRecordResult batchQueryMouseRecordRangTime(long startTime, long endTime);
}
