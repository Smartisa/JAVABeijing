package util;
import java.io.IOException;
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
public class pa1 implements PageProcessor {
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
	public void child(Page page)
	{
		首都之眼 VALUE=new 首都之眼();
		VALUE.Value_start();
		/**
		 *   <div class="o-font3 col-md-12 pb-3">
		 <a onclick="letterdetail('咨询','AH20012300136')">
		 <font color=#a94442>·【咨询】</font>
		 <span name="cutStr" dispLength="68">关于新型冠状病毒防控问题</span>
		 </a>
		 </div>
		 page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()").get()
		 page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());

		 text = '''
		 <li class="li li-first" name="item"><a href="link.html">first item</a></li>
		 '''
		 html = etree.HTML(text)
		 result = html.xpath('//li[contains(@class, "li") and @name="item"]/a/text()')
		 print(result)
		 */
		page.addTargetRequests(
				page.getHtml().xpath("//div[contains(@class, \"o-fonts\")]/a/@onclick").all());
		//System.out.println(page.getHtml());
		//List<String> s=page.getHtml().xpath("//div[@class=o-font3]/a/@onclick").all();
		List<String> s=VALUE.lines_zi;
		for(String str:s)
		{
			System.out.println(str);
         	/*
             String jiequ1=str.substring(14, 16);
             String jiequ2=str.substring(19, 32);
             String url= "http://www.beijing.gov.cn/hudong/hdjl/com.web.consult.";
             String type="";//声明类型码
             if(jiequ1.equals("咨询")){
                 type="consultDetail.flow?originalId=";
                 url+=type;
             }else if(jiequ1.equals("建议")) {
                 type="suggesDetail.flow?originalId=";
                 url+=type;
             }else if(jiequ1.equals("投诉")) {
                 type="complainDetail.flow?originalId=";
                 url+=type;
             }
             url+=jiequ2;
             */
			String url= "http://www.beijing.gov.cn/hudong/hdjl/com.web.consult.";
			String type="";//声明类型码
			type="consultDetail.flow?originalId=";
			url+=type;
			url+=str;
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


			Answer_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//text()").get();
			//Answer_info=Answer_info.replaceAll(regEx, aa);

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
	public static void jsoup(String address)
	{
		Connection conn=Jsoup.connect(address).timeout(10000);
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			Document document=Jsoup.connect(address).timeout(10000).get();
			//Elements timea=document.getElementsByClass("rowClickTarget");
			Elements k=document.getElementsByTag("cutStr");

			System.out.println(k.size());
			//Dao dao=new Dao();
			Element b;
			for(int i=0;i<k.size();i++)
			{
				String str=k.get(i).text().trim();
				System.out.println("asdfasedfasdfasfasdf");
				System.out.println(str);

			}


		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void process(Page page) {
		num=num+1;
		if(num==1)
		{
			child(page);
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


			Answer_info=page.getHtml().xpath("//div[contains(@class, 'col-xs-12') and contains(@class, 'my-3')and contains(@class, 'p-4')]//text()").get();
			//Answer_info=Answer_info.replaceAll(regEx, aa);

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
		Spider.create(new pa1()).addUrl("http://www.beijing.gov.cn/hudong/hdjl/com.web.search.mailList.flow").thread(5).run();
		endTime = System.currentTimeMillis();
		System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
	}


}

