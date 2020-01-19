package com.meitu.ablum.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meitu.ablum.entity.VersionData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PropertitesUtilTest {



    @Test
    boolean writeFile(String content){

        try {
            PropertitesUtil.writeFile("","data/VersionData.json");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test
    void readAndWriteTest(){
        String s = null;
        try {
            s = PropertitesUtil.readFile("data/VersionData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<VersionData> list = JSONObject.parseArray(s, VersionData.class);
        list.get(1).setVersion("1.1.2");
        for (VersionData versionData : list) {
            System.out.println(versionData);
        }

        String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        writeFile(content);
    }

    @Test
    void generateVersionJson(){
        VersionData versionData = new VersionData(1001,"1.1.0");
        VersionData versionData2 = new VersionData(1002,"1002");

        List<VersionData> list = new ArrayList<>();

        list.add(versionData);
        list.add(versionData2);

        String s = JSON.toJSONString(list);

        System.out.println(s);

    }
}