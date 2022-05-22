package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.AppUsagesDO;
import com.lmwis.datachecker.center.pojo.AppUsagesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 6:44 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface AppUsageConvert {

    AppUsageConvert CONVERT = Mappers.getMapper(AppUsageConvert.class);

    @Mappings({
            @Mapping(target = "lastTimeStamp", expression = "java(new java.util.Date(appUsagesDTO.getLastTimeStamp()))"),
            @Mapping(target = "lastTimeUsed", expression = "java(new java.util.Date(appUsagesDTO.getLastTimeUsed()))"),
            @Mapping(target = "lastTimeVisible", expression = "java(new java.util.Date(appUsagesDTO.getLastTimeVisible()))"),
            @Mapping(target = "lastTimeForegroundServiceUsed", expression = "java(new java.util.Date(appUsagesDTO.getLastTimeForegroundServiceUsed()))")
    })
    AppUsagesDO convertToDO(AppUsagesDTO appUsagesDTO);
}
