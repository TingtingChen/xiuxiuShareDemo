package com.meitu.ablum.util;

import com.meitu.ablum.entity.Tester;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guoshuang
 * @date 2019/12/28
 */

@Slf4j
public class CfDataSerach {
    private static Map<String, String> cookies;
    private static final String cfUrl = "http://cf.meitu.com/";

    static {
        try {
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 模拟登录获取cookie和sessionid
     */
    public static void login() throws IOException {
        String urlLogin = "http://cf.meitu.com/confluence/login.action?os_destination=%2Findex.action&permissionViolation=true";
        Connection connect = Jsoup.connect(urlLogin);
        // 伪造请求头
        connect.header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Encoding",
                "gzip, deflate");
        connect.header("Accept-Language", "zh-CN,zh;q=0.9").header("Connection", "keep-alive");
        connect.header("Content-Length", "72").header("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        connect.header("Host", "cf.meitu.com").header("Referer", "http://cf.meitu.com/confluence/index.action");
        connect.header("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest");

        // 携带登陆信息
        connect.data("os_username", "gs2")
                .data("os_password", "GSpass2019!");

        //请求url获取响应信息
        Connection.Response res = connect.ignoreContentType(true).method(Connection.Method.POST).execute();// 执行请求
        // 获取返回的cookie
        cookies = res.cookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            log.info(entry.getKey() + "-" + entry.getValue());
        }
        log.info("---------华丽的分割线-----------");
    }



    public static final String versionListUrl = "http://cf.meitu.com/confluence/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=false&spaceKey=mdxx&treeId=0&startDepth=0&mobile=false&ancestors=80623727&ancestors=5308516&treePageId=93846737";
    public static Float findForecastTime(String version, Map<String, Tester> testers) throws IOException {
        Map<String,String>  versionUrlMap = new HashMap<>();
        // 获取各个版本的目标地址
        if(versionUrlMap.size()==0) {
            Document document = Jsoup.connect(versionListUrl).cookies(cookies).get();

            Elements elements = document.select("span[class='plugin_pagetree_children_span']");
            elements.forEach(element -> {
                if(element.text().contains("社区-")) {
                    String aimUrl = element.select("a").attr("href");
                    versionUrlMap.put(element.text(),aimUrl);
                }
            });
        }

        String versionUrl = versionUrlMap.get(version);
        log.info("请求版本的预估时长连接："+cfUrl+versionUrl);
        Document document = Jsoup.connect(cfUrl+versionUrl).cookies(cookies).get();

        Element table = document.select("table[class='relative-table confluenceTable']").get(1);
        Elements trs = table.select("table[class='relative-table confluenceTable']").select("tr");
        System.out.println(trs.toString());
        Elements th = trs.select("th");

        int startIndex = 0;
        for (int i = 0; i < th.size(); i++) {
            if (th.get(i).text().contains("一轮")){
                startIndex = i;
                break;
            }
        }


        for (int i = 1; i < trs.size(); i++) {

            Elements tds = trs.get(i).select("td");
            String strTime = tds.get(startIndex).text();
            if(StringUtils.isEmpty(strTime)){
                continue;
            }

            String regEx="-*\\d+(\\.\\d+)?";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(strTime);
            String group = "0";
            if(m.find()){
                group = m.group(0);
            }
            Float time = Float.valueOf(group);
            String android = tds.get(startIndex+1).text();
            String ios = tds.get(startIndex+2).text();

            if (testers.containsKey(android)){
                Tester tester = testers.get(android);
                tester.setForecastTime(tester.getForecastTime()+time);
            }

            if (testers.containsKey(ios)){
                Tester tester = testers.get(ios);
                tester.setForecastTime(tester.getForecastTime()+time);
            }
        }

        return 1.0f;
    }


    public static void main(String[] args) {
        String regEx="-*\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("3.1");

        System.out.println(m.find());

        System.out.println(m.group(0));
    }
}
