package util;
import java.io.*;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.SslUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.bean.InfoBean;
import com.dao.InfoDao;
public class pa2 implements PageProcessor {
    static int num=0;
    static String Id;
    static String Question;
    static String Question_user;
    static String Question_date;
    static String Question_info;
    static String Answer;
    static String Answer_user;
    static String Answer_date;
    static String Answer_info;
    static String Url;
    //static String regEx="[\n`~!@#$%^&()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？? ]";
    static String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static int count =0;

    @Override
    public Site getSite() {
        return site;
    }
    //主页面
    public void parent(Page page)
    {

        System.out.println("抓取的内容\n"+
                page.getHtml().xpath("//span[@name='cutStr' and @dispLength='68']//text()").get()
        );
    }
    //子页面
    public void child(Page page) throws IOException {

        System.out.println("RRRRRRRRR");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\578095023\\FileRecv\\寒假作业\\大三寒假作业\\list.txt")),
                "UTF-8"));
        String line=null;
        System.out.println("SSSSSSSS");
        while((line=br.readLine())!=null)
        {

            String url= "http://www.beijing.gov.cn/hudong/hdjl/com.web.consult.";
            String type="";//声明类型码
            type="consultDetail.flow?originalId=";
            url+=type;
            url+=line;
            System.out.println(url);
            page.addTargetRequest(url);

            url= "http://www.beijing.gov.cn/hudong/hdjl/com.web.consult.";
            type="";//声明类型码
            type="suggesDetail.flow?originalId=";
            url+=type;
            url+=line;
            System.out.println(url);
            page.addTargetRequest(url);

            url= "http://www.beijing.gov.cn/hudong/hdjl/com.web.consult.";
            type="";//声明类型码
            type="complainDetail.flow?originalId=";
            url+=type;
            url+=line;
            System.out.println(url);
            page.addTargetRequest(url);
        }

        if(page.getUrl().regex("http://www.beijing.gov.cn/hudong/hdjl/com.web.search.mailList.flow").match())
        {

            parent(page);
        }
        else
        {
            Question=page.getHtml().xpath("//div[contains(@class, 'col-xs-10')]/strong//text()").get().trim();
            // Question=Question.replaceAll(regEx, aa);

            Question_user=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')]/div[contains(@class, 'col-xs-10') and contains(@class, 'text-muted')]//text()").get().trim();
            //Question_user=Question_user.replaceAll(regEx, aa);
            Question_user=Question_user.replaceAll("来信人", aa).trim();
            Question_user=Question_user.replaceAll("：", aa).trim();
            Question_date=page.getHtml().xpath("//div[contains(@class, 'col-xs-12')]/div[contains(@class, 'col-xs-5')]//text()").get();
            // Question=Question.replaceAll(regEx, aa);
            Question_date=Question_date.replaceAll("时间", aa).trim();
            Question_date=Question_date.replaceAll("：", aa).trim();

            Question_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'mx-2') ]//text()").get();
            //Question_info=Question_info.replaceAll(regEx, aa);

            Answer=page.getHtml().xpath("//div[contains(@class, 'col-xs-9') and contains(@class, 'my-2')]//text()").get();
            //Answer=Answer.replaceAll(regEx, aa);

            Answer_user=page.getHtml().xpath("//div[contains(@class, 'col-xs-9') and contains(@class, 'my-2')]//text()").get();
            // Answer_user=Answer_user.replaceAll(regEx, aa);

            Answer_date=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'col-sm-3')and contains(@class, 'col-md-3') and contains(@class, 'my-2')]//text()").get();
            // Answer_date=Answer_date.replaceAll(regEx, aa);
            Answer_date=Answer_date.replaceAll("答复时间", aa).trim();
            Answer_date=Answer_date.replaceAll("：", aa).trim();


            List<String> values=new ArrayList<String>();
            values=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//*//text()").all();
            Answer_info=null;
            for(String value:values)
            {
                Answer_info+=value;
            }
            if(Answer_info==null)
            {
                Answer_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//text()").get();
            }
            Answer_info=Answer_info.replaceAll("？", aa).trim();
            Answer_info=Answer_info.replaceAll("null", aa).trim();

            Url=page.getUrl().get();
            System.out.println("抓取的内容\n"+
                    page.getHtml().xpath("//div[contains(@class, 'col-xs-10')]/strong//text()").get()
            );

            System.out.println("Id:" + Id+
                    "\n Question:" + Question+
                    "\\n Question_user:" + Question_user+
                    "\n Question_date:" + Question_date+
                    "\n Question_info:" + Question_info+
                    "\n Answer:" + Answer+
                    "\n Answer_user:" + Answer_user+
                    "\n Answer_date:" + Answer_date+
                    "\n Answer_info:"+Answer_info+
                    "\n Url:"+Url);
            InfoDao.add(Question, Question_user, Question_date, Question_info, Answer, Answer_user, Answer_date, Answer_info, Url);
        }
        count ++;
    }
    @Override
    public void process(Page page) {
        num=num+1;
        if(num==1)
        {
            try {
                child(page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Question=page.getHtml().xpath("//div[contains(@class, 'col-xs-10')]/strong//text()").get().trim();
            // Question=Question.replaceAll(regEx, aa);

            Question_user=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')]/div[contains(@class, 'col-xs-10') and contains(@class, 'text-muted')]//text()").get().trim();
            //Question_user=Question_user.replaceAll(regEx, aa);
            Question_user=Question_user.replaceAll("来信人", aa).trim();
            Question_user=Question_user.replaceAll("：", aa).trim();
            Question_date=page.getHtml().xpath("//div[contains(@class, 'col-xs-12')]/div[contains(@class, 'col-xs-5')]//text()").get();
            // Question=Question.replaceAll(regEx, aa);
            Question_date=Question_date.replaceAll("时间", aa).trim();
            Question_date=Question_date.replaceAll("：", aa).trim();

            Question_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'mx-2') ]//text()").get();
            //Question_info=Question_info.replaceAll(regEx, aa);

            Answer=page.getHtml().xpath("//div[contains(@class, 'col-xs-9') and contains(@class, 'my-2')]//text()").get();
            //Answer=Answer.replaceAll(regEx, aa);

            Answer_user=page.getHtml().xpath("//div[contains(@class, 'col-xs-9') and contains(@class, 'my-2')]//text()").get();
            // Answer_user=Answer_user.replaceAll(regEx, aa);

            Answer_date=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'col-sm-3')and contains(@class, 'col-md-3') and contains(@class, 'my-2')]//text()").get();
            // Answer_date=Answer_date.replaceAll(regEx, aa);
            Answer_date=Answer_date.replaceAll("答复时间", aa).trim();
            Answer_date=Answer_date.replaceAll("：", aa).trim();

            List<String> values=new ArrayList<String>();
            values=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//*//text()").all();
            Answer_info=null;
            for(String value:values)
            {
                Answer_info+=value;
            }
            if(Answer_info==null)
            {
                Answer_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//text()").get();
            }
            Answer_info=Answer_info.replaceAll("？", aa).trim();
            Answer_info=Answer_info.replaceAll("null", aa).trim();

            Url=page.getUrl().get();
            System.out.println("抓取的内容\n"+
                    page.getHtml().xpath("//div[contains(@class, 'col-xs-10')]/strong//text()").get()
            );

            System.out.println("Id:" + Id+
                    "\n Question:" + Question+
                    "\\n Question_user:" + Question_user+
                    "\n Question_date:" + Question_date+
                    "\n Question_info:" + Question_info+
                    "\n Answer:" + Answer+
                    "\n Answer_user:" + Answer_user+
                    "\n Answer_date:" + Answer_date+
                    "\n Answer_info:"+Answer_info+
                    "\n Url:"+Url);
            InfoDao.add(Question, Question_user, Question_date, Question_info, Answer, Answer_user, Answer_date, Answer_info, Url);
        }

    }

    public static void main(String[] args) {
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // jsoup("http://www.beijing.gov.cn/hudong/hdjl/com.web.search.mailList.flow");
        long startTime, endTime;
        System.out.println("开始爬取...");
        InfoDao.delete();
        startTime = System.currentTimeMillis();
        Spider.create(new pa2()).addUrl("http://www.beijing.gov.cn/hudong/hdjl/com.web.search.mailList.flow").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }


}

