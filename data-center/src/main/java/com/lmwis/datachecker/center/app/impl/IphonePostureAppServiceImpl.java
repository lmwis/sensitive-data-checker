package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.IphonePostureAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.IphonePostureConvert;
import com.lmwis.datachecker.center.dao.IphonePostureDO;
import com.lmwis.datachecker.center.dao.mapper.IphonePostureMapper;
import com.lmwis.datachecker.center.pojo.BatchQueryIphonePostureResult;
import com.lmwis.datachecker.center.pojo.BatchUploadIphonePostureDTO;
import com.lmwis.datachecker.center.pojo.IphonePostureResult;
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
 * @Data: 2022/6/7 11:55 上午
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IphonePostureAppServiceImpl implements IphonePostureAppService {

    final IphonePostureMapper iphonePostureMapper;

    final UserContextHolder userContextHolder;

    @Override
    public boolean batchUploadIphonePosture(BatchUploadIphonePostureDTO batchUploadIphonePostureDTO) throws BusinessException {
        if (batchUploadIphonePostureDTO == null || CollectionUtils.isEmpty(batchUploadIphonePostureDTO.getList())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        try{
            batchUploadIphonePostureDTO.getList().forEach(l->{
                IphonePostureDO iphonePostureDO = IphonePostureConvert.CONVERT.convertToDO(l);
                iphonePostureDO.setUid(userContextHolder.getCurrentUid());
                iphonePostureMapper.insert(iphonePostureDO);
            });
            return true;
        }catch (Exception e){
            log.error("[batchUploadIphoneActions] save to db error :{}", e.getMessage());
        }
        return false;
    }

    @Override
    public BatchQueryIphonePostureResult batchQueryIphonePostureRangeTime(long startTime, long endTime) {
        if (LongUtil.nullOrZero(startTime) || LongUtil.nullOrZero(endTime)){
            // 为空默认获取一小时内的数据
            endTime = System.currentTimeMillis();
            startTime = endTime - 1000*60*60;
        }

        QueryWrapper<IphonePostureDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        List<IphonePostureDO> iphonePostureDOS = iphonePostureMapper.selectList(queryWrapper);

        BatchQueryIphonePostureResult result = new BatchQueryIphonePostureResult();

        result.setList(resolveIphonePostureResultList(iphonePostureDOS));
        result.setCount(result.getList().size());
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        return result;
    }

    private List<IphonePostureResult> resolveIphonePostureResultList(List<IphonePostureDO> iphonePostureDOS) {
        List<IphonePostureResult> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(iphonePostureDOS)) {
            iphonePostureDOS.forEach(k -> {
                IphonePostureResult iphonePostureResult = IphonePostureResult.builder()
                        .azimuth(k.getAzimuth())
                        .pitch(k.getPitch())
                        .roll(k.getRoll())
                        .gmtCreate(k.getGmtCreate().getTime())
                        .gmtModified(k.getGmtModified().getTime())
                        .build();
                results.add(iphonePostureResult);
            });
        }
        return results;
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<IphonePostureDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return iphonePostureMapper.selectCount(queryWrapper).intValue();
    }
}
