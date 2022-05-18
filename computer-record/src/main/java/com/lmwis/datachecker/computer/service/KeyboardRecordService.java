package com.lmwis.datachecker.computer.service;

import com.lmwis.datachecker.computer.pojo.KeyboardRecordDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface KeyboardRecordService {

    void insertRecordByTemp(KeyboardRecordDTO keyboardRecord);

    int saveTempRecord();
}
