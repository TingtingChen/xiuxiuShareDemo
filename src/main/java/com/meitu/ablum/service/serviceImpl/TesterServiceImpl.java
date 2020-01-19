package com.meitu.ablum.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meitu.ablum.entity.Tester;
import com.meitu.ablum.service.TesterService;
import com.meitu.ablum.util.PropertitesUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author yanrangnan
 * @date 2019/12/23
 */
@Service
public class TesterServiceImpl implements TesterService {
    private static Map<Integer,Tester> map = new HashMap<>();


    @Override
    public List<Tester> getAllTester() {
        readTester();
        return new ArrayList<>(map.values());
    }

    @Override
    public Boolean addTester(Tester tester) {
        int[] currentMaxId = new int[1];
        map.forEach((k,v)->currentMaxId[0] = k>currentMaxId[0]?k:currentMaxId[0]);
        tester.setId(currentMaxId[0]+1);
        map.put(tester.getId(),tester);
        saveTester();
        return true;
    }

    @Override
    public Boolean deleteTester(Integer id) {
        map.remove(id);
        saveTester();
        return true;
    }

    private void readTester(){
        String s = null;
        try {
            s = PropertitesUtil.readFile("data/Tester.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Tester> list = JSONObject.parseArray(s, Tester.class);
        list.forEach(item -> map.put(item.getId(),item));
    }

    private void saveTester(){
        List<Tester> list = new ArrayList<>(map.values());

        String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            PropertitesUtil.writeFile(content,"data/Tester.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
