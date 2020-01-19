package com.meitu.ablum.util;

import com.meitu.ablum.entity.BugEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsoupConnect {
    private static Map<String, String> cookies;
    private static String jiraUrl = "http://jira.meitu.com/issues/?filter=24048";

    static {
        try {
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        List<String> versions = new ArrayList<>();
        List<String> reports = new ArrayList<>();
        reports.add("lyj3@meitu.com");
        versions.add("V8.7.1.0 Android");
        versions.add("V8.7.1.0 web");
        versions.add("V8.7.10 iOS");
        String owner = "郭霜";
        login();
  /*      System.out.println(owner+"owner的bug漏测数是"+getOverSightBugNum(versions,owner));
        System.out.println("lyj3@meitu.com的有效bug数"+getValidBugNum(versions,reports));
        System.out.println("历史漏测数"+getHistoryOverSight());*/
/*        String url  = "project = MTXX AND issuetype = Bug AND creator in (currentUser(), membersOf(秀秀测试组)) AND affectedVersion = \"V8.7.10 iOS\" AND labels = 历史 AND \"Bug Resolve Solution\" in (以后解决, 已解决, 未解决)";
        List<BugEntity> content = getContent(url);
        System.out.println("爬取总数"+getNum(url));
        content.forEach(c-> System.out.println("爬取数据"+c.getKeyWord()));*/
        log.info(owner+"owner的bug漏测数是"+getOverSightBugNum(versions,owner));
    }


    /**
     * 模拟登录获取cookie和sessionid
     */
    public static void login() throws IOException {
        String urlLogin = "http://jira.meitu.com/login.jsp";
        Connection connect = Jsoup.connect(urlLogin);
        // 伪造请求头
        connect.header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Encoding",
                "gzip, deflate");
        connect.header("Accept-Language", "zh-CN,zh;q=0.9").header("Connection", "keep-alive");
        connect.header("Content-Length", "72").header("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        connect.header("Host", "jira.meitu.com").header("Referer", "http://jira.meitu.com/login.jsp");
        connect.header("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest");

        // 携带登陆信息
        connect.data("os_username", "")
                .data("os_password", "")
                .data("login", "登录");

        //请求url获取响应信息
        Connection.Response res = connect.ignoreContentType(true).method(Connection.Method.POST).execute();// 执行请求
        // 获取返回的cookie
        cookies = res.cookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            log.info(entry.getKey() + "-" + entry.getValue());
        }
        log.info("---------华丽的分割线-----------");
    }


    /**
     * 获取漏测bug数
     */
    public static int getOverSightBugNum(List<String> versions,String owner) throws IOException {
        String version = listToString(versions);
        // project = MTXX AND issuetype = Bug AND affectedVersion in ("V8.4.3.0 Android", "V8.4.3.0 web", "V8.4.30 iOS") AND labels = 燕君 AND "Bug Resolve Solution" in (以后解决, 已解决, 未解决) ORDER BY created DESC, summary DESC, key DESC
        /*String url = "project = MTXX AND issuetype = Bug AND affectedVersion in ("+version+") AND labels = "+owner+" AND \"Bug Resolve Solution\" in (以后解决, 已解决, 未解决) ORDER BY created DESC, summary DESC, key DESC";*/
        String url = "project = MTXX AND affectedVersion in ("+version+") AND labels = "+owner+" ORDER BY summary ASC, created DESC";
        return getNum(url);
    }


    /**
     * 有效bug总数
     */
    public static int getValidBugNum(List<String> versions,String reportName) throws IOException {
        String version = listToString(versions);
        String url = "project = MTXX AND issuetype = Bug AND affectedVersion in ("+version+") AND \"Bug Resolve Solution\" in (以后解决, 已解决, 未解决) AND reporter = "+reportName+" ORDER BY created DESC, summary DESC, key DESC";
        return getNum(url);
    }


    /**
     * ios/Android历史漏测
     */
    public static int getHistoryOverSight(List<String> versions,String reportName) throws IOException {
        String version = listToString(versions);
        /*String url  = "project = MTXX AND issuetype = Bug AND affectedVersion in ("+version+") AND labels = 历史 AND \"Bug Resolve Solution\" in (以后解决, 以后解决, 未解决, 已解决, 已解决, 未解决) AND reporter in ("+rpStr+") ORDER BY created DESC, summary DESC, key DESC";*/
        String url = "project = MTXX AND affectedVersion in ("+version+") AND labels = "+reportName+" && labels = 历史 ORDER BY summary ASC, created DESC";
        return getNum(url);
    }


    /**
     * 字符串解析
     */
    public static String listToString(List<String> list){
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("\""+list.get(i)+"\"");
            if (size>i+1){
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /**
     * 得到总数
     */
    public static int getNum(String paramsUrl) throws IOException {
        String encode =  URLEncoder.encode(paramsUrl, "utf-8");
        String url = jiraUrl+"&jql="+encode;

        log.info("查询语句"+paramsUrl);
        Document document = Jsoup.connect(url).cookies(cookies).post();
        Elements num = document.select("span[class='results-count-total results-count-link']");
        // log.info(issuetable);

        if(StringUtils.isEmpty(num.text())){
            return 0;
        }
        return Integer.parseInt(num.text());
    }



    /**
     * 实例化bug对象
     */
    public static  List<BugEntity> getContent(String paramsUrl) throws IOException {
        String encode =  URLEncoder.encode(paramsUrl, "utf-8");
        String url = jiraUrl+"&jql="+encode;

        log.info("查询语句"+paramsUrl);
        Document document = Jsoup.connect(url).cookies(cookies).post();
        Elements table = document.select("table[id='issuetable']");
        Elements trs = table.select("tr");
        int trSize = trs.size();
        List<BugEntity> list = new ArrayList<>(trSize);
        for (int i = 1; i < trSize; i++) {
            Elements tds = trs.get(i).select("td");
            BugEntity bugEntity = new BugEntity();
            bugEntity.setType(tds.get(0).text());
            bugEntity.setKeyWord(tds.get(1).text());
            list.add(bugEntity);
        }
        return list;
    }

    /**
     *获取总时长
     */
    public static Float getTotalLogged(String user,long startTime ,long entTime) throws IOException {
        String url = "http://jira.meitu.com/rest/jttp-rest/1/paging-report/page?filterConditionJson={%22issueAffectedVersions%22:[],%22issueAssignees%22:[],%22issueComponents%22:[],%22issueEpicLinkIssueIds%22:[],%22issueEpicName%22:%22%22,%22issueFixedVersions%22:[],%22issueKeys%22:[],%22issuePriorityIds%22:[],%22issueReporters%22:[],%22issueResolutionIds%22:[],%22issueStatusIds%22:[],%22issueTypeIds%22:[%2211601%22,%2210001%22],%22labels%22:[],%22projectIds%22:[10311]," +
                "%22groupUsers%22:[%22users:"+user+"%22,%22users:gs2@meitu.com%22],%22filter%22:[%2224431%22],%22searcherValue%22:%22basic%22,%22includeSubtask%22:false,%22includeEpic%22:false," +
                "%22worklogStartDate%22:"+startTime+",%22worklogEndDate%22:"+entTime+",%22limit%22:20,%22offset%22:0}&selectedColumnsJson=[%22jtrp_col_issueKey%22,%22jtrp_col_issueSummary%22,%22jtrp_col_type%22,%22jtrp_col_status%22,%22jtrp_col_assignee%22,%22jtrp_col_worklogDescription%22,%22jtrp_col_user%22,%22jtrp_col_timeSpent%22]&orderBy=undefined-DESC&maintab=summaries&subtab=tabs-project&selectedMore=&_=1577287263593";

        log.info("查询语句user= "+user+" ,startTime="+new Date(startTime)+", endTime="+new Date(entTime));
        Document document = Jsoup.connect(url).cookies(cookies).get();

        Elements table = document.select("table[class='aui reporting-table']");
        Elements trs = table.select("tr");
        if (trs.size()<1){
            log.warn("查无记录");
            return 0f;
        }
        Elements tds = trs.get(1).select("td");

        Float totalLogged = Float.valueOf(tds.get(4).text().replace("小时",""));

        log.info("查询到totalLogged总数为="+totalLogged);

        return totalLogged;

    }




}
