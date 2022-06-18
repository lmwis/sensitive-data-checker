package com.lmwis.datachecker.center.controller;

import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.*;
import com.lmwis.datachecker.center.dao.LocationInfoDO;
import com.lmwis.datachecker.center.pojo.DashboardInitChartResult;
import com.lmwis.datachecker.center.pojo.DashboardInitResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/29 7:36 上午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    final KeyboardRecordAppService keyboardRecordAppService;

    final MouseRecordAppService mouseRecordAppService;

    final LocationInfoAppService locationInfoAppService;

    final AppUsageService appUsageService;

    final AppBaseAppService appBaseAppService;

    final IphoneActionAppService iphoneActionAppService;

    final IphonePostureAppService iphonePostureAppService;

    final ProcessUsageAppService processUsageAppService;

    final NetInfoAppService netInfoAppService;


    @GetMapping("/init")
    public CommonReturnType getDashboardInitData(){
        LocationInfoDO locationInfoDO = locationInfoAppService.selectLastDO();
        return CommonReturnType.create(DashboardInitResult.builder()
                .keyboardLast(keyboardRecordAppService.selectLastDO().getKeyText())
                .locationName(locationInfoAppService.selectLocationName(locationInfoDO.getLongitude(),locationInfoDO.getLatitude()))
                .appLast(appBaseAppService.selectById(appUsageService.selectLastDO().getAppId()).getName())
                .uploadLast(System.currentTimeMillis()).build());
    }
    @GetMapping("/chart")
    public CommonReturnType getDashboardInitChartData(){
        List<String> days = queryLast7DayString();
        List<Integer> labels = queryXLabel();
        Collections.reverse(days);
        Collections.reverse(labels);
        return CommonReturnType.create(DashboardInitChartResult.builder()
        .xLabel(labels)
        .locationCount(days.stream().map(locationInfoAppService::queryCountADay).collect(Collectors.toList()))
        .appCount(days.stream().map(appUsageService::queryCountADay).collect(Collectors.toList()))
                .keyboardCount(days.stream().map(keyboardRecordAppService::queryCountADay).collect(Collectors.toList()))
                .mouseCount(days.stream().map(mouseRecordAppService::queryCountADay).collect(Collectors.toList()))
                .postureCount(days.stream().map(iphonePostureAppService::queryCountADay).collect(Collectors.toList()))
                .actionCount(days.stream().map(iphoneActionAppService::queryCountADay).collect(Collectors.toList()))
                .pcAppCount(days.stream().map(processUsageAppService::queryCountADay).collect(Collectors.toList()))
                .netCount(days.stream().map(netInfoAppService::queryCountADay).collect(Collectors.toList()))
        .build());
    }
    private List<String> queryLast7DayString(){
        List<String> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            res.add(formatDate);
        }
        return res;
    }
    private List<Integer> queryXLabel(){
        List<Integer> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        for (int i = 0; i < 7; i++) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            res.add(Integer.parseInt(formatDate));
        }
        return res;
    }
}
