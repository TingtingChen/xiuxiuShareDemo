package com.meitu.ablum.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meitu.ablum.entity.VersionData;
import com.meitu.ablum.mapper.VersionDataMapper;
import com.meitu.ablum.service.VersiondataService;
import com.meitu.ablum.util.PropertitesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class VersiondataServiceImpl implements VersiondataService {
    private static Map<Integer,VersionData> versionDatas = new HashMap<>();

    @Autowired
    VersionDataMapper versionDataMapper;

    @Override
    public List<VersionData> selectVersion() {
        return versionDataMapper.selectVersion();
    }




    public VersionData getVersionById(int id){
        readVersion();
        return versionDatas.get(id);
    }

    public List<VersionData> getAll(){
        readVersion();
        return new ArrayList<>(versionDatas.values());
    }



    public boolean deleteVersion(int id){
        versionDatas.remove(id);
        saveVersion();
        return !versionDatas.containsKey(id);
    }

    public boolean addVersion(VersionData versionData){
        int[] currentMaxId = new int[1];
        versionDatas.forEach((k,v)->currentMaxId[0] = k>currentMaxId[0]?k:currentMaxId[0]);
        versionData.setId(currentMaxId[0]+1);
        versionDatas.put(versionData.getId(),versionData);
        saveVersion();
        return versionDatas.containsKey(versionData.getId());
    }

    public boolean updateVersion(VersionData versionData){
        return addVersion(versionData);
    }

    private void readVersion(){
        String s = null;
        try {
            s = PropertitesUtil.readFile("data/VersionData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<VersionData> list = JSONObject.parseArray(s, VersionData.class);
        list.forEach(versionData -> versionDatas.put(versionData.getId(),versionData));
    }

    private void saveVersion(){
        List<VersionData> list = new ArrayList<>(versionDatas.values());

        String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            PropertitesUtil.writeFile(content,"data/VersionData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
