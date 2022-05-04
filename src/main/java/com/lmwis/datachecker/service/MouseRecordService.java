package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.MouseRecordDO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface MouseRecordService {

    public void insertRecordByTemp(MouseRecordDO mouseRecord);

    int saveTempRecord();
}
