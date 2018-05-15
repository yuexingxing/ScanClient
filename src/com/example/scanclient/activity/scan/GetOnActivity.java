package com.example.scanclient.activity.scan;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.R.layout;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.db.dao.LoadingCarInfoDao;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 上车、下车
 * 根据scan_type区分类型
 * @author yxx
 *
 */
public class GetOnActivity extends BaseActivity {

	@ViewInject(R.id.geton_carplate) EditText edtCarPlate;
	@ViewInject(R.id.geton_driver) EditText edtDriver;
	@ViewInject(R.id.geton_phone1) EditText edtPhone1;
	@ViewInject(R.id.geton_phone2) EditText edtPhone2;
	@ViewInject(R.id.geton_gps) EditText edtGps;
	@ViewInject(R.id.geton_sitename) EditText edtSiteName;
	@ViewInject(R.id.geton_phone3) EditText edtPhone3;
	
	private OrderInfo mOrderInfo = new OrderInfo();
	
	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_get_on);
		ViewUtils.inject(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		
		mOrderInfo.setScanType(getIntent().getStringExtra("scan_type"));
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			setTitle("上车");
		}else{
			setTitle("下车");
		}
	}
	
	public void onEventMainThread(Object event) {  
		  
	    String msg = event.toString();  
	}  

	public void toBack(View v){

		finish();
	}
	
	public void toNext(View v){

		String carPlate = edtCarPlate.getText().toString();
		if(TextUtils.isEmpty(carPlate)){
			CommandTools.showToast("请输入车牌号");
			return;
		}
		
		String driver = edtDriver.getText().toString();
		String phone1 = edtPhone1.getText().toString();
		String phone2 = edtPhone2.getText().toString();
		String phone3 = edtPhone3.getText().toString();
		String siteName = edtSiteName.getText().toString();
		String gpsCode = edtGps.getText().toString();
		
		mOrderInfo.setCph(carPlate);
		mOrderInfo.setTelephone(phone1);
		mOrderInfo.setCellphone(phone2);
		mOrderInfo.setTelephone(phone3);
		mOrderInfo.setStationName(siteName);
		mOrderInfo.setDriver(driver);
		mOrderInfo.setScanTime(CommandTools.getTime());
		mOrderInfo.setGpsCode(gpsCode);
		
		LoadingCarInfoDao loadingCarInfoDao = new LoadingCarInfoDao();
		int n = loadingCarInfoDao.checkData(mOrderInfo);
		if(n > 0){
			
			boolean flag = loadingCarInfoDao.updateData(mOrderInfo);
			if(flag){
				CommandTools.showToast("数据更新成功");
			}
		}else{
			loadingCarInfoDao.addData(mOrderInfo);
		}
		
		mOrderInfo.setCph(carPlate);
		mOrderInfo.setScanID("123");
		mOrderInfo.setUserID("admin");
		
		Intent intent = new Intent(this, GetOnInfoActivity.class);
		intent.putExtra("order_info", mOrderInfo);
		startActivity(intent);
	}
	
	public void onStop(){
		super.onStop();

		MyApplication.getEventBus().unregister(this);
	}
}
