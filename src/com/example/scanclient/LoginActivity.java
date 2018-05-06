package com.example.scanclient;

import com.example.scanclient.activity.MainMenuActivity;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.SoapUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

/**
 * 登录
 * @author yxx
 *
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

	}

	public void login(View v){

//		PresenterUtil.HelloWorld(LoginActivity.this, null);
		PresenterUtil.RFLogin(LoginActivity.this, "123321", "admin", "123", "1.0", "631008030699", "wifi", new ObjectCallback() {
			
			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub
				
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
