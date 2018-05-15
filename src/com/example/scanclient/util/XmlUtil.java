package com.example.scanclient.util;

import java.util.List;

import android.util.Log;

import com.example.scanclient.MyApplication;
import com.example.scanclient.info.OrderInfo;

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

		Log.v("soapXML", soapXML);
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
				+"</soap:Body>"  
				+"</soap:Envelope>";  

		Log.v("soapXML", soapXML);
		return soapXML;  
	}  

	public static String PodUpload (List<OrderInfo> list, String DvcID){  

		int len = list.size();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++){

			OrderInfo info = list.get(i);

			sb.append("<PodScanMsgEty>")
			.append("<OrderID>").append(info.getOrderID()).append("</OrderID>")
			.append("<CargoID>").append(info.getCargoID()).append("</CargoID>")
			.append("<Count>").append(info.getCount()).append("</Count>")
			.append("<Rmk>").append(info.getRemark()).append("</Rmk>")
			.append("<UserID>").append(info.getUserID()).append("</UserID>")
			.append("<ScanTime>").append(info.getScanTime().replace(" ", "T").toString()).append("</ScanTime>")
			.append("</PodScanMsgEty>");//ScanTime这个参数要注意下格式，中间不能出现空格，否则xml格式上传会报错
		}

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Header>" 
				+"<Authentication xmlns=\"http://tempuri.org/\">" 
				+"<AccessKey>"+12+"</AccessKey>"
				+"<Device>"+12+"</Device>"
				+"</Authentication>" 
				+"</soap:Header>" 
				+"<soap:Body>"
				+"<PodUpload xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<Consignee>"+12+"</Consignee>"  
				+"<SignOrg>"+123+"</SignOrg>" 
				+"<Telephone>"+"15555555555"+"</Telephone>" 
				+"<Cellphone>"+"15555555555"+"</Cellphone>" 
				+"<DeliverStatus>"+1+"</DeliverStatus>" 
				+"<CrtBillNo>"+11203630+"</CrtBillNo>" 
				+"<DvcID>"+DvcID+"</DvcID>" 

				+"<MsgList>"  

				+sb.toString() 

				+"</MsgList>"  
				+"</para>"
				+"</PodUpload>"  
				+"</soap:Body>"  
				+"</soap:Envelope>";  

		Log.v("soapXML", soapXML);
		return soapXML;  
	}  

	public static String LoadingQueryOrderHeader(String actionName, OrderInfo info){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<" + actionName + " xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<OrderID>"+info.getOrderID()+"</OrderID>"  
				+"<UserID>"+info.getUserID()+"</UserID>"  
				+"<DvcID>"+CommandTools.getMIME(MyApplication.getInstance())+"</DvcID>"  
				+"</para>"
				+ "</" + actionName + ">"
				+"</soap:Body>"  
				+"</soap:Envelope>";  

		Log.v("soapXML", soapXML);
		return soapXML;  
	}  

	public static String LoadingQueryOrderDetail(String actionName, OrderInfo mOrderInfo){  

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Body>"  
				+"<" + actionName + " xmlns=\"http://tempuri.org/\">"  
				+"<para>"
				+"<OrderID>"+mOrderInfo.getOrderID()+"</OrderID>"  
				+"<UserID>"+mOrderInfo.getUserID()+"</UserID>"  
				+"<DvcID>"+CommandTools.getMIME(MyApplication.getInstance())+"</DvcID>"  
				+"</para>"
				+"</" + actionName + ">"
				+"</soap:Body>"  
				+"</soap:Envelope>";  

		Log.v("soapXML", soapXML);
		return soapXML;  
	}  

	public static String LoadingUpload(String actionName, List<OrderInfo> list, OrderInfo mOrderInfo){  

		String method = "";
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			method = API.LoadingScanMsgEty;
		}else{
			method = API.UnloadingScanMsgEty;
		}

		int len = list.size();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++){

			OrderInfo info = list.get(i);

			sb.append("<" + method + ">")
			.append("<OrderID>").append(info.getOrderID()).append("</OrderID>")
			.append("<CargoID>").append(info.getCargoID()).append("</CargoID>")
			.append("<Count>").append(info.getCount()).append("</Count>")
			.append("<Rmk>").append(info.getRemark()).append("</Rmk>")
			.append("<UserID>").append(info.getUserID()).append("</UserID>")
			.append("<ScanTime>").append(info.getScanTime().replace(" ", "T").toString()).append("</ScanTime>")
			.append("</" + method + ">");//ScanTime这个参数要注意下格式，中间不能出现空格，否则xml格式上传会报错
		}

		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"   
				+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"  
				+"<soap:Header>" 
				+"<Authentication xmlns=\"http://tempuri.org/\">" 
				+"<AccessKey>"+12+"</AccessKey>"
				+"<Device>"+12+"</Device>"
				+"</Authentication>" 
				+"</soap:Header>" 
				+"<soap:Body>"
				+"<" + actionName + " xmlns=\"http://tempuri.org/\">"   
				+"<para>"
				+"<Cph>" + mOrderInfo.getCph() + "</Cph>"  
				+"<Driver>" + mOrderInfo.getDriver() + "</Driver>" 
				+"<Telephone>" + mOrderInfo.getTelephone() + "</Telephone>" 
				+"<Cellphone>" + mOrderInfo.getCellphone() + "</Cellphone>" 
				+"<GpsCode>" + mOrderInfo.getGpsCode() + "</GpsCode>" 
				+"<StationName>" + mOrderInfo.getStationName() + "</StationName>" 
				+"<TransferPhone>" + mOrderInfo.getTransferPhone() + "</TransferPhone>" 
				+"<CrtBillNo>" + mOrderInfo.getCrtBillNo() + "</CrtBillNo>"
				+"<DvcID>" + CommandTools.getMIME(MyApplication.getInstance()) + "</DvcID>"

				+"<MsgList>"  

				+sb.toString() 

				+"</MsgList>"  
				+"</para>"
				+"</" + actionName + ">"
				+"</soap:Body>"  
				+"</soap:Envelope>";  

		Log.v("soapXML", soapXML);
		return soapXML;  
	}  
}
