package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.KeyboardRecord;
import com.lmwis.datachecker.mapper.KeyboardRecordMapper;
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
public class KeyboardRecordServiceImpl implements KeyboardRecordService {

    private final KeyboardRecordMapper keyboardRecordMapper;

    private final List<KeyboardRecord> lists = new ArrayList<>();

    private final double threshold = 20;

    public KeyboardRecordServiceImpl(KeyboardRecordMapper keyboardRecordMapper) {
        this.keyboardRecordMapper = keyboardRecordMapper;
    }

    public void insertRecordByTemp(KeyboardRecord keyboardRecord) {
        if (lists.size() > threshold) {
            doInsertRecord();
        } else {
            lists.add(keyboardRecord);
        }
    }

    private int doInsertRecord() {
        List<KeyboardRecord> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (KeyboardRecord k : o) {
//                log.info("do save record " + k);
                insert += keyboardRecordMapper.insert(k);
            }
        } catch (Exception e) {
            log.error("数据库写入失败：" + e.getMessage());
            // 失败将已经写入的数据进行删除
            List<KeyboardRecord> temp = new ArrayList<>();
            for (KeyboardRecord k : o) {
                KeyboardRecord keyboardRecord = keyboardRecordMapper.selectById(k.getId());
                // 表示未被写入
                if (keyboardRecord==null){
                    temp.add(k);
                }
            }
            // 将未被写入db的存回到list
            lists.addAll(temp);
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
