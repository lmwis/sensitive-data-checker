package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.ProcessBaseAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.ProcessBaseConvert;
import com.lmwis.datachecker.center.dao.ProcessBaseDO;
import com.lmwis.datachecker.center.dao.ProcessBaseDTO;
import com.lmwis.datachecker.center.dao.mapper.ProcessBaseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:39 下午
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
public class ProcessBaseAppServiceImpl implements ProcessBaseAppService {

    final ProcessBaseMapper processBaseMapper;

    final UserContextHolder userContextHolder;

    @Override
    public ProcessBaseDO insertProcessBase(ProcessBaseDTO processBaseDTO) {
        if (processBaseDTO == null){
            return null;
        }
        ProcessBaseDO processBaseDO = ProcessBaseConvert.CONVERT.convertToDO(processBaseDTO);
        processBaseDO.setUid(userContextHolder.getCurrentUid());
        processBaseMapper.insert(processBaseDO);
        return processBaseDO;
    }

    @Override
    public ProcessBaseDO selectByProcessName(String name)  {

        if (StringUtils.isBlank(name)){
            return null;
        }
        QueryWrapper<ProcessBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_name", name);
        List<ProcessBaseDO> processBaseDOS = processBaseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(processBaseDOS)){
            return null;
        }
        return processBaseDOS.get(0);
    }

    @Override
    public ProcessBaseDO selectById(long id) {
        if (LongUtil.nullOrZero(id)){
            return null;
        }
        return processBaseMapper.selectById(id);
    }
}
