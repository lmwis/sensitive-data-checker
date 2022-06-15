package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.ProcessUsageDO;
import com.lmwis.datachecker.center.pojo.ProcessUsageDTO;
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
public interface ProcessUsageConvert {

    ProcessUsageConvert CONVERT = Mappers.getMapper(ProcessUsageConvert.class);

    @Mappings({
            @Mapping(target = "startTime", expression = "java(new java.util.Date(processUsageDTO.getStartTime()))"),
            @Mapping(target = "endTime", expression = "java(new java.util.Date(processUsageDTO.getEndTime()))"),
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(processUsageDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(processUsageDTO.getGmtModified()))")
    })
    ProcessUsageDO convertToDO(ProcessUsageDTO processUsageDTO);
}
