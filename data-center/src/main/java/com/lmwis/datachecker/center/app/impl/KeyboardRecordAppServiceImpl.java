package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.KeyboardRecordAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.KeyboardRecordConvert;
import com.lmwis.datachecker.center.dao.KeyboardRecordDO;
import com.lmwis.datachecker.center.dao.mapper.KeyboardRecordMapper;
import com.lmwis.datachecker.center.pojo.KeyboardRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:17 下午
 * @Version: 1.0
 */
@Service
@Slf4j
public class KeyboardRecordAppServiceImpl implements KeyboardRecordAppService {

    private final KeyboardRecordMapper keyboardRecordMapper;

    final UserContextHolder userContextHolder;

    private final List<KeyboardRecordDO> lists = new ArrayList<>();

    private final double threshold = 3;

    public KeyboardRecordAppServiceImpl(KeyboardRecordMapper keyboardRecordMapper, UserContextHolder userContextHolder) {
        this.keyboardRecordMapper = keyboardRecordMapper;
        this.userContextHolder = userContextHolder;
    }

    public int insertRecordByTemp(KeyboardRecordDTO keyboardRecord) throws BusinessException {
        int res;
        if (lists.size() > threshold) {
            res = doInsertRecord();
        } else {
            KeyboardRecordDO keyboardRecordDO = KeyboardRecordConvert.CONVERT.convertToDO(keyboardRecord);
            if (!verifyKeyboardValue(keyboardRecordDO)) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            keyboardRecordDO.setUid(userContextHolder.getCurrentUid());
            lists.add(keyboardRecordDO);
            res = 1;
        }
        return res;
    }

    private int doInsertRecord() {
        List<KeyboardRecordDO> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (KeyboardRecordDO k : o) {
//                log.info("do save record " + k);
                insert += keyboardRecordMapper.insert(k);
            }
        } catch (Exception e) {
            log.error("数据库写入失败：" + e.getMessage());

            // 失败将已经写入的数据进行删除
            List<KeyboardRecordDO> temp = new ArrayList<>();
            for (KeyboardRecordDO k : o) {
                KeyboardRecordDO keyboardRecord = keyboardRecordMapper.selectById(k.getId());
                // 表示未被写入
                if (keyboardRecord == null) {
                    temp.add(k);
                }
            }
            // 将未被写入db的存回到list
            lists.addAll(temp);
        }
        return insert;
    }

    private boolean verifyKeyboardValue(KeyboardRecordDO keyboardRecordDO) {
        if (keyboardRecordDO == null || keyboardRecordDO.getKeyCode() == null || keyboardRecordDO.getKeyText() == null) {
            return false;
        }
        if (keyboardRecordDO.getGmtCreate() == null || keyboardRecordDO.getGmtModified() == null) {
            keyboardRecordDO.setGmtCreate(new Date());
            keyboardRecordDO.setGmtModified(new Date());
        }
        return true;
    }

    @Override
    public int saveTempRecord() {
        if (!lists.isEmpty()) {
            return doInsertRecord();
        }
        return 0;
    }

    @Override
    public KeyboardRecordDO selectKeyboardById(Long id) {
        return keyboardRecordMapper.selectById(id);
    }

    @Override
    public KeyboardRecordDO selectLastDO() {
        QueryWrapper<KeyboardRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.last("limit 1");
        queryWrapper.orderByDesc("gmt_create");
        return keyboardRecordMapper.selectOne(queryWrapper);
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<KeyboardRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return keyboardRecordMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public List<KeyboardRecordDTO> batchQueryKeyboardRecordRangTime(long startTime, long endTime) {
        QueryWrapper<KeyboardRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        return keyboardRecordMapper.selectList(queryWrapper).stream()
                .map(KeyboardRecordConvert.CONVERT::convertToDTO).collect(Collectors.toList());
    }
}
