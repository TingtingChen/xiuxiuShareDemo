package com.meitu.ablum.controller;

import com.meitu.ablum.entity.ResponseData;
import com.meitu.ablum.entity.VersionData;
import com.meitu.ablum.entity.XmSelect;
import com.meitu.ablum.service.VersiondataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoshuang
 * @date 2019/12/22
 */
@RestController
@RequestMapping("data")
@Slf4j
public class DataController {

    @Autowired
    VersiondataService versiondataService;

    @GetMapping("version")
    public VersionData getVersionDataById(int id){
        return versiondataService.getVersionById(id);
    }

    @GetMapping("allVersion")
    @ResponseBody
    @CrossOrigin(maxAge = 3600)
    public ResponseData getAll(){
        log.info("进入getAll Version");
        List<VersionData> all = versiondataService.getAll();
        List<XmSelect> list = new ArrayList<>();
        all.forEach(version->{
            XmSelect xmSelect1 = new XmSelect();
            xmSelect1.setName(version.getVersion());
            xmSelect1.setValue(version.getId().toString());
            list.add(xmSelect1);
        });
        ResponseData data = new ResponseData();
        data.setCode(0);
        data.setMsg("success");
        data.setData(list);
        return data;
    }




}
