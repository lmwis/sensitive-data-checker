package com.lmwis.datachecker.center.app.impl;

import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.MouseRecordAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.MouseRecordConvert;
import com.lmwis.datachecker.center.dao.MouseRecordDO;
import com.lmwis.datachecker.center.dao.mapper.MouseRecordMapper;
import com.lmwis.datachecker.center.pojo.MouseRecordDTO;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MouseRecordAppServiceImpl implements MouseRecordAppService {

    final MouseRecordMapper mouseRecordMapper;

    final UserContextHolder userContextHolder;

    private final List<MouseRecordDO> lists = new ArrayList<>();

    private final double threshold = 20;


    public int insertRecordByTemp(MouseRecordDTO mouseRecordDTO) throws BusinessException {
        int res;
        try{
            if (lists.size() > threshold) {
                res = doInsertRecord();
            } else {
                MouseRecordDO mouseRecordDO = MouseRecordConvert.CONVERT.convertToDO(mouseRecordDTO);

                mouseRecordDO.setUid(userContextHolder.getCurrentUid());

                lists.add(mouseRecordDO);
                res = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.DATARESOURCE_CONNECT_FAILURE);
        }
        return res;
    }

    private int doInsertRecord() {
        List<MouseRecordDO> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (MouseRecordDO k : o) {
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

    @Override
    public MouseRecordDO selectMouseById(Long id) {
        return mouseRecordMapper.selectById(id);
    }
}
