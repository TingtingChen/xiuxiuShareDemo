package com.meitu.ablum.mapper;

import com.meitu.ablum.entity.Missingdata;
import com.meitu.ablum.entity.VersionData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VersionDataMapper {
    @Select("select version from versiondata")
    List<VersionData> selectVersion();
}
