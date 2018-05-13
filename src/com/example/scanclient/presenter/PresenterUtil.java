package com.example.scanclient.presenter;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.example.scanclient.db.dao.LoadingDetailDao;
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
					jsonObject = new JSONObject(jsonObject.optJSONObject("RFLoginResult").toString());

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					jsonObject = new JSONObject(jsonObject.optJSONObject("User").toString());

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
	 * 订单列表查询
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

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderTitle").toString());

					final List<OrderInfo> dataList = new ArrayList<OrderInfo>();
					final OrderInfo info = new GsonBuilder().create().fromJson(jsonObject.toString(), new TypeToken<OrderInfo>(){}.getType());

					final PupHeaderDao pupHeaderDao = new PupHeaderDao();
					if(pupHeaderDao.checkData(info.getOrderID()) < 1){
						pupHeaderDao.addData(info);
						dataList.add(info);
						callback.callback(success, message, dataList);
					}else{
						CommandTools.showChooseDialog(context, "是否删除本地表中数据", new CommandToolsCallback() {

							@Override
							public void callback(int position) {
								// TODO Auto-generated method stub
								if(position == 0){
									pupHeaderDao.deleteById(info.getOrderID());

									new PupDetailDao().deleteById(info.getOrderID());
									new PupScanDao().deleteById(info);
									CommandTools.showToast("删除成功");

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
	 * 订单详情查询
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

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetail").toString());
					List<OrderInfo> dataList = new ArrayList<OrderInfo>();
					//接口不改，只能前端判断是否是数组和对象
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

	/**
	 * 提货扫描上传
	 * @param context
	 * @param callback
	 */
	public static void PupUpload(Context context, List<OrderInfo> list, String DvcID, final ObjectCallback callback){

		SoapUtil.post(API.PodUpload , XmlUtil.PodUpload (list, DvcID), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodUploadResponse").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject("PodUploadResult").toString());

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					// flag 1 是成功，-1 失败，失败的时候才有 MSG 
					CommandTools.showToast("提交成功");
					callback.callback(true, message, jsonObject.toString());

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
	public static void LoadingQueryOrderHeader(Context context, final String actionName, final OrderInfo info, final ObjectCallback callback){

		SoapUtil.post(actionName, XmlUtil.LoadingQueryOrderHeader(actionName, info), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Response").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Result").toString());

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderTitle").toString());

					OrderInfo orderInfo = new GsonBuilder().create().fromJson(jsonObject.toString(), new TypeToken<OrderInfo>(){}.getType());
					orderInfo.setOrderID(info.getOrderID());

					callback.callback(success, message, orderInfo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void LoadingQueryOrderDetail(Context context, final String actionName, final OrderInfo mOrderInfo, final ObjectCallback callback){

		SoapUtil.post(actionName, XmlUtil.LoadingQueryOrderDetail(actionName, mOrderInfo), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Response").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Result").toString());

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}

					jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetail").toString());


					LoadingDetailDao loadingDetailDao = new LoadingDetailDao();
					List<OrderInfo> dataList = new ArrayList<OrderInfo>();
					//接口不改，只能前端判断是否是数组和对象
					if(jsonObject.toString().contains("[") && jsonObject.toString().contains("]")){

						JSONArray jsonArray = new JSONArray(jsonObject.optJSONArray("OrderDetailEty").toString());
						int len = jsonArray.length();
						for(int i=0; i<len; i++){

							JSONObject jsonObject1 = jsonArray.optJSONObject(i);
							OrderInfo orderInfo = new GsonBuilder().create().fromJson(jsonObject1.toString(), new TypeToken<OrderInfo>(){}.getType());
							orderInfo.setOrderID(mOrderInfo.getOrderID());
							orderInfo.setScanTime(mOrderInfo.getScanTime());
							orderInfo.setCph(mOrderInfo.getCph());
							dataList.add(orderInfo);

							loadingDetailDao.addData(orderInfo);//保存到本地表
						}
					}else{

						jsonObject = new JSONObject(jsonObject.optJSONObject("OrderDetailEty").toString());
						OrderInfo orderInfo = new GsonBuilder().create().fromJson(jsonObject.toString(), new TypeToken<OrderInfo>(){}.getType());
						orderInfo.setOrderID(mOrderInfo.getOrderID());
						orderInfo.setScanTime(mOrderInfo.getScanTime());
						orderInfo.setCph(mOrderInfo.getCph());
						dataList.add(orderInfo);

						loadingDetailDao.addData(orderInfo);//保存到本地表
					}

					callback.callback(success, message, dataList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void LoadingUpload(Context context, final String actionName, List<OrderInfo> list, OrderInfo mOrderInfo, final ObjectCallback callback){

		SoapUtil.post(actionName, XmlUtil.LoadingUpload(actionName, list, mOrderInfo), new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {

				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(data.toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Response").toString());
					jsonObject = new JSONObject(jsonObject.optJSONObject(actionName + "Result").toString());

					JSONObject jsonObjectRst = new JSONObject(jsonObject.optJSONObject("Rst").toString());
					String flag = jsonObjectRst.optString("Flag");
					if(!flag.equals("1")){
						CommandTools.showToast(jsonObjectRst.optString("Msg"));
						return;
					}else{
						CommandTools.showToast("提交成功");
					}

					callback.callback(success, message, null);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
