package com.meitu.ablum.mapper;

import com.meitu.ablum.entity.Missingdata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MissingdataMapper {
    @Select("select * from Missingdata")
    List<Missingdata> selectAll();
}
