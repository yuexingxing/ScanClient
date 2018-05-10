package com.example.scanclient;

import java.util.ArrayList;
import java.util.List;
import com.example.scanclient.activity.MainMenuActivity;
import com.example.scanclient.db.dao.SysCodeDao;
import com.example.scanclient.info.SysCode;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.SharedPreferencesUtils;
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
import android.widget.EditText;
import android.widget.TextView;

/**
 * 登录
 * @author yxx
 *
 */
public class LoginActivity extends Activity {

	@ViewInject(R.id.login_btn_name) TextView tvName;
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

		String loginId = SharedPreferencesUtils.getParam(this, Constant.SP_LOGIN_ID, "").toString();
		String loginPsd = SharedPreferencesUtils.getParam(this, Constant.SP_LOGIN_PSD, "").toString();

		SysCodeDao sysCodeDao = new SysCodeDao();
		listSysCode.addAll(sysCodeDao.selectAllData());

		int len = listSysCode.size();
		for(int i=0; i<len; i++){

			SysCode sysCode = listSysCode.get(i);
			listSysCodeName.add(sysCode.getName());
			if(sysCode.getID().equals(loginId)){
				edtId.setText(loginId);
				edtPsd.setText(loginPsd);
				tvName.setText(sysCode.getName());
			}

			Log.v("zd", sysCode.getID() + "/" + sysCode.getName());
		}


	}

	public void selMan(View v){

		SingleItemDialog.showDialog(this, "请选择", false, listSysCodeName, new SingleItemCallBack() {

			@Override
			public void callback(int pos) {
				// TODO Auto-generated method stub

				tvName.setText(listSysCode.get(pos).getName());
				edtId.setText(listSysCode.get(pos).getID());
			}
		});
	}

	public void login(View v){

		final String strId = edtId.getText().toString();
		final String strPsd = edtPsd.getText().toString();

		if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strPsd)){

			CommandTools.showToast("账号或密码不能为空");
			return;
		}
		//		PresenterUtil.HelloWorld(LoginActivity.this, null);
		PresenterUtil.RFLogin(LoginActivity.this, "123456", strId, strPsd, "1.0", CommandTools.getMIME(this), "wifi", new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub

				MyApplication.mUserInfo.setName("admin");

				SharedPreferencesUtils.setParam(LoginActivity.this, Constant.SP_LOGIN_ID, strId);
				SharedPreferencesUtils.setParam(LoginActivity.this, Constant.SP_LOGIN_PSD, strPsd);

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
