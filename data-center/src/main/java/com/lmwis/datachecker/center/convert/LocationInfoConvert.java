package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.LocationInfoDO;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:54 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface LocationInfoConvert {

    LocationInfoConvert CONVERT = Mappers.getMapper(LocationInfoConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(locationInfoDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(locationInfoDTO.getGmtModified()))")
    })
    LocationInfoDO convertToDO(LocationInfoDTO locationInfoDTO);

}
