package com.example.scanclient;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.activity.MainMenuActivity;
import com.example.scanclient.db.dao.SysCodeDao;
import com.example.scanclient.info.SysCode;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.view.SingleItemDialog;
import com.example.scanclient.view.SingleItemDialog.SingleItemCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * 登录
 * @author yxx
 *
 */
public class LoginActivity extends Activity {
	
	@ViewInject(R.id.login_btn_name) Button btnName;
	@ViewInject(R.id.login_id) EditText edtId;
	@ViewInject(R.id.login_psd) EditText edtPsd;
	
	List<SysCode> listSysCode = new ArrayList<SysCode>();
	List<String> listSysCodeName = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		
		initData();
	}
	
	private void initData(){
		
		SysCodeDao sysCodeDao = new SysCodeDao();
		listSysCode.addAll(sysCodeDao.selectAllData());
		
		int len = listSysCode.size();
		for(int i=0; i<len; i++){
			
			listSysCodeName.add(listSysCode.get(i).getName());
			Log.v("zd", listSysCode.get(i).getName());
		}
	}
	
	public void selMan(View v){
		
		SingleItemDialog.showDialog(this, "请选择", false, listSysCodeName, new SingleItemCallBack() {
			
			@Override
			public void callback(int pos) {
				// TODO Auto-generated method stub
				
				btnName.setText(listSysCode.get(pos).getName());
				edtId.setText(listSysCode.get(pos).getID());
			}
		});
	}

	public void login(View v){
		
		String strId = edtId.getText().toString();
		String strPsd = edtPsd.getText().toString();

		if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strPsd)){
			
			CommandTools.showToast("账号或密码不能为空");
			return;
		}
//		PresenterUtil.HelloWorld(LoginActivity.this, null);
		PresenterUtil.RFLogin(LoginActivity.this, strId, strPsd, "123", "1.0", CommandTools.getMIME(this), "wifi", new ObjectCallback() {
			
			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub
				
				MyApplication.mUserInfo.setName("admin");
				
				Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // 获取 back键

			exit();
		}
		return false;
	}
	
	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage("确认退出程序？")
		.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				MyApplication.finishAllActivities();
				finish();
			}
		}).setNegativeButton("取消", null).show();
	}
}
