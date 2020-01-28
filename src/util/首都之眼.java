package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class 首都之眼 {
	//static List<String> lines_zi=new LinkedList<String>();
	//static List<String> lines_jian=new LinkedList<String>();
	//static List<String> lines_tou=new LinkedList<String>();
	
	static String line;
	public static void Value_start()
	{
		// TODO 自动生成的方法存根
WebClient webClient=new WebClient(BrowserVersion.CHROME); // 实例化Web客户端 
        
		System.out.println("AAAAAA");
        try {
        	webClient.getOptions().setActiveXNative(false);
        	//webClient.getOptions().setCssEnabled(false);
        	//webClient.getOptions().setRedirectEnabled(true);
        	webClient.getOptions().setJavaScriptEnabled(true);
        	webClient.getOptions().setDoNotTrackEnabled(true);
        	webClient.getOptions().setThrowExceptionOnScriptError(false);
        	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        	webClient.getCache().setMaxSize(100);
        	webClient.getOptions().setJavaScriptEnabled(true);//运行js脚本执行
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX
            webClient.getOptions().setCssEnabled(false);//忽略css
            webClient.getOptions().setUseInsecureSSL(true);//ssl安全访问
            webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
            //webClient.getOptions().setTimeout(50000);  //超时时间  ms
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getCache().clear();
	        webClient.setRefreshHandler(new ImmediateRefreshHandler());
	        webClient.getOptions().setTimeout(2*1000);	//网页多少ms超时响应
	        webClient.setJavaScriptTimeout(600*1000);   //javaScript多少ms超时
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
	        //webClient.setJavaScriptTimeout(600*1000);   
	        //webClient.getOptions().setRedirectEnabled(true); 
	        webClient.waitForBackgroundJavaScript(60*1000);
	        
	        HtmlPage page=webClient.getPage("http://www.beijing.gov.cn/hudong/hdjl/com.web.search.mailList.flow"); // 解析获取页面
	        HtmlElement a=page.getElementByName("nextPage");
	        int j=1,lastj=0;
	        FileHandle fh=new FileHandle();
	        StringHandle sh=new StringHandle();
	        List<String> lastInfo_zi=new ArrayList<String>();
	        List<String> lastInfo_jian=new ArrayList<String>();
	        List<String> lastInfo_tou=new ArrayList<String>();
	        System.out.println("asdfsdaf");
	        fh.outFile(""+"\r\n", "E:\\578095023\\FileRecv\\寒假作业\\大三寒假作业\\北京市政百姓信件分析实战\\list.txt", false);
	       
	        while(j!=600)
	        {
	        	
	        	String nowInfo=page.asXml();

	        	List<String> infoList_zi=sh.getExpString("letterdetail\\('.*?','.*?'\\)", nowInfo);
	        	int g_size_zi=infoList_zi.size();
	        	if(sh.StringListSameOutStringList(infoList_zi, lastInfo_zi).size()!=g_size_zi&&g_size_zi==7)
	        	{
	        		//System.out.println(g_size);
		        	for(int i=0;i<g_size_zi;i++)
		        	{
		        		String theWeb=infoList_zi.get(i).replaceAll("letterdetail\\('.*?','", "").replace("')", "");
		        		System.out.println(theWeb);
		        		//lines_zi.add(theWeb);
		        		fh.outFile(theWeb+"\r\n", "E:\\578095023\\FileRecv\\寒假作业\\大三寒假作业\\北京市政百姓信件分析实战\\list.txt", true);
		        		
		        		if(i==g_size_zi-1)
		        		{
		        			lastInfo_zi=infoList_zi;
		        			System.out.println(j);
		        			j++;
		        			break;
		        		}
		        			 
		        	}
		            page=a.click();
	        	}
	        	//page=a.click();
	        }
	        
	        
        }catch (FailingHttpStatusCodeException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            webClient.close(); // 关闭客户端，释放内存
        }
    
	}
	public static void main(String[] args) {
		Value_start();
	}

}


