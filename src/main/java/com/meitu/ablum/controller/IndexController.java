package com.meitu.ablum.controller;

import com.meitu.ablum.entity.FormData;
import com.meitu.ablum.entity.ShowData;
import com.meitu.ablum.entity.Tester;
import com.meitu.ablum.entity.VersionData;
import com.meitu.ablum.service.MissingdataService;
import com.meitu.ablum.service.TesterService;
import com.meitu.ablum.service.VersiondataService;
import com.meitu.ablum.util.CfDataSerach;
import com.meitu.ablum.util.ExcelUtil;
import com.meitu.ablum.util.JsoupConnect;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


@RestController
@Slf4j
public class IndexController {

    @Autowired
    private MissingdataService missingdataService;
    @Autowired
    private VersiondataService versiondataService;
    @Autowired
    private TesterService testerService;

    @CrossOrigin(maxAge = 3600)
    @RequestMapping("showData")
    @ResponseBody
    private List<ShowData> test() throws IOException {

        List<ShowData> list = new ArrayList();

        List<Tester> reports = testerService.getAllTester();
        Map<String, Tester> map = new HashMap<>(reports.size());
        reports.forEach(o->{
            map.put(o.getId().toString(),o);
            map.put(o.getUsername(),o);
            map.put(o.getNickname(),o);
        });

        //版本
        List<String> versions = new ArrayList<>();
        versions.add("V8.7.1.0 Android");
        versions.add("V8.7.1.0 web");
        versions.add("V8.7.10 iOS");
        CfDataSerach.findForecastTime("社区-871版本",map);
        //实例化ShowData对象
        for(int i=0; i<reports.size(); i++){
            ShowData data = new ShowData();
            // 名称
            Tester user = reports.get(i);

            // 漏测bug数
            int overSightBugNum = JsoupConnect.getOverSightBugNum(versions,user.getUsername());
            // 历史漏测数
            int historyOverSight = JsoupConnect.getHistoryOverSight(versions,user.getUsername());
            // 有效bug数
            int validBugNum = JsoupConnect.getValidBugNum(versions,user.getUsername());

            Float totalLogged = JsoupConnect.getTotalLogged(user.getEmail(), 1576022400000L, 1577145600000L);

            data.setId(user.getId());
            data.setUsername(user.getUsername());
            data.setBug(overSightBugNum);
            data.setHistoryMissingRate(historyOverSight);
            data.setValidBug(validBugNum);
            data.setTotalLogged(totalLogged);
            data.setForecastTime(map.get(user.getUsername()).getForecastTime());
            list.add(data);
        }
        return list;
    }

    /**
     * 展示版本号
     * @return
     */
    @RequestMapping("/selectVersion")
    @ResponseBody
    public List<VersionData> selectVersion(){
        List<VersionData> versionData = versiondataService.selectVersion();
        return versionData;
    }

    /***
     * 获取多选下拉框的值:
     * @author guoshuang
     * @date 2020/1/15 23:18:56
     */
    @CrossOrigin(maxAge = 3600)
    @PostMapping("/getversionAndDate")
    public List<Object> getVersionAndDate(FormData formData){
        String[] split = formData.getVersions().split(",");
        List<String> versions = Arrays.asList(split);

        String endTime = formData.getEndTime();
        String startTime = formData.getStartTime();
        log.info("起始时间："+formData);
        return null;
    }



    /**
     * 导出到excel
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    @CrossOrigin(maxAge = 3600)
    private void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取数据
        List<ShowData> data = test();

        //excel标题
        String[] title = {"名称","漏测bug数","有效bug总数","各模块总用时（单位:h）","预估用时","历史漏测数"};

        //excel文件名
        String fileName = "数据表"+System.currentTimeMillis()+".xls";

        //sheet名
        String sheetName = "数据表";

        String [][] content = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            content[i] = new String[title.length];
            ShowData obj = data.get(i);
            content[i][0] = obj.getUsername().toString();
            content[i][1] = obj.getBug().toString();
            content[i][2] = obj.getHistoryMissingRate().toString();
            content[i][3] = obj.getValidBug().toString();
            content[i][4] = obj.getTotalLogged().toString();
            content[i][5] = obj.getForecastTime().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
