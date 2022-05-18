package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.KeyboardRecordDO;
import com.lmwis.datachecker.center.pojo.KeyboardRecordDTO;
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
public interface KeyboardRecordConvert {

    KeyboardRecordConvert CONVERT = Mappers.getMapper(KeyboardRecordConvert.class);

    @Mappings({
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(keyboardRecordDTO.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(keyboardRecordDTO.getGmtModified()))")
    })
    KeyboardRecordDO convertToDO(KeyboardRecordDTO keyboardRecordDTO);
}
