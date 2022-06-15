package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.ProcessBaseDO;
import com.lmwis.datachecker.center.dao.ProcessBaseDTO;
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
public interface ProcessBaseConvert {

    ProcessBaseConvert CONVERT = Mappers.getMapper(ProcessBaseConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(processBaseDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(processBaseDTO.getGmtModified()))")
    })
    ProcessBaseDO convertToDO(ProcessBaseDTO processBaseDTO);
}
