package com.example.scanclient.util;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
  
/** 
 *  
 * <p>Title: HttpClient.java</p> 
 * <p>Description:HttpURLConnection���÷�ʽ</p> 
 */  
public class test {  
  
    public static void main(String[] args) throws IOException {  
    	
    	System.out.println("123");
//        //1�����������ַ  
//        URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");  
//        //2���򿪵������ַ��һ������  
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
//        //3���������Ӳ���  
//        //3.1���÷��ͷ�ʽ��POST�����д  
//        connection.setRequestMethod("POST");  
//        //3.2�������ݸ�ʽ��Content-type  
//        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");  
//        //3.3��������������´�����connectionĬ����û�ж�дȨ�޵ģ�  
//        connection.setDoInput(true);  
//        connection.setDoOutput(true);  
//  
//        //4����֯SOAPЭ�����ݣ����͸������  
//        String soapXML = getXML("1866666666");  
//        OutputStream os = connection.getOutputStream();  
//        os.write(soapXML.getBytes());  
//          
//        //5�����շ���˵���Ӧ  
//        int responseCode = connection.getResponseCode();  
//        if(200 == responseCode){//��ʾ�������Ӧ�ɹ�  
//            InputStream is = connection.getInputStream();  
//            InputStreamReader isr = new InputStreamReader(is);  
//            BufferedReader br = new BufferedReader(isr);  
//              
//            StringBuilder sb = new StringBuilder();  
//            String temp = null;  
//              
//            while(null != (temp = br.readLine())){  
//                sb.append(temp);  
//            }  
//              
//            System.out.println(sb.toString());  
//              
//            is.close();  
//            isr.close();  
//            br.close();  
//        }  
//  
//        os.close();  
    }  
      
    /** 
     * <?xml version="1.0" encoding="utf-8"?> 
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"> 
            <soap:Body> 
                <getMobileCodeInfo xmlns="http://WebXml.com.cn/"> 
                    <mobileCode>string</mobileCode> 
                    <userID>string</userID> 
                </getMobileCodeInfo> 
            </soap:Body> 
        </soap:Envelope> 
     * @param phoneNum 
     * @return 
     */  
    public static String getXML(String phoneNum){  
        String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
        +"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
            +"<soap:Body>"  
            +"<getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">"  
                +"<mobileCode>"+phoneNum+"</mobileCode>"  
              +"<userID></userID>"  
            +"</getMobileCodeInfo>"  
         +" </soap:Body>"  
        +"</soap:Envelope>";  
        return soapXML;  
    }  
}  
