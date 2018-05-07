package com.example.scanclient.presenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.example.scanclient.info.BillInfo;
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
	 * 订单列表查询
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
					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderTitle").toString());
					
					List<BillInfo> dataList = new ArrayList<BillInfo>();
					BillInfo info = new BillInfo();
					info.setBillcode(jsonObject.optString("OrderID"));
					info.setScanTime(jsonObject.optString("OrderDate"));
					info.setStatus(jsonObject.optString("Status"));
					info.setCusBillcode(jsonObject.optString("CrtBillNo"));
					
					dataList.add(info);
					
					callback.callback(success, message, dataList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 订单详情查询
	 * @param context
	 * @param callback
	 */
	public static void PodQueryOrderDetail(Context context, String OrderID, String UserID, String DvcID, final ObjectCallback callback){

		SoapUtil.post(API.PodQueryOrderDetail, XmlUtil.PodQueryOrderDetail(OrderID, UserID, DvcID), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodQueryOrderDetailResponse").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodQueryOrderDetailResult").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetail").toString());
					JSONArray jsonArray = new JSONArray(jsonObject.optJSONArray("OrderDetailEty").toString());
					
					CommandTools.showToast(jsonArray.toString());
					List<BillInfo> dataList = new ArrayList<BillInfo>();
//					BillInfo info = new BillInfo();
//					info.setBillcode(jsonObject.optString("OrderID"));
//					info.setScanTime(jsonObject.optString("OrderDate"));
//					info.setStatus(jsonObject.optString("Status"));
//					info.setCusBillcode(jsonObject.optString("CrtBillNo"));
//					
//					dataList.add(info);
					
					callback.callback(success, message, dataList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
