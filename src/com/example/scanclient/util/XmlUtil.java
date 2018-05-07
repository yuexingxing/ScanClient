package com.example.scanclient.util;

public class XmlUtil {

	public static String HelloWorld(String phoneNum){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<HelloWorld  xmlns=\"http://tempuri.org/\">"  
				+"<tryConnectDb>"+phoneNum+"</tryConnectDb>"  
				+"</HelloWorld >"  
				+" </soap:Body>"  
				+"</soap:Envelope>";  

		return soapXML;  
	}  

	public static String RFLogin(String ClientID, String UserID, String Password, String AppVer, String DvcID, String Network){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<RFLogin  xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<ClientID>"+ClientID+"</ClientID>"  
				+"<UserID>"+UserID+"</UserID>"  
				+"<Password>"+Password+"</Password>"  
				+"<AppVer>"+AppVer+"</AppVer>"  
				+"<DvcID>"+DvcID+"</DvcID>"  
				+"<Network>"+Network+"</Network>"  
				+"</para>"
				+"</RFLogin >"  
				+" </soap:Body>"  
				+"</soap:Envelope>";  

		return soapXML;  
	}  

	public static String PupQueryOrderHeader(String OrderID, String OrderDate, String UserID, String DvcID){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<PupQueryOrderHeader  xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<OrderID>"+OrderID+"</OrderID>"  
				+"<OrderDate>"+OrderDate+"</OrderDate>"  
				+"<UserID>"+UserID+"</UserID>"  
				+"<DvcID>"+DvcID+"</DvcID>"  
				+"</para>"
				+"</PupQueryOrderHeader >"  
				+" </soap:Body>"  
				+"</soap:Envelope>";  

		return soapXML;  
	}  
	
	public static String PodQueryOrderDetail(String OrderID, String UserID, String DvcID){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<PodQueryOrderDetail xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<OrderID>"+OrderID+"</OrderID>"  
				+"<UserID>"+UserID+"</UserID>"  
				+"<DvcID>"+DvcID+"</DvcID>"  
				+"</para>"
				+"</PodQueryOrderDetail>"  
				+" </soap:Body>"  
				+"</soap:Envelope>";  

		return soapXML;  
	}  
}
