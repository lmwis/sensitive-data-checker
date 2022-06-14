package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.IphoneActionDO;
import com.lmwis.datachecker.center.pojo.IphoneActionDTO;
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
public interface IphoneActionConvert {

    IphoneActionConvert CONVERT = Mappers.getMapper(IphoneActionConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(iphoneActionDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(iphoneActionDTO.getGmtModified()))")
    })
    IphoneActionDO convertToDO(IphoneActionDTO iphoneActionDTO);
}
