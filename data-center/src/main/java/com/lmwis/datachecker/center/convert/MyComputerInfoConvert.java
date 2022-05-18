package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.MyComputerInfo;
import com.lmwis.datachecker.center.pojo.MyComputerInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:13 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface MyComputerInfoConvert {

    MyComputerInfoConvert CONVERT = Mappers.getMapper(MyComputerInfoConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(myComputerInfoDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(myComputerInfoDTO.getGmtModified()))")
    })
    MyComputerInfo convertToDO(MyComputerInfoDTO myComputerInfoDTO);
}
