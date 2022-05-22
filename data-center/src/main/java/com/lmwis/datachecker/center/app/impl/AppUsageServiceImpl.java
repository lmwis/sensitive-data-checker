package com.lmwis.datachecker.center.app.impl;

import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.app.AppUsageService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.AppUsageConvert;
import com.lmwis.datachecker.center.convert.UsageEventConvert;
import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.dao.AppUsagesDO;
import com.lmwis.datachecker.center.dao.UsageEventDO;
import com.lmwis.datachecker.center.dao.mapper.AppUsageDOMapper;
import com.lmwis.datachecker.center.dao.mapper.UsageEventDOMapper;
import com.lmwis.datachecker.center.pojo.BatchUploadAppUsagesDTO;
import com.lmwis.datachecker.center.pojo.BatchUploadUsageEventDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 1:22 下午
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class AppUsageServiceImpl implements AppUsageService {

    final AppUsageDOMapper appUsageDOMapper;

    final UsageEventDOMapper usageEventDOMapper;

    final UserContextHolder userContextHolder;

    final AppBaseAppService appBaseAppService;

    @Override
    public boolean updateAppUsage(BatchUploadAppUsagesDTO batchUploadAppUsagesDTO) throws BusinessException {
        if (batchUploadAppUsagesDTO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        try{
            batchUploadAppUsagesDTO.getList().forEach(l->{
                AppUsagesDO appUsagesDO = AppUsageConvert.CONVERT.convertToDO(l);
                AppBaseDO appBaseDO = appBaseAppService.selectAppBaseDOByPackageName(l.getPackageName());
                if (appBaseDO!=null){
                    appUsagesDO.setAppId(appBaseDO.getId());
                }else {
                    appUsagesDO.setAppId(0L);
                }
                appUsagesDO.setUid(userContextHolder.getCurrentUid());
                Date now = new Date();
                appUsagesDO.setGmtCreate(now);
                appUsagesDO.setGmtModified(now);
                appUsageDOMapper.insert(appUsagesDO);
            });
            log.info("[updateAppUsage] 数据保存成功，共:{}", batchUploadAppUsagesDTO.getList().size());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean updateUsageEvent(BatchUploadUsageEventDTO batchUploadUsageEventDTO) throws BusinessException {
        if (batchUploadUsageEventDTO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        try{
            batchUploadUsageEventDTO.getList().forEach(l->{
                UsageEventDO usageEventDO = UsageEventConvert.CONVERT.convertToDO(l);
                AppBaseDO appBaseDO = appBaseAppService.selectAppBaseDOByPackageName(l.getPackageName());
                if (appBaseDO!=null){
                    usageEventDO.setAppId(appBaseDO.getId());
                }else {
                    usageEventDO.setAppId(0L);
                }
                usageEventDO.setUid(userContextHolder.getCurrentUid());
                Date now = new Date();
                usageEventDO.setGmtCreate(now);
                usageEventDO.setGmtModified(now);
                usageEventDOMapper.insert(usageEventDO);
            });
            log.info("[updateUsageEvent] 数据保存成功，共:{}", batchUploadUsageEventDTO.getList().size());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void convertDateTime(){

    }
}
