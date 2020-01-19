package com.meitu.ablum.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meitu.ablum.entity.VersionData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class PropertitesUtil {
    public static  Properties props;

//    static {
//        try {
//            readPropertiesFile("data/versionData.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException {
        String s =null;
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

        writeFile(content,"data/VersionData.json");

    }


    // 读文件
    public static String readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:"+filePath);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = reader.readLine())!=null){
            sb.append(str);
        }
        reader.close();
        return sb.toString();
    }

    // 写文件
    public static void writeFile(String content,String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:"+filePath);

        if (!file.exists()){
           file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public static Properties readPropertiesFile(String filePath) throws FileNotFoundException, IOException {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            InputStream inputStream =classPathResource.getInputStream();
            props = new Properties();
            props.load(new InputStreamReader(inputStream, "UTF-8"));
            return props;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getServiceBeanName(String modelName){
        String modelId = props.getProperty(modelName);
        return props.getProperty(modelId);
    }

    public static Long getModelId(String modelName){

        return Long.parseLong(props.getProperty(modelName));
    }

}
