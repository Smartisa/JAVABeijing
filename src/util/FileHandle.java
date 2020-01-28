package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FileHandle {

	//是否以追加的形式导出文件
	public void outFile(String txt,String outfile,boolean isappend) throws IOException
	{
		File fi=new File(outfile);
		FileOutputStream fop=new FileOutputStream(fi,isappend);
		OutputStreamWriter ops=new OutputStreamWriter(fop,"UTF-8");
		ops.append(txt);
		ops.close();
		fop.close();
	}
	//判断文件是否存在
	public boolean judgeFileExists(String path) {

		File file=new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}
	
	//导入文件时判断文件存在
	public boolean judeFileExistsNoDepend(File file) {

		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}
	
	//读取文件每一行的数据并且放在字符串容器中
	public List<String> inputFile(String path)
	{

		List<String> strlist=new ArrayList<String>();
		File a=new File(path);
		if(!judeFileExistsNoDepend(a))
		{
			System.out.println(path+"文件不存在");
			return strlist;
		}
		FileInputStream b;
		try {
			b = new FileInputStream(a);
			InputStreamReader c=new InputStreamReader(b,"UTF-8");


			{
				BufferedReader bufr =new BufferedReader(c);
				String line = null;
				while((line = bufr.readLine())!=null){
					//line是每一行的数据
					strlist.add(line);
				}
				bufr.close();
			}
			c.close();
			b.close();
		} catch ( IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return strlist;
	}
	
	
	//获得文件的内容行数
	public long getLineNumber(String strfile) {
		File file=new File(strfile);
	    if (file.exists()) {
	        try {
	            FileReader fileReader = new FileReader(file);
	            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
	            lineNumberReader.skip(Long.MAX_VALUE);
	            long lines = lineNumberReader.getLineNumber() + 1;
	            fileReader.close();
	            lineNumberReader.close();
	            return lines;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return 0;
	}
	
	
	//删除文件中的第n行数据
	public String deleteLine(String filePath,int indexLine){             
		try {        
			List<String> ifList=inputFile(filePath);
			ifList.remove(indexLine);
			outFile(new StringHandle().StringListIntoString(ifList, "\r\n"),filePath,false);
		} catch (Exception e) {  
			return "fail :"+ e.getCause();     
		}      
		return "success!";   
	}
	
	
	//将字符串容器以行间修饰串存入文件中
	public void outFileByStringList(List<String> strlist,String outfile,String lineDecorate)
	{
		try {
			outFile(new StringHandle().StringListIntoString(strlist, lineDecorate),outfile,false);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//将字符串容器以空格和行间修饰串存入文件中
	public void outFileByStringListList(List<List<String>> strlist,String outfile,String lineDecorate)
	{
		try {
			StringHandle sh=new StringHandle();
			outFile(sh.StringListIntoString(sh.StringListListIntoStringList(strlist," "),lineDecorate ),outfile,false);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//获得文件的修改时间
	public String getModifiedTime(String path){  
        File f = new File(path);              
        Calendar cal = Calendar.getInstance();  
        String timechange="";
        //获取文件时间
        long time = f.lastModified();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        //转换文件最后修改时间的格式
        cal.setTimeInMillis(time);    

        timechange = formatter.format(cal.getTime());
        return timechange;
        //输出：修改时间[2]    2009-08-17 10:32:38  
    }  
	
	//判断文件是否为空
	public boolean fileIsEmpty(String path)
	{
		File fi=new File(path);
		
		if(fi.length()==0||!fi.exists())
			return true;
		else return false;
	}
	
	//获得文件目录里面的信息转为字符串的容器的容器
	public List<List<String>> getInfosList(String path)
	{
		StringHandle sh=new StringHandle();
		List<String> objline=null;
		objline=inputFile(path);
		List<List<String>> objinfo=sh.StringSplitByExpToStringList(objline, " ");;
		return objinfo;
	}
	
	//获得文件目录里面的信息转为对应类的容器
	public <T> List<T> getInfosToTlist(String path,String []nameNlist,Class<T> clazz)
	{
		StringHandle sh=new StringHandle();
		List<String> objline=null;
		objline=inputFile(path);
		List<T> objinfo=sh.StringSplitByExpToTList(objline, " ",nameNlist,clazz);;
		return objinfo;
	}
	
	//获得文件目录的里面的信息转为对应类的容器(自动型)
	public <T> List<T> getInfosToTlist(String path,Class<T> clazz)
	{
		StringHandle sh=new StringHandle();
		List<String> objline=null;
		objline=inputFile(path);
		List<T> objinfo=sh.StringSplitByExpToTList(objline, " ",clazz);;
		return objinfo;
	}
	
	//将对应类容器里面的所有数据存入对应文件中
	public <T> void outputFileByTlist(List<T> obj,Class<?> clazz,String path,boolean isAppend)
	{
		EntityToString ets=new EntityToString();
		StringHandle sh=new StringHandle();
		try {
			if(fileIsEmpty(path))
				outFile(sh.StringListIntoString(ets.getStringList(obj, clazz),"\r\n"),path,isAppend);
			else outFile("\r\n"+sh.StringListIntoString(ets.getStringList(obj, clazz),"\r\n"),path,isAppend);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public <T> void outputFileByTlist(List<T> obj,String path,boolean isAppend)
	{
		outputFileByTlist(obj,obj.get(0).getClass(),path,isAppend);
	}
	
	
	//将对应的类数据添加到对应文件中
	public <T> void outputFileByT(T obj,Class<?> clazz,String path,boolean isAppend)
	{
		EntityToString ets=new EntityToString();
		try {
			if(fileIsEmpty(path))
				outFile(ets.getString(obj, clazz),path,isAppend);
			else outFile("\r\n"+ets.getString(obj, clazz),path,isAppend);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public <T> void outputFileByT(T obj,String path,boolean isAppend)
	{
		outputFileByT(obj,obj.getClass(),path,isAppend);
	}
}
