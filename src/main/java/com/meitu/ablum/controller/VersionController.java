package com.meitu.ablum.controller;

import com.meitu.ablum.entity.VersionData;
import com.meitu.ablum.service.VersiondataService;
import com.meitu.ablum.util.JsoupConnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author guoshuang
 * @date 2019/12/22
 */
@RestController
@RequestMapping("version")
@Slf4j
public class VersionController {

    @Autowired
    VersiondataService service;
    @GetMapping
    public int getVersionData(List<String> versions, String owner){
        log.info("获取 "+owner+"的 "+versions.toString()+"版本漏测数");
        int overSightBugNum = 0;
        try {
            overSightBugNum = JsoupConnect.getOverSightBugNum(versions, owner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return overSightBugNum;
    }

    @PostMapping("addVersion")
    @CrossOrigin(maxAge = 3600)
    public Boolean addVerison(VersionData versionData){
        log.info("添加版本号"+versionData);
        return service.addVersion(versionData);
    }

    @GetMapping("test")
    public void test(){
        VersionData versionData = new VersionData();
        versionData.setVersion("2.1.2");
        addVerison(versionData);
    }
}
