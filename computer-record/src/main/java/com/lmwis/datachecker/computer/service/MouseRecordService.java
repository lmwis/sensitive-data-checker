package com.lmwis.datachecker.computer.service;

import com.lmwis.datachecker.computer.pojo.MouseRecordDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface MouseRecordService {

    void insertRecordByTemp(MouseRecordDTO mouseRecord);

    int saveTempRecord();
}
