package util;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class GetTxt {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\578095023\\FileRecv\\������ҵ\\����������ҵ\\�������������ż�����ʵս\\list.txt")),
                "UTF-8"));
        String line=null;
        while((line=br.readLine())!=null)
        {
            System.out.println("AAAA");
            System.out.println(line);
        }
    }

}
