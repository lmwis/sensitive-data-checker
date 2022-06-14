package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.IphonePostureDO;
import com.lmwis.datachecker.center.pojo.IphonePostureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:51 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface IphonePostureConvert {

    IphonePostureConvert CONVERT = Mappers.getMapper(IphonePostureConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(iphonePostureDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(iphonePostureDTO.getGmtModified()))")
    })
    IphonePostureDO convertToDO(IphonePostureDTO iphonePostureDTO);
}
