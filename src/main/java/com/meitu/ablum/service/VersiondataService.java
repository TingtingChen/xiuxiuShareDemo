package com.meitu.ablum.service;

import com.meitu.ablum.entity.Tester;
import com.meitu.ablum.entity.VersionData;

import java.util.List;

public interface VersiondataService {
    List<VersionData> selectVersion();

    VersionData getVersionById(int id);

    List<VersionData> getAll();

    boolean deleteVersion(int id);

    boolean addVersion(VersionData versionData);

    boolean updateVersion(VersionData versionData);

}
