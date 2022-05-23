package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.AppBaseConvert;
import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.dao.mapper.AppBaseDOMapper;
import com.lmwis.datachecker.center.pojo.AppBaseDTO;
import com.lmwis.datachecker.center.pojo.BatchInitAppBaseDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    final UserContextHolder userContextHolder;

    @Override
    public boolean batchInitAppBase(BatchInitAppBaseDTO batchInitAppBaseDTO) throws BusinessException {
        if (batchInitAppBaseDTO == null || CollectionUtils.isEmpty(batchInitAppBaseDTO.getList()) ){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<AppBaseDTO> list = batchInitAppBaseDTO.getList();
        list.forEach(l->{
            AppBaseDO appBaseDO = AppBaseConvert.CONVERT.convertToDO(l);
            Date now = new Date();
            appBaseDO.setUid(userContextHolder.getCurrentUid());
            appBaseDO.setGmtCreate(now);
            appBaseDO.setGmtModified(now);

            QueryWrapper<AppBaseDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",appBaseDO.getName());
            queryWrapper.eq("package_name",appBaseDO.getPackageName());
            List<AppBaseDO> appBaseDOS = appBaseDOMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(appBaseDOS)){
                appBaseDOMapper.insert(appBaseDO);
            }
            // 重复的不做处理
        });

        return true;
    }

    @Override
    public AppBaseDO selectAppBaseDOByPackageName(String packageName) {
        if (StringUtils.isBlank(packageName)){
            return null;
        }
        QueryWrapper<AppBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("package_name",packageName);
        return appBaseDOMapper.selectOne(queryWrapper);
    }

    @Override
    public AppBaseDO selectById(long id) {
        return appBaseDOMapper.selectById(id);
    }

    private boolean verifyDTO(AppBaseDTO appBaseDTO){
        if (StringUtils.isBlank(appBaseDTO.getName()) ||
        StringUtils.isBlank(appBaseDTO.getPackageName()) ||
        StringUtils.isBlank(appBaseDTO.getPictureUrl())){
            return false;
        }
        return true;
    }
}
