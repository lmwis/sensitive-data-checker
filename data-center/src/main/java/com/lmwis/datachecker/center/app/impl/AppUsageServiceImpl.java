package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.app.AppUsageService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.constant.BatchQueryAppTimeMode;
import com.lmwis.datachecker.center.constant.UsageEventType;
import com.lmwis.datachecker.center.convert.AppUsageConvert;
import com.lmwis.datachecker.center.convert.UsageEventConvert;
import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.dao.AppUsagesDO;
import com.lmwis.datachecker.center.dao.UsageEventDO;
import com.lmwis.datachecker.center.dao.mapper.AppUsageDOMapper;
import com.lmwis.datachecker.center.dao.mapper.UsageEventDOMapper;
import com.lmwis.datachecker.center.pojo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    final int MAX_DIF_TIME_MS = 2000;

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
    private void fixData(){
        log.info("[fixData] 开始 修复数据");
        List<UsageEventDO> usageEventDOS = usageEventDOMapper.selectList(null);
        usageEventDOS.forEach(k->{
            AppBaseDO appBaseDO = appBaseAppService.selectAppBaseDOByPackageName(k.getPackageName());
            if (!k.getAppId().equals(appBaseDO.getId())){
                k.setAppId(appBaseDO.getId());
                usageEventDOMapper.updateById(k);
            }
        });

        List<AppUsagesDO> appUsagesDOS = appUsageDOMapper.selectList(null);
        appUsagesDOS.forEach(k->{
            AppBaseDO appBaseDO = appBaseAppService.selectAppBaseDOByPackageName(k.getPackageName());
            if (!k.getAppId().equals(appBaseDO.getId())){
                k.setAppId(appBaseDO.getId());
                appUsageDOMapper.updateById(k);
            }

        });
        log.info("[fixData] fix success");
    }
    @Override
    public BatchQueryUsageEventResult batchQueryUsageEventRangeTime(BatchQueryUsageEventDTO queryParam) throws BusinessException {

//        fixData();

        long endTime = System.currentTimeMillis();
        // 默认读一小时
        long startTime = 0;
        if (LongUtil.nullOrZero(queryParam.getEndTime()) && LongUtil.nullOrZero(queryParam.getStartTime())){
            if (queryParam.getMode() != BatchQueryAppTimeMode.CUSTOMIZE.getCode()){
                startTime = convertEndTimeFromMode(queryParam.getMode(), endTime);
            }else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        }else {
            startTime = queryParam.getStartTime();
            endTime = queryParam.getEndTime();
        }

        QueryWrapper<UsageEventDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("time_stamp",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("time_stamp");
        List<UsageEventDO> usageEventDOS = usageEventDOMapper.selectList(queryWrapper);

        List<AppUsageResult> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(usageEventDOS)){
            log.info("[batchQueryUsageEventRangeTime] usageEventDOS size:{}",usageEventDOS.size());
            dealSideData(usageEventDOS);
            list = resolveAppUsageResultList(usageEventDOS);
        }

        BatchQueryUsageEventResult result = new BatchQueryUsageEventResult();
        result.setList(list);
        result.setCount(result.getList().size());
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        return result;
    }

    private void dealSideData(List<UsageEventDO> usageEventDOS){
        // 处理不合理的头尾数据
        UsageEventDO head = usageEventDOS.get(0);
        if (head.getEventType() == UsageEventType.TO_BACK.getCode()){
            QueryWrapper<UsageEventDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("package_name",head.getPackageName());
            queryWrapper.eq("event_type",UsageEventType.TO_FRONT.getCode());
            // 小于
            queryWrapper.lt("time_stamp",head.getTimeStamp());
            queryWrapper.orderByDesc("time_stamp");
            queryWrapper.last("limit 1");
            UsageEventDO headAppend = usageEventDOMapper.selectOne(queryWrapper);
            // 如果不存在 为了保证数据一致性删掉这个
            if (headAppend == null){
                usageEventDOS.remove(0);
            }else {
                usageEventDOS.add(0,headAppend);
            }

        }
        UsageEventDO tail = usageEventDOS.get(usageEventDOS.size()-1);
        if (tail.getEventType() == UsageEventType.TO_FRONT.getCode()){
            QueryWrapper<UsageEventDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("package_name",tail.getPackageName());
            queryWrapper.eq("event_type",UsageEventType.TO_BACK.getCode());
            // 大于
            queryWrapper.gt("time_stamp",tail.getTimeStamp());
            queryWrapper.orderByAsc("time_stamp");
            queryWrapper.last("limit 1");
            UsageEventDO tailAppend = usageEventDOMapper.selectOne(queryWrapper);
            // 如果不存在 为了保证数据一致性删掉这个
            if (tailAppend == null){
                usageEventDOS.remove(usageEventDOS.size()-1);
            }else {
                usageEventDOS.add(tailAppend);
            }
        }
    }

    /**
     * 解析为展示的格式
     * @param usageEventDOS
     * @return
     */
    private List<AppUsageResult> resolveAppUsageResultList(List<UsageEventDO> usageEventDOS){
        List<AppUsageResult> results = new ArrayList<>();
        UsageEventDO lastEvent = null;
        AppUsageResult appUsageResult = new AppUsageResult();
        for (UsageEventDO k : usageEventDOS) {
            if (lastEvent == null || !k.getAppId().equals(lastEvent.getAppId())){
                // 保存上次事件
                if (lastEvent!=null){
                    appUsageResult.setContinueTime(k.getTimeStamp().getTime() - appUsageResult.getStartTime());
                    results.add(appUsageResult);
                }
                // 新事件
                appUsageResult = new AppUsageResult();
                appUsageResult.setAppInfo(appBaseAppService.selectById(k.getAppId()));
                appUsageResult.setStartTime(k.getTimeStamp().getTime());
            }else {
                // 相同应用
                if (k.getEventType() == UsageEventType.TO_FRONT.getCode()){
                    // 切出去和切回来的时间间隔超过2s
                    if ((k.getTimeStamp().getTime() - lastEvent.getTimeStamp().getTime())>=MAX_DIF_TIME_MS){
                        // 拆开记录
                        appUsageResult.setContinueTime(k.getTimeStamp().getTime() - appUsageResult.getStartTime());
                        results.add(appUsageResult);

                        // 新事件
                        appUsageResult = new AppUsageResult();
                        appUsageResult.setAppInfo(appBaseAppService.selectById(k.getAppId()));
                        appUsageResult.setStartTime(k.getTimeStamp().getTime());
                    }
                }

            }

            lastEvent = k;
        }

        return results;
    }
    private long convertEndTimeFromMode(int mode , long now){
        if(mode == BatchQueryAppTimeMode.CURRENT_DAY.getCode()){
            // 当天12点
            return now-(now+ TimeZone.getDefault().getRawOffset())%(1000*3600*24);

        }
        return now - 1000*60*60;
    }
    private void convertDateTime(){

    }
}
