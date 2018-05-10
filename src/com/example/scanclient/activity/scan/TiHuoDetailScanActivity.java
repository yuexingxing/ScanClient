package com.example.scanclient.activity.scan;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.db.dao.PupDetailDao;
import com.example.scanclient.db.dao.PupHeaderDao;
import com.example.scanclient.db.dao.PupScanDao;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.Res;
import com.example.scanclient.util.CommandTools.CommandToolsCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ���ɨ��
 * @author yxx
 *
 */
public class TiHuoDetailScanActivity extends BaseActivity {
	
	@ViewInject(R.id.tihuo_detail_scan_orderid) TextView tvOrderId;
	@ViewInject(R.id.tihuo_detail_scan_cargoid) EditText edtCargoId;
	@ViewInject(R.id.tihuo_detail_scan_count) EditText edtCount;
	@ViewInject(R.id.tihuo_detail_scan_weight) EditText edtWeight;
	@ViewInject(R.id.tihuo_detail_scan_remerk) EditText edtRemark;
	
	@ViewInject(R.id.tihuo_detail_scan_totalcount) TextView tvTotalCount;
	@ViewInject(R.id.tihuo_detail_scan_totalnum) TextView tvTotalNum;

	@ViewInject(R.id.lv_public) ListView listView;
	List<OrderInfo> dataList = new ArrayList<OrderInfo>();
	CommonAdapter<OrderInfo> commonAdapter;
	
	private String orderId;
	private PupScanDao pupScanDao;

	private int currPos = -1;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_ti_huo_detail_scan);
		ViewUtils.inject(this);
	}

	@Override
	public void initView() {

		commonAdapter = new CommonAdapter<OrderInfo>(this, dataList, R.layout.item_layout_tihuo_detail_scan) {

			@Override
			public void convert(ViewHolder helper, OrderInfo item) {

				if(item.isSelected()){
					helper.setLayoutResource(R.id.item_layout_tihuo_detail_scan_top, Res.getColor(R.color.blue));
				}else{
					helper.setLayoutResource(R.id.item_layout_tihuo_detail_scan_top, Res.getColor(R.color.transparent));
				}

				helper.setText(R.id.item_layout_tihuo_detail_scan_1, item.getCargoID());
				helper.setText(R.id.item_layout_tihuo_detail_scan_2, item.getCount());
				helper.setText(R.id.item_layout_tihuo_detail_scan_3, item.getWeight());
				helper.setText(R.id.item_layout_tihuo_detail_scan_4, item.getRemark());
			}
		};

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				OrderInfo info = dataList.get(arg2);

				int len = dataList.size();
				for(int i=0; i<len; i++){
					dataList.get(i).setSelected(false);
				}

				info.setSelected(!info.isSelected());

				currPos = arg2;
				commonAdapter.notifyDataSetChanged();
			}
		});

		listView.setAdapter(commonAdapter);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		setTitle("���ɨ��");
		
		orderId = getIntent().getStringExtra("order_id");
		tvOrderId.setText(orderId);
		
		pupScanDao = new PupScanDao();
		initDBData();
	}
	
	/**
	 * �ӱ���ȡ����
	 */
	public void initDBData(){
		
		dataList.clear();
		dataList.addAll(pupScanDao.selectDataById(orderId));
		commonAdapter.notifyDataSetChanged();
		
		edtCargoId.setText("");
		tvTotalCount.setText(dataList.size() + "");
	}
	
	public void toBack(View v){

		dataList.clear();
		finish();
	}

	public void del(View v){

		if(currPos < 0){
			CommandTools.showToast("����ѡ��һ������");
			return;
		}

		final String billcode = dataList.get(currPos).getOrderID();

		final PupHeaderDao pupHeaderDao = new PupHeaderDao();
		if(pupHeaderDao.checkData(billcode) < 1){
			CommandTools.showToast("���ر���û������");
		}else{
			CommandTools.showChooseDialog(this, "�Ƿ�ɾ�����ر�������", new CommandToolsCallback() {

				@Override
				public void callback(int position) {
					// TODO Auto-generated method stub
					if(position == 0){
						pupHeaderDao.deleteById(billcode);

						new PupDetailDao().deleteById(billcode);
						new PupScanDao().deleteById(billcode);
						CommandTools.showToast("ɾ���ɹ�");
					}
				}
			});
		}
	}
	
	public void toTakePhoto(View v){

	}

	public void toPrint(View v){

	}
	
	public void toComplete(View v){

		if(dataList.size() == 0){
			CommandTools.showToast("��ǰû������");
			return;
		}
		
		PresenterUtil.PupUpload(this, dataList, CommandTools.getMIME(this), new ObjectCallback() {
			
			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub
				CommandTools.showToast(data.toString());
			}
		});
	}
	
	public void save(View v){
		
		String cargoId = edtCargoId.getText().toString();
		if(TextUtils.isEmpty(cargoId)){
			CommandTools.showToast("���ϱ��벻��Ϊ��");
			return;
		}
		
		String count = edtCount.getText().toString();
		if(TextUtils.isEmpty(count)){
			CommandTools.showToast("��������Ϊ��");
			return;
		}
		
		String weight = edtWeight.getText().toString();
		if(TextUtils.isEmpty(weight)){
			CommandTools.showToast("��������Ϊ��");
			return;
		}
		
		OrderInfo info = new OrderInfo();
		info.setUserID(MyApplication.mUserInfo.getName());
		info.setOrderID(orderId);
		info.setCargoID("052358DN3004347298");
		info.setCount(count);
		info.setWeight(weight);
		info.setRemark(edtRemark.getText().toString());
		info.setScanTime(CommandTools.getTime());
		
		pupScanDao.addData(info);
		
		initDBData();
	}
}