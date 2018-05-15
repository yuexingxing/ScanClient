package com.example.scanclient;

import java.util.LinkedList;
import java.util.List;
import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.UserInfo;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.VoiceHint;

import okhttp3.OkHttpClient;
import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;

public class MyApplication extends Application{

	public static UserInfo mUserInfo = new UserInfo();

	public static MyApplication instance;
	public List<Activity> activityList = new LinkedList<Activity>();
	public static OkHttpClient mOkHttpClient;

	private static EventBus eventBus;

	public void onCreate() {
		super.onCreate();
		instance = this;
		mOkHttpClient = new OkHttpClient();

		new DBHelper(this);
		DBHelper.SQLiteDBHelper.getWritableDatabase();
		VoiceHint.initSound(instance);
		initScanner();

		if (eventBus == null) {
			eventBus = EventBus.getDefault();
			eventBus.register(this);
		}

	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public static MyApplication getInstance() {

		return instance;
	}

	/**
	 * ���Activity��������
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {

		if (!MyApplication.getInstance().activityList.contains(activity)) {
			MyApplication.getInstance().activityList.add(activity);
		}
	}

	/**
	 * �ͷ����е�Activity
	 */
	public static void finishAllActivities() {

		for (Activity activity : MyApplication.getInstance().activityList) {
			if (activity != null) {
				activity.finish();
			}
		}

		MyApplication.getInstance().activityList.clear();
		System.exit(0);
	}

	/**
	 * ����activity
	 */
	public static void clearActivityList() {

		for (Activity activity : MyApplication.getInstance().activityList) {
			if (activity != null) {
				activity.finish();
			}
		}

		MyApplication.getInstance().activityList.clear();
	}

	public static EventBus getEventBus() {

		if (eventBus == null) {
			eventBus.register(MyApplication.getInstance());
		}

		return eventBus;
	}

	// ����ɾ������ɨ��������
	public void onEventMainThread(Message msg) {

		if(msg.what == 0x0011){
			CommandTools.showToast((String) msg.obj);
		}
	}

	public void initScanner(){

		IntentFilter scanDataIntentFilter = new IntentFilter(); 
		scanDataIntentFilter.addAction("com.android.scancontext"); //ǰ̨���
		scanDataIntentFilter.addAction("com.android.scanservice.scancontext"); //��̨���
		registerReceiver(mScanDataReceiver, scanDataIntentFilter);

		//����ǰ̨���
//		Intent intent = new Intent("com.android.scanservice.output.foreground"); 
//		intent.putExtra("Scan_output_foreground", false); 
//		sendBroadcast(intent);
	}

	//Receiver
	private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver(){ 

		@Override 
		public void onReceive(Context context, Intent intent){ 

			String action = intent.getAction(); 
			if (action.equals("com.android.scancontext")){ 
				// ��ǰ̨���������ʱ�����ᷢ�ʹ�Intent 
				String str = intent.getStringExtra("Scan_context"); 
			} else if (action.equals("com.android.scanservice.scancontext")) { 

				String str = intent.getStringExtra("Scan_context"); 
				MyApplication.getEventBus().post(str);
			} 
		} 
	};
}
