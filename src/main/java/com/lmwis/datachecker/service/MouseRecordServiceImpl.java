package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.MouseRecordDO;
import com.lmwis.datachecker.mapper.MouseRecordMapper;
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

    private final MouseRecordMapper mouseRecordMapper;

    private final List<MouseRecordDO> lists = new ArrayList<>();

    private final double threshold = 20;

    public MouseRecordServiceImpl(MouseRecordMapper mouseRecordMapper) {
        this.mouseRecordMapper = mouseRecordMapper;
    }

    public void insertRecordByTemp(MouseRecordDO mouseRecord) {
        if (lists.size() > threshold) {
            doInsertRecord();
        } else {
            lists.add(mouseRecord);
        }
    }

    private int doInsertRecord() {
        List<MouseRecordDO> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (MouseRecordDO k : o) {
//                log.info("do save record " + k);
                insert += mouseRecordMapper.insert(k);
            }
        } catch (Exception e) {
            log.error("数据库写入失败：" + e.getMessage());
            // 失败将已经写入的数据进行删除
            List<MouseRecordDO> temp = new ArrayList<>();
            for (MouseRecordDO k : o) {
                MouseRecordDO mouseRecord = mouseRecordMapper.selectById(k.getId());
                // 表示未被写入
                if (mouseRecord==null){
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
