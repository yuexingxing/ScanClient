package com.example.scanclient.presenter;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.example.scanclient.util.API;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.SoapUtil;
import com.example.scanclient.util.XmlUtil;

public class PresenterUtil {

	/**
	 * 测试接口
	 * @param context
	 * @param callback
	 */
	public static void HelloWorld(Context context,  final ObjectCallback callback){

		SoapUtil.post(API.HelloWorld, XmlUtil.HelloWorld("1"), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("HelloWorldResponse").toString());
					String msg = jsonObject.optString("HelloWorldResult");

					CommandTools.showToast(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * 登录接口
	 * @param context
	 * @param callback
	 */
	public static void RFLogin(Context context, String ClientID, String UserID, String Password, String AppVer, String DvcID, String Network, final ObjectCallback callback){

		SoapUtil.post(API.RFLogin, XmlUtil.RFLogin(ClientID, UserID, Password, AppVer, DvcID, Network), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("RFLoginResponse").toString());
					String msg = jsonObject.optString("RFLoginResult");

					callback.callback(success, message, msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 订单查询
	 * @param context
	 * @param callback
	 */
	public static void PupQueryOrderHeader(Context context, String OrderID, String OrderDate, String UserID, String DvcID, final ObjectCallback callback){

		SoapUtil.post(API.PupQueryOrderHeader, XmlUtil.PupQueryOrderHeader(OrderID, OrderDate, UserID, DvcID), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PupQueryOrderHeaderResponse").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PupQueryOrderHeaderResult").toString());
					String msg = jsonObject.optString("OrderTitle");

					callback.callback(success, message, msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
