package com.example.scanclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.scanclient.util.OkHttpUtil.CallBackData;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SoapUtil {

	static class CallBackData{

		private boolean success;
		private String message;
		private Object data;
		private ObjectCallback callback;

		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public ObjectCallback getCallback() {
			return callback;
		}
		public void setCallback(ObjectCallback callback) {
			this.callback = callback;
		}
	}

	public static void post(final String actionName, final String soapXML, final ObjectCallback callback){

		new Thread(new Runnable() {  

			@Override  
			public void run() {  

				//1：创建服务地址  
				URL url;
				try {
					url = new URL("http://47.97.207.208/apiService/RFService.asmx?");

					//2：打开到服务地址的一个连接  
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
					//3：设置连接参数  
					//3.1设置发送方式：POST必须大写  
					connection.setRequestMethod("POST");  
					//3.2设置数据格式：Content-type  
					connection.setRequestProperty("content-type", "text/xml;charset=utf-8");  
					//3.3设置输入输出，新创建的connection默认是没有读写权限的，  
					connection.setDoInput(true);  
					connection.setDoOutput(true);  

					//4：组织SOAP协议数据，发送给服务端  
					OutputStream os = connection.getOutputStream();  
					os.write(soapXML.getBytes());  

					//5：接收服务端的响应  
					StringBuilder sb = new StringBuilder(); 
					int responseCode = connection.getResponseCode();  
					if(200 == responseCode){//表示服务端响应成功  
						InputStream is = connection.getInputStream();  
						InputStreamReader isr = new InputStreamReader(is);  
						BufferedReader br = new BufferedReader(isr);  

						String temp = null;  

						while(null != (temp = br.readLine())){  
							sb.append(temp);  
						}   

						is.close();  
						isr.close();  
						br.close();  
					}  

					os.close();  

					Log.v("zd", "服务器返回: " + sb.toString());
					XmlToJson xmlToJson = new XmlToJson.Builder(sb.toString()).build();
					String jsonStr = xmlToJson.toJson().toString();

					JSONObject jsonObject = new JSONObject(jsonStr);
					jsonObject = new JSONObject(jsonObject.optJSONObject("soap:Envelope").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("soap:Body").toString());

					Log.i("zd", jsonObject.toString());

					CallBackData callBackData = new CallBackData();
					callBackData.setData(jsonObject.toString());
					callBackData.setCallback(callback);

					Message msg = new Message();
					msg.what = 0x1001;
					msg.obj = callBackData;
					mHandler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}  
		}).start();  
	}

	public static Handler mHandler = new Handler(){

		public void handleMessage(Message msg){

			if(msg.what == 0x1001){

				CallBackData callBackData = (CallBackData) msg.obj;
				if(callBackData.getCallback() != null){
					callBackData.getCallback().callback(callBackData.isSuccess(), callBackData.getMessage(), callBackData.getData());
				}
			}
		}
	};

	/**
	 * 调用WebService的方法
	 * @return
	 */
	public static String helloWorld() {
		// 命名空间
		String nameSpace = "http://tempuri.org/";
		// 调用的方法名称
		String methodName = "HelloWorld";
		// EndPoint
		String endPoint = "http://47.97.207.208/apiService/RFService.asmx?op=HelloWorld";
		// SOAP Action
		String soapAction = "http://tempuri.org/HelloWorld";//
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);
		/**
		 * 设置需调用WebService接口需要传入的两个参数szUserName、szOrderId、szVerifyString
		 * 如果不需要参数的话，则不写
		 * 这里不需要参数，所以注释了
		 */
		rpc.addProperty("tryConnectDb", 1);
		//        rpc.addProperty("UserID", "admin");
		//        rpc.addProperty("Password", "123");
		//        rpc.addProperty("AppVer", "1.0");
		//        rpc.addProperty("DvcID", "631008030699");
		//        rpc.addProperty("Network", "1");
		//生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc); 

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		/**
		 * 获取返回的结果
		 * IXJCWI_GetVerResult为返回结果键值对的Key值
		 * szResult才是你需要value值，也就是返回的结果
		 */

		String szResult = "";
		//        szResult = object.getProperty("HelloWorldResult").toString();
		return object + "";
	}

	/**
	 * 手机号段归属地查询
	 *
	 * @param phoneSec 手机号段
	 */
	public static String getRemoteInfo(){

		//    	 // 命名空间
		//        String nameSpace = "http://tempuri.org/";
		//        // 调用的方法名称
		//        String methodName = "HelloWorld";
		//        // EndPoint
		//        String endPoint = "http://47.97.207.208/apiService/RFService.asmx?";
		//        // SOAP Action
		//        String soapAction = "http://tempuri.org/HelloWorld";//
		//        // 指定WebService的命名空间和调用的方法名
		//        SoapObject rpc = new SoapObject(nameSpace, methodName);

		String WSDL_URI = "http://47.97.207.208/apiService/RFService.asmx?op=HelloWorld";//wsdl 的uri
		String namespace = "http://tempuri.org/";//namespace
		String methodName = "HelloWorld";//要调用的方法名称

		SoapObject request = new SoapObject(namespace, methodName);
		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		request.addProperty("tryConnectDb", 1);

		//创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
		envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

		HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);
		try {
			httpTransportSE.call(null, envelope);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//调用

		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		String result = object.getProperty(0).toString();
		Log.d("debug", result);
		return result;

	}
}
