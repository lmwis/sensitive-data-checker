package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.LocationInfoAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.LocationInfoConvert;
import com.lmwis.datachecker.center.dao.LocationInfoDO;
import com.lmwis.datachecker.center.dao.mapper.LocationInfoMapper;
import com.lmwis.datachecker.center.pojo.BatchQueryLocationResult;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;
import com.lmwis.datachecker.center.pojo.LocationResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:35 下午
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class LocationInfoAppServiceImpl implements LocationInfoAppService {

    final LocationInfoMapper locationInfoMapper;

    final UserContextHolder userContextHolder;

    @Override
    public boolean saveLocationInfo(LocationInfoDTO dto) throws BusinessException {
        if (!locationDtoValid(dto)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        LocationInfoDO locationInfoDO = LocationInfoConvert.CONVERT.convertToDO(dto);

        locationInfoDO.setUid(userContextHolder.getCurrentUid());
        locationInfoDO.setGmtCreate(new Date());
        locationInfoDO.setGmtModify(new Date());

        return locationInfoMapper.insert(locationInfoDO)>0;
    }

    @Override
    public int batchSaveLocationInfo(List<LocationInfoDTO> dtos) {
        return 0;
    }

    @Override
    public BatchQueryLocationResult batchQueryLocationRangTime(long startTime, long endTime) throws BusinessException {
        if (LongUtil.nullOrZero(startTime) || LongUtil.nullOrZero(endTime)){
            // 为空默认获取一小时内的数据
            endTime = System.currentTimeMillis();
            startTime = endTime - 1000*60*60;
        }

        QueryWrapper<LocationInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        List<LocationInfoDO> locationInfoDOS = locationInfoMapper.selectList(queryWrapper);

        BatchQueryLocationResult result = new BatchQueryLocationResult();

        result.setList(resolveLocationResultList(locationInfoDOS));
        result.setCount(result.getList().size());
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        return result;
    }

    @Override
    public LocationInfoDO selectLastDO() {
        QueryWrapper<LocationInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.last("limit 1");
        queryWrapper.orderByDesc("gmt_create");
        return locationInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<LocationInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return locationInfoMapper.selectCount(queryWrapper).intValue();
    }

    private List<LocationResult> resolveLocationResultList(List<LocationInfoDO> locationInfoDOS){
        List<LocationResult> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(locationInfoDOS)){
            locationInfoDOS.forEach(k->{
                LocationResult locationResult = LocationResult.builder()
                        .latitude(k.getLatitude())
                        .longitude(k.getLongitude())
                        .timeStamp(k.getGmtCreate().getTime())
                        .build();
                results.add(locationResult);
            });
        }
        return results;
    }

    boolean locationDtoValid(LocationInfoDTO dto){

        if (dto != null && StringUtils.isNoneBlank(dto.getLatitude())
        && StringUtils.isNoneBlank(dto.getLongitude())){
            return true;
        }

        return false;
    }
}
