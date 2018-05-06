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

				//1�����������ַ  
				URL url;
				try {
					url = new URL("http://47.97.207.208/apiService/RFService.asmx?op=" + actionName);

					//2���򿪵������ַ��һ������  
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
					//3���������Ӳ���  
					//3.1���÷��ͷ�ʽ��POST�����д  
					connection.setRequestMethod("POST");  
					//3.2�������ݸ�ʽ��Content-type  
					connection.setRequestProperty("content-type", "text/xml;charset=utf-8");  
					//3.3��������������´�����connectionĬ����û�ж�дȨ�޵ģ�  
					connection.setDoInput(true);  
					connection.setDoOutput(true);  

					//4����֯SOAPЭ�����ݣ����͸������  
					OutputStream os = connection.getOutputStream();  
					os.write(soapXML.getBytes());  

					//5�����շ���˵���Ӧ  
					StringBuilder sb = new StringBuilder(); 
					int responseCode = connection.getResponseCode();  
					if(200 == responseCode){//��ʾ�������Ӧ�ɹ�  
						InputStream is = connection.getInputStream();  
						InputStreamReader isr = new InputStreamReader(is);  
						BufferedReader br = new BufferedReader(isr);  

						String temp = null;  

						while(null != (temp = br.readLine())){  
							sb.append(temp);  
						}  

						System.out.println(sb.toString());  

						is.close();  
						isr.close();  
						br.close();  
					}  

					os.close();  

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
	 * ����WebService�ķ���
	 * @return
	 */
	public static String helloWorld() {
		// �����ռ�
		String nameSpace = "http://tempuri.org/";
		// ���õķ�������
		String methodName = "HelloWorld";
		// EndPoint
		String endPoint = "http://47.97.207.208/apiService/RFService.asmx?op=HelloWorld";
		// SOAP Action
		String soapAction = "http://tempuri.org/HelloWorld";//
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject rpc = new SoapObject(nameSpace, methodName);
		/**
		 * ���������WebService�ӿ���Ҫ�������������szUserName��szOrderId��szVerifyString
		 * �������Ҫ�����Ļ�����д
		 * ���ﲻ��Ҫ����������ע����
		 */
		rpc.addProperty("tryConnectDb", 1);
		//        rpc.addProperty("UserID", "admin");
		//        rpc.addProperty("Password", "123");
		//        rpc.addProperty("AppVer", "1.0");
		//        rpc.addProperty("DvcID", "631008030699");
		//        rpc.addProperty("Network", "1");
		//���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		// �����Ƿ���õ���dotNet������WebService
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc); 

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// ����WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��ȡ���ص�����
		SoapObject object = (SoapObject) envelope.bodyIn;
		/**
		 * ��ȡ���صĽ��
		 * IXJCWI_GetVerResultΪ���ؽ����ֵ�Ե�Keyֵ
		 * szResult��������Ҫvalueֵ��Ҳ���Ƿ��صĽ��
		 */

		String szResult = "";
		//        szResult = object.getProperty("HelloWorldResult").toString();
		return object + "";
	}

	/**
	 * �ֻ��Ŷι����ز�ѯ
	 *
	 * @param phoneSec �ֻ��Ŷ�
	 */
	public static String getRemoteInfo(){

		//    	 // �����ռ�
		//        String nameSpace = "http://tempuri.org/";
		//        // ���õķ�������
		//        String methodName = "HelloWorld";
		//        // EndPoint
		//        String endPoint = "http://47.97.207.208/apiService/RFService.asmx?";
		//        // SOAP Action
		//        String soapAction = "http://tempuri.org/HelloWorld";//
		//        // ָ��WebService�������ռ�͵��õķ�����
		//        SoapObject rpc = new SoapObject(nameSpace, methodName);

		String WSDL_URI = "http://47.97.207.208/apiService/RFService.asmx?op=HelloWorld";//wsdl ��uri
		String namespace = "http://tempuri.org/";//namespace
		String methodName = "HelloWorld";//Ҫ���õķ�������

		SoapObject request = new SoapObject(namespace, methodName);
		// ���������WebService�ӿ���Ҫ�������������mobileCode��userId
		request.addProperty("tryConnectDb", 1);

		//����SoapSerializationEnvelope ����ͬʱָ��soap�汾��(֮ǰ��wsdl�п�����)
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.bodyOut = request;//�����Ƿ�����������������bodyOut
		envelope.dotNet = true;//������.net������webservice����������Ҫ����Ϊtrue

		HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);
		try {
			httpTransportSE.call(null, envelope);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//����

		// ��ȡ���ص�����
		SoapObject object = (SoapObject) envelope.bodyIn;
		// ��ȡ���صĽ��
		String result = object.getProperty(0).toString();
		Log.d("debug", result);
		return result;

	}
}
