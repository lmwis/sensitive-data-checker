package com.lmwis.datachecker.center.app.impl;

import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.LocationInfoAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.LocationInfoConvert;
import com.lmwis.datachecker.center.dao.LocationInfoDO;
import com.lmwis.datachecker.center.dao.mapper.LocationInfoMapper;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    boolean locationDtoValid(LocationInfoDTO dto){

        if (dto != null && StringUtils.isNoneBlank(dto.getLatitude())
        && StringUtils.isNoneBlank(dto.getLongitude())){
            return true;
        }

        return false;
    }
}
