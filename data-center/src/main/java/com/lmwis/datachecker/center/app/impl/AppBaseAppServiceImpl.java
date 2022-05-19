package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.convert.AppBaseConvert;
import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.dao.mapper.AppBaseDOMapper;
import com.lmwis.datachecker.center.pojo.AppBaseDTO;
import com.lmwis.datachecker.common.error.BusinessErrorEnum;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:52 下午
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class AppBaseAppServiceImpl implements AppBaseAppService {

    final AppBaseDOMapper appBaseDOMapper;

    @Override
    public AppBaseDO uploadAppBase(AppBaseDTO appBaseDTO) throws BusinessException {
        if (appBaseDTO == null || !verifyDTO(appBaseDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        AppBaseDO appBaseDO = AppBaseConvert.CONVERT.convertToDO(appBaseDTO);
        // verify app is already exist
        QueryWrapper<AppBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",appBaseDO.getName());
        queryWrapper.eq("package_name",appBaseDO.getPackageName());
        List<AppBaseDO> appBaseDOS = appBaseDOMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(appBaseDOS)){
            throw new BusinessException(BusinessErrorEnum.USER_APP_BASE_ALREADY_EXIST);
        }
        appBaseDOMapper.insert(appBaseDO);
        return appBaseDO;
    }
    private boolean verifyDTO(AppBaseDTO appBaseDTO){
        if (StringUtils.isBlank(appBaseDTO.getName()) ||
        StringUtils.isBlank(appBaseDTO.getPackageName()) ||
        StringUtils.isBlank(appBaseDTO.getPictureUrl())){
            return false;
        }
        if (LongUtil.nullOrZero(appBaseDTO.getGmtModified()) ||
                LongUtil.nullOrZero(appBaseDTO.getGmtCreate())){
            appBaseDTO.setGmtModified(System.currentTimeMillis());
            appBaseDTO.setGmtModified(System.currentTimeMillis());
        }
        return true;
    }
}
