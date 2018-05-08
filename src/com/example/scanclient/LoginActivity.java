package com.example.scanclient;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.activity.MainMenuActivity;
import com.example.scanclient.db.dao.SysCodeDao;
import com.example.scanclient.info.SysCode;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * ��¼
 * @author yxx
 *
 */
public class LoginActivity extends Activity {

	@ViewInject(R.id.login_id) EditText edtId;
	@ViewInject(R.id.login_psd) EditText edtPsd;
	
	List<SysCode> listSysCode = new ArrayList<SysCode>();
	
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
			
			System.out.println(listSysCode.get(i).getName());
		}
	}

	public void login(View v){
		
		String strId = edtId.getText().toString();
		String strPsd = edtPsd.getText().toString();

		if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strPsd)){
			
			CommandTools.showToast("�˺Ż����벻��Ϊ��");
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

		if (keyCode == KeyEvent.KEYCODE_BACK) { // ��ȡ back��

			exit();
		}
		return false;
	}
	
	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("��ʾ")
		.setMessage("ȷ���˳�����")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				MyApplication.finishAllActivities();
				finish();
			}
		}).setNegativeButton("ȡ��", null).show();
	}
}
