package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.IphoneActionAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.IphoneActionConvert;
import com.lmwis.datachecker.center.dao.IphoneActionDO;
import com.lmwis.datachecker.center.dao.mapper.IphoneActionMapper;
import com.lmwis.datachecker.center.pojo.BatchQueryIphoneActionResult;
import com.lmwis.datachecker.center.pojo.BatchUploadIphoneActionDTO;
import com.lmwis.datachecker.center.pojo.IphoneActionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/6 8:47 下午
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IphoneActionAppServiceImpl implements IphoneActionAppService {


    final IphoneActionMapper iphoneActionMapper;

    final UserContextHolder userContextHolder;

    @Override
    public boolean batchUploadIphoneActions(BatchUploadIphoneActionDTO batchUploadIphoneActionDTO) throws BusinessException {
        if (batchUploadIphoneActionDTO == null || CollectionUtils.isEmpty(batchUploadIphoneActionDTO.getList())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        try{
            batchUploadIphoneActionDTO.getList().forEach(l->{
                IphoneActionDO iphoneActionDO = IphoneActionConvert.CONVERT.convertToDO(l);
                iphoneActionDO.setUid(userContextHolder.getCurrentUid());
                iphoneActionMapper.insert(iphoneActionDO);
            });
            return true;
        }catch (Exception e){
            log.error("[batchUploadIphoneActions] save to db error :{}", e.getMessage());
        }

        return false;
    }

    @Override
    public BatchQueryIphoneActionResult batchQueryIphoneActionRangeTime(long startTime, long endTime) {
        if (LongUtil.nullOrZero(startTime) || LongUtil.nullOrZero(endTime)){
            // 为空默认获取一小时内的数据
            endTime = System.currentTimeMillis();
            startTime = endTime - 1000*60*60;
        }

        QueryWrapper<IphoneActionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        List<IphoneActionDO> iphoneActionDOS = iphoneActionMapper.selectList(queryWrapper);

        BatchQueryIphoneActionResult result = new BatchQueryIphoneActionResult();

        result.setList(resolveIphoneActionResultList(iphoneActionDOS));
        result.setCount(result.getList().size());
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        return result;
    }

    private List<IphoneActionResult> resolveIphoneActionResultList(List<IphoneActionDO> iphonePostureDOS) {
        List<IphoneActionResult> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(iphonePostureDOS)) {
            iphonePostureDOS.forEach(k -> {
                IphoneActionResult iphoneActionResult = IphoneActionResult.builder()
                        .actionCode(k.getActionCode())
                        .actionName(k.getActionName())
                        .gmtCreate(k.getGmtCreate().getTime())
                        .gmtModified(k.getGmtModified().getTime())
                        .build();
                results.add(iphoneActionResult);
            });
        }
        return results;
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<IphoneActionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return iphoneActionMapper.selectCount(queryWrapper).intValue();
    }
}
