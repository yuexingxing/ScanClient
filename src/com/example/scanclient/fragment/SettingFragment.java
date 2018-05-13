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
			Toast.makeText(getActivity(), "����û���ҵ�����Ӳ����������", Toast.LENGTH_SHORT).show(); 
			return;
		}

		// �����������û�п���������  
		//���²�����Ҫ��AndroidManifest.xml��ע��Ȩ��android.permission.BLUETOOTH ��android.permission.BLUETOOTH_ADMIN
		if (!btAdapter.isEnabled()) { 
//			Toast.makeText(getActivity(), "���ڿ�������", Toast.LENGTH_SHORT).show(); 
			if (!mBtOpenSilent)
			{
				// ����ͨ��startActivityForResult()���������Intent������onActivityResult()�ص������л�ȡ�û���ѡ�񣬱����û�������Yes������  
				// ��ô�����յ�RESULT_OK�Ľ����  
				// ���RESULT_CANCELED������û���Ը�⿪������  
				Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
				startActivityForResult(mIntent, REQUEST_BT_ENABLE ); 
			}
			else { // ��enable()����������������ѯ���û�(����Ϣ�Ŀ��������豸),��ʱ����Ҫ�õ�android.permission.BLUETOOTH_ADMINȨ�ޡ�  
				btAdapter.enable();
//				Toast.makeText(getActivity(), "���������Ѵ�", Toast.LENGTH_SHORT).show(); 
			}        	
		}
		else
		{
			Toast.makeText(getActivity(), "���������Ѵ�", Toast.LENGTH_SHORT).show(); 
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
				Toast.makeText(context,"���������ѶϿ�",1).show();
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
//				Toast.makeText(getActivity(), "�����Ѵ�", Toast.LENGTH_SHORT).show();
			} 
			else if (resultCode == getActivity().RESULT_CANCELED) 
			{ 
//				Toast.makeText(getActivity(), "��������������", Toast.LENGTH_SHORT).show(); 
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
					btnPrinter.setText("��������:"+data.getStringExtra(BtConfigActivity.EXTRA_BLUETOOTH_DEVICE_NAME) + "\r\n��ַ:" + btDeviceString);

					if(btAdapter.isDiscovering())
						btAdapter.cancelDiscovery();							

					if (printer != null)
					{
						printer.close();
					}

					printer = new JQPrinter(btAdapter,btDeviceString);						

					if (!printer.open(PRINTER_TYPE.ULT113x))
					{
						Toast.makeText(getActivity(), "��ӡ��Openʧ��", Toast.LENGTH_SHORT).show(); 
						return;
					}

					if (!printer.wakeUp())
						return;

					Log.e("JQ", "printer open ok");

					//	Toast.makeText(this, " �ҵ��ⲿ�����豸", Toast.LENGTH_LONG).show();		

					IntentFilter filter = new IntentFilter();
					filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);//�����Ͽ�
					getActivity().registerReceiver(mReceiver, filter);
				}
			}
			else
			{
				btnPrinter.setText("ѡ���ӡ��");
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
				CommandTools.showToast("������������");
				return;
			}
			
			btnPrinter.setText("��ȴ�");
			Intent myIntent = new Intent(getActivity(), BtConfigActivity.class);
			startActivityForResult(myIntent, REQUEST_BT_ADDR);
		}
	}
	
	public void toLogout(){

		new AlertDialog.Builder(getActivity())
		.setTitle("��ʾ")
		.setMessage("ȷ��ע����¼��")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//ע����������룬�����˺���Ϣ
				SharedPreferencesUtils.setParam(getActivity(), Constant.SP_LOGIN_PSD, "");

				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		}).setNegativeButton("ȡ��", null).show();
	}
}
