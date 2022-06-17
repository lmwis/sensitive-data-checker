package com.lmwis.datachecker.computer.service.impl;

import com.lmwis.datachecker.computer.client.DataStore;
import com.lmwis.datachecker.computer.pojo.MouseRecordDTO;
import com.lmwis.datachecker.computer.service.MouseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:17 下午
 * @Version: 1.0
 */
@Service
@Slf4j
public class MouseRecordServiceImpl implements MouseRecordService {

    final DataStore dataStore;

    private final List<MouseRecordDTO> lists = new ArrayList<>();

    private final double threshold = 20;

    public MouseRecordServiceImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void insertRecordByTemp(MouseRecordDTO mouseRecord) {
        if (lists.size() > threshold) {
            doInsertRecord();
        } else {
            lists.add(mouseRecord);
        }
    }

    private int doInsertRecord() {
        List<MouseRecordDTO> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (MouseRecordDTO k : o) {
//                insert += dataCenterClient.saveMouseRecord(k);
                dataStore.saveMouseRecord(k);
            }
        } catch (Exception e) {
            log.error("数据库写入失败：" + e.getMessage());
        }
        return insert;
    }


    @Override
    public int saveTempRecord() {
        if (!lists.isEmpty()) {
            return doInsertRecord();
        }
        return 0;
    }
}
