package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.ProcessBaseAppService;
import com.lmwis.datachecker.center.app.ProcessUsageAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.ProcessUsageConvert;
import com.lmwis.datachecker.center.dao.ProcessBaseDO;
import com.lmwis.datachecker.center.dao.ProcessBaseDTO;
import com.lmwis.datachecker.center.dao.ProcessUsageDO;
import com.lmwis.datachecker.center.dao.mapper.ProcessUsageMapper;
import com.lmwis.datachecker.center.pojo.BatchQueryProcessUsageResult;
import com.lmwis.datachecker.center.pojo.BatchUploadProcessUsageDTO;
import com.lmwis.datachecker.center.pojo.ProcessUsageResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:33 下午
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class ProcessUsageAppServiceImpl implements ProcessUsageAppService {

    final ProcessUsageMapper processUsageMapper;

    final ProcessBaseAppService processBaseAppService;

    final UserContextHolder userContextHolder;

    @Override
    public boolean batchUploadProcessUsage(BatchUploadProcessUsageDTO batchUploadProcessUsageDTO) throws BusinessException {

        if (batchUploadProcessUsageDTO == null || CollectionUtils.isEmpty(batchUploadProcessUsageDTO.getList())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        batchUploadProcessUsageDTO.getList().forEach(k->{
            ProcessBaseDO processBaseDO = processBaseAppService.selectByProcessName(k.getProcessName());
            if (processBaseDO == null){
                // 写入new base
                ProcessBaseDTO processBaseDTO = new ProcessBaseDTO();
                processBaseDTO.setProcessFile(k.getProcessFile());
                processBaseDTO.setProcessName(k.getProcessName());
                processBaseDTO.setProcessDescription(k.getProcessDescription());
                long now = System.currentTimeMillis();
                processBaseDTO.setGmtCreate(now);
                processBaseDTO.setGmtModified(now);
                processBaseDO = processBaseAppService.insertProcessBase(processBaseDTO);
            }
            ProcessUsageDO processUsageDO = ProcessUsageConvert.CONVERT.convertToDO(k);
            processUsageDO.setProcessId(processBaseDO.getId());
            processUsageDO.setUid(userContextHolder.getCurrentUid());
            processUsageMapper.insert(processUsageDO);
        });

        return true;
    }

    @Override
    public BatchQueryProcessUsageResult batchQueryProcessUsageRangeTime(long startTime, long endTime) {
        if (LongUtil.nullOrZero(startTime) || LongUtil.nullOrZero(endTime)){
            // 为空默认获取一小时内的数据
            endTime = System.currentTimeMillis();
            startTime = endTime - 1000*60*60;
        }

        QueryWrapper<ProcessUsageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        List<ProcessUsageDO> iphonePostureDOS = processUsageMapper.selectList(queryWrapper);

        BatchQueryProcessUsageResult result = new BatchQueryProcessUsageResult();

        result.setList(resolveProcessUsageResultList(iphonePostureDOS));
        result.setCount(result.getList().size());
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        return result;
    }

    private List<ProcessUsageResult> resolveProcessUsageResultList(List<ProcessUsageDO> processUsageDOS) {
        List<ProcessUsageResult> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(processUsageDOS)) {
            processUsageDOS.forEach(k -> {
                ProcessBaseDO processBaseDO = processBaseAppService.selectById(k.getProcessId());
                if (processBaseDO != null){
                    ProcessUsageResult processUsageResult = ProcessUsageResult.builder()
                            .processFile(processBaseDO.getProcessFile())
                            .processDescription(processBaseDO.getProcessDescription())
                            .processName(processBaseDO.getProcessName())
                            .startTime(k.getStartTime().getTime())
                            .endTime(k.getEndTime().getTime())
                            .continueTime((int)(k.getEndTime().getTime() - k.getStartTime().getTime()))
                            .build();
                    results.add(processUsageResult);
                }

            });
        }
        return results;
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<ProcessUsageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return processUsageMapper.selectCount(queryWrapper).intValue();
    }
}
