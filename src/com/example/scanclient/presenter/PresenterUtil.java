package com.example.scanclient.presenter;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.example.scanclient.db.dao.PupDetailDao;
import com.example.scanclient.db.dao.PupHeaderDao;
import com.example.scanclient.db.dao.PupScanDao;
import com.example.scanclient.db.dao.SysCodeDao;
import com.example.scanclient.info.BillInfo;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.info.SysCode;
import com.example.scanclient.util.API;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil;
import com.example.scanclient.util.CommandTools.CommandToolsCallback;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.SoapUtil;
import com.example.scanclient.util.XmlUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
public class PresenterUtil {

	/**
	 * ���Խӿ�
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
	 * ��¼�ӿ�
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
					JSONObject jsonObjectResult = new JSONObject(jsonObject.optJSONObject("RFLoginResult").toString());
					jsonObject = new JSONObject(jsonObjectResult.optJSONObject("Rst").toString());
					String flag = jsonObject.optString("flag");
					//					if(!flag.equals("1")){
					//						
					//						String msg = jsonObject.optString("msg");
					//						CommandTools.showToast(msg);
					//						return;
					//					}

					jsonObject = new JSONObject(jsonObjectResult.optJSONObject("User").toString());

					SysCode sysCode = new SysCode();
					sysCode.setID(jsonObject.optString("UserID"));
					sysCode.setName(jsonObject.optString("ChnName"));
					sysCode.setType(jsonObject.optString("OrgID"));

					SysCodeDao sysCodeDao = new SysCodeDao();
					if(sysCodeDao.checkData(sysCode.getID()) < 1){
						sysCodeDao.addData(sysCode);
					}

					callback.callback(success, message, jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * �����б��ѯ
	 * @param context
	 * @param callback
	 */
	public static void PupQueryOrderHeader(final Context context, String OrderID, String OrderDate, String UserID, String DvcID, final ObjectCallback callback){

		SoapUtil.post(API.PupQueryOrderHeader, XmlUtil.PupQueryOrderHeader(OrderID, OrderDate, UserID, DvcID), new ObjectCallback() {

			@Override
			public void callback(final boolean success, final String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PupQueryOrderHeaderResponse").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PupQueryOrderHeaderResult").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderTitle").toString());

					final List<OrderInfo> dataList = new ArrayList<OrderInfo>();
					final OrderInfo info = new GsonBuilder().create().fromJson(jsonObject.toString(), new TypeToken<OrderInfo>(){}.getType());

					final PupHeaderDao pupHeaderDao = new PupHeaderDao();
					if(pupHeaderDao.checkData(info.getOrderID()) < 1){
						pupHeaderDao.addData(info);
						dataList.add(info);
						callback.callback(success, message, dataList);
					}else{
						CommandTools.showChooseDialog(context, "�Ƿ�ɾ�����ر�������", new CommandToolsCallback() {

							@Override
							public void callback(int position) {
								// TODO Auto-generated method stub
								if(position == 0){
									pupHeaderDao.deleteById(info.getOrderID());

									new PupDetailDao().deleteById(info.getOrderID());
									new PupScanDao().deleteById(info.getOrderID());
									CommandTools.showToast("ɾ���ɹ�");

									pupHeaderDao.addData(info);

									dataList.add(info);

									callback.callback(success, message, dataList);
								}
							}
						});
					}


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ���������ѯ
	 * @param context
	 * @param callback
	 */
	public static void PodQueryOrderDetail(Context context, final String OrderID, String UserID, String DvcID, final ObjectCallback callback){

		SoapUtil.post(API.PodQueryOrderDetail, XmlUtil.PodQueryOrderDetail(OrderID, UserID, DvcID), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodQueryOrderDetailResponse").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodQueryOrderDetailResult").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetail").toString());

					List<OrderInfo> dataList = new ArrayList<OrderInfo>();
					//�ӿڲ��ģ�ֻ��ǰ���ж��Ƿ�������Ͷ���
					if(jsonObject.toString().contains("[") && jsonObject.toString().contains("]")){

						JSONArray jsonArray = new JSONArray(jsonObject.optJSONArray("OrderDetailEty").toString());
						int len = jsonArray.length();
						for(int i=0; i<len; i++){

							JSONObject jsonObject1 = jsonArray.optJSONObject(i);
							OrderInfo orderInfo = new GsonBuilder().create().fromJson(jsonObject1.toString(), new TypeToken<OrderInfo>(){}.getType());
							orderInfo.setOrderID(OrderID);
							dataList.add(orderInfo);
						}
					}else{

						jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetailEty").toString());
						OrderInfo orderInfo = new GsonBuilder().create().fromJson(jsonObject.toString(), new TypeToken<OrderInfo>(){}.getType());
						orderInfo.setOrderID(OrderID);
						dataList.add(orderInfo);
					}

					PupDetailDao pupDetailDao = new PupDetailDao();
					int len = dataList.size();
					for(int i=0; i<len; i++){

						OrderInfo orderInfo = dataList.get(i);
						pupDetailDao.addData(orderInfo);
					}

					callback.callback(success, message, dataList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
