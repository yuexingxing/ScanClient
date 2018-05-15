package com.example.scanclient.activity.scan;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.VoiceHint;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 扫描比对
 * @author xugang
 *
 */
public class ScanCompareActivity extends BaseActivity {

	@ViewInject(R.id.scan_compare_billcode_1) EditText edtBillcode1;
	@ViewInject(R.id.scan_compare_billcode_2) EditText edtBillcode2;
	@ViewInject(R.id.scan_compare_billcode_3) EditText edtBillcode3;

	private String billcode1;
	private String billcode2;
	private String billcode3;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_scan_compare);
		ViewUtils.inject(this);
		MyApplication.getEventBus().register(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		setTitle("扫描比对");
	}

	public void onEventMainThread(Object event) {  

		String billcode = event.toString();  
		checkData(billcode);
	}  

	public void checkData(String billcode){

		billcode1 = edtBillcode1.getText().toString();
		if(TextUtils.isEmpty(billcode1)){
			edtBillcode1.setText(billcode);
			return;
		}

		billcode2 = edtBillcode2.getText().toString();
		if(TextUtils.isEmpty(billcode2)){
			edtBillcode2.setText(billcode);
			billcode2 = billcode;

			if(!billcode1.equals(billcode2)){

				VoiceHint.playErrorSounds();
				CommandTools.showToast("订单号不一致");
				return;
			}

			return;
		}

		billcode3 = edtBillcode3.getText().toString();
		if(TextUtils.isEmpty(billcode3)){
			edtBillcode3.setText(billcode);
			billcode3 = billcode;

			if(!billcode3.equals(billcode2)){

				VoiceHint.playNewOrderSounds();
				CommandTools.showToast("订单号不一致");
				return;
			}
		}

		if(billcode1.equals(billcode2) && billcode2.equals(billcode3)){

			VoiceHint.playRightSounds();
			Toast.makeText(this, "恭喜，三个订单号完全一致", Toast.LENGTH_LONG).show();

			clear(null);
		}else{
			
			VoiceHint.playErrorSounds();
			Toast.makeText(this, "订单号不一致", Toast.LENGTH_LONG).show();
		}

	}

	public void clear(View v){

		billcode1 = "";
		billcode2 = "";
		billcode3 = "";

		edtBillcode1.setText("");
		edtBillcode2.setText("");
		edtBillcode3.setText("");
	}

	public void toBack(View v){

		finish();
	}

	public void onStop(){
		super.onStop();

		MyApplication.getEventBus().unregister(this);
	}
}
