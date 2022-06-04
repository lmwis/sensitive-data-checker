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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            queryWrapper.eq("uid",appBaseDO.getUid());
            queryWrapper.eq("name",appBaseDO.getName());
            queryWrapper.eq("package_name",appBaseDO.getPackageName());
            AppBaseDO exitAppBase = appBaseDOMapper.selectOne(queryWrapper);
            if (exitAppBase == null){
                appBaseDOMapper.insert(appBaseDO);
            }else {
                // 重复的检查更新
                if (!StringUtils.equals(exitAppBase.getPictureUrl(),l.getPictureUrl())){
                    // 更新图片url
                    exitAppBase.setPictureUrl(l.getPictureUrl());
                    appBaseDOMapper.updateById(exitAppBase);
                }
                if (!StringUtils.equals(exitAppBase.getVersion(),l.getVersion())){
                    // 更新图片url
                    exitAppBase.setVersion(l.getVersion());
                    appBaseDOMapper.updateById(exitAppBase);
                }
            }


        });
        log.info("[batchInitAppBase] 初始化完毕 数量:{}",batchInitAppBaseDTO.getList().size());
        return true;
    }

    @Override
    public AppBaseDO selectAppBaseDOByPackageName(String packageName) {
        if (StringUtils.isBlank(packageName)){
            return null;
        }
        QueryWrapper<AppBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userContextHolder.getCurrentUid());
        queryWrapper.eq("package_name",packageName);
        return appBaseDOMapper.selectOne(queryWrapper);
    }

    @Override
    public AppBaseDO selectById(long id) {
        return appBaseDOMapper.selectById(id);
    }

    @Override
    public int selectAllCount() {
        QueryWrapper<AppBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userContextHolder.getCurrentUid());
        return appBaseDOMapper.selectCount(queryWrapper).intValue();
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
