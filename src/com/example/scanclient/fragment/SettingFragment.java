package com.example.scanclient.fragment;

import com.example.scanclient.BtConfigActivity;
import com.example.scanclient.LoginActivity;
import com.example.scanclient.R;
import com.example.scanclient.printer.JQPrinter;
import com.example.scanclient.printer.JQPrinter.PRINTER_TYPE;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
import com.example.scanclient.util.SharedPreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author xugang
 *
 */
public class SettingFragment extends Fragment implements OnClickListener{

	private View view;
	@ViewInject(R.id.setting_fragment_printer) Button btnPrinter;
	@ViewInject(R.id.setting_fragment_zhuxiao) Button btnZhuXiao;

	private final static int REQUEST_BT_ENABLE = 0;
	private final static int REQUEST_BT_ADDR = 1;	

	public static JQPrinter printer = null;
	public static BluetoothAdapter btAdapter = null;
	private boolean mBtOpenSilent = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_setting_fragment, container, false);

		ViewUtils.inject(this, view);
		initData();
		setOnClick();
		return view;
	}

	public void initData(){

		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) { 
			Toast.makeText(getActivity(), "本机没有找到蓝牙硬件或驱动！", Toast.LENGTH_SHORT).show(); 
			return;
		}

		// 如果本地蓝牙没有开启，则开启  
		//以下操作需要在AndroidManifest.xml中注册权限android.permission.BLUETOOTH ；android.permission.BLUETOOTH_ADMIN
		if (!btAdapter.isEnabled()) { 
//			Toast.makeText(getActivity(), "正在开启蓝牙", Toast.LENGTH_SHORT).show(); 
			if (!mBtOpenSilent)
			{
				// 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，  
				// 那么将会收到RESULT_OK的结果，  
				// 如果RESULT_CANCELED则代表用户不愿意开启蓝牙  
				Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
				startActivityForResult(mIntent, REQUEST_BT_ENABLE ); 
			}
			else { // 用enable()方法来开启，无需询问用户(无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。  
				btAdapter.enable();
//				Toast.makeText(getActivity(), "本地蓝牙已打开", Toast.LENGTH_SHORT).show(); 
			}        	
		}
		else
		{
			Toast.makeText(getActivity(), "本地蓝牙已打开", Toast.LENGTH_SHORT).show(); 
		} 
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() 
	{
		@Override
		public void onReceive(android.content.Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action))
			{
				if (printer != null)
				{
					if (printer.isOpen)
						printer.close();
				}
				Toast.makeText(context,"蓝牙连接已断开",1).show();
			}	
		};
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){ 
		super.onActivityResult(requestCode, resultCode, data); 
		if (requestCode == REQUEST_BT_ENABLE) 
		{ 
			if (resultCode == getActivity().RESULT_OK) 
			{ 
//				Toast.makeText(getActivity(), "蓝牙已打开", Toast.LENGTH_SHORT).show();
			} 
			else if (resultCode == getActivity().RESULT_CANCELED) 
			{ 
//				Toast.makeText(getActivity(), "不允许蓝牙开启", Toast.LENGTH_SHORT).show(); 
				return;
			}         
		}
		else if (requestCode == REQUEST_BT_ADDR)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				String btDeviceString = data.getStringExtra(BtConfigActivity.EXTRA_BLUETOOTH_DEVICE_ADDRESS);
				if (btDeviceString != null)
				{
					btnPrinter.setText("蓝牙名称:"+data.getStringExtra(BtConfigActivity.EXTRA_BLUETOOTH_DEVICE_NAME) + "\r\n地址:" + btDeviceString);

					if(btAdapter.isDiscovering())
						btAdapter.cancelDiscovery();							

					if (printer != null)
					{
						printer.close();
					}

					printer = new JQPrinter(btAdapter,btDeviceString);						

					if (!printer.open(PRINTER_TYPE.ULT113x))
					{
						Toast.makeText(getActivity(), "打印机Open失败", Toast.LENGTH_SHORT).show(); 
						return;
					}

					if (!printer.wakeUp())
						return;

					Log.e("JQ", "printer open ok");

					//	Toast.makeText(this, " 找到外部蓝牙设备", Toast.LENGTH_LONG).show();		

					IntentFilter filter = new IntentFilter();
					filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);//蓝牙断开
					getActivity().registerReceiver(mReceiver, filter);
				}
			}
			else
			{
				btnPrinter.setText("选择打印机");
			}
		} 
	}

	public void setOnClick(){

		btnZhuXiao.setOnClickListener(this);
		btnPrinter.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == btnZhuXiao.getId()){

			toLogout();
		}else if(arg0.getId() == btnPrinter.getId()){
			
			if (btAdapter == null){
				CommandTools.showToast("请检查蓝牙设置");
				return;
			}
			
			btnPrinter.setText("请等待");
			Intent myIntent = new Intent(getActivity(), BtConfigActivity.class);
			startActivityForResult(myIntent, REQUEST_BT_ADDR);
		}
	}
	
	public void toLogout(){

		new AlertDialog.Builder(getActivity())
		.setTitle("提示")
		.setMessage("确认注销登录？")
		.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//注销后清空密码，保留账号信息
				SharedPreferencesUtils.setParam(getActivity(), Constant.SP_LOGIN_PSD, "");

				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		}).setNegativeButton("取消", null).show();
	}
}
