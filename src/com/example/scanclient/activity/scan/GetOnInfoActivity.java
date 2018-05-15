package com.example.scanclient.activity.scan;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.db.dao.LoadingDetailDao;
import com.example.scanclient.db.dao.LoadingHeaderDao;
import com.example.scanclient.db.dao.LoadingScanDao;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.API;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.Res;
import com.example.scanclient.util.CommandTools.CommandToolsCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GetOnInfoActivity extends BaseActivity {

	@ViewInject(R.id.geton_info_carplate) TextView tvCarPlate;
	@ViewInject(R.id.geton_info_orderid) EditText edtOrderId;
	@ViewInject(R.id.geton_info_count) TextView tvCount;

	@ViewInject(R.id.lv_public) ListView listView;
	List<OrderInfo> dataList = new ArrayList<OrderInfo>();
	CommonAdapter<OrderInfo> commonAdapter;

	private int currPos = -1;
	private OrderInfo mOrderInfo;
	private LoadingHeaderDao loadingHeaderDao = new LoadingHeaderDao();
	private LoadingScanDao loadingScanDao = new LoadingScanDao();
	private LoadingDetailDao loadingDetailDao = new LoadingDetailDao();
	
	private String actionName;//11203609

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_get_on_info);
		ViewUtils.inject(this);
		MyApplication.getEventBus().register(this);
	}

	@Override
	public void initView() {

		commonAdapter = new CommonAdapter<OrderInfo>(this, dataList, R.layout.item_layout_geton_info) {

			@Override
			public void convert(ViewHolder helper, OrderInfo item) {

				if(item.isSelected()){
					helper.setLayoutResource(R.id.item_layout_geton_info_top, Res.getColor(R.color.blue));
				}else{
					helper.setLayoutResource(R.id.item_layout_geton_info_top, Res.getColor(R.color.transparent));
				}

				helper.setText(R.id.item_layout_geton_info_1, item.getOrderID());
				helper.setText(R.id.item_layout_geton_info_2, item.getOrderDate());
				helper.setText(R.id.item_layout_geton_info_3, item.getStatus());
			}
		};

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				OrderInfo info = dataList.get(arg2);
				mOrderInfo.setOrderID(info.getOrderID());

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
		
		mOrderInfo = (OrderInfo) getIntent().getSerializableExtra("order_info");
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			setTitle("上车订单信息");
		}else{
			setTitle("下车订单信息");
		}
	
		tvCarPlate.setText(mOrderInfo.getCph());

		dataList.addAll(loadingHeaderDao.selectDataById(mOrderInfo));
		commonAdapter.notifyDataSetChanged();

		tvCount.setText(dataList.size() + "");
	}
	
	public void onEventMainThread(Object event) {  

		String billcode = event.toString();  
		edtOrderId.setText(billcode);
		save(null);
	}  

	public void toBack(View v){

		dataList.clear();
		finish();
	}

	public void del(View v){

		if(currPos < 0){
			CommandTools.showToast("请先选择一条数据");
			return;
		}

		CommandTools.showChooseDialog(this, "是否删除本地表中数据", new CommandToolsCallback() {

			@Override
			public void callback(int position) {
				// TODO Auto-generated method stub
				if(position == 0){
					
					loadingHeaderDao.deleteById(mOrderInfo);

					dataList.remove(currPos);
					commonAdapter.notifyDataSetChanged();
					CommandTools.showToast("删除成功");

					currPos = -1;
					tvCount.setText(dataList.size() + "");
				}
			}
		});

	}

	public void save(View v){

		final String orderId = edtOrderId.getText().toString();
		if(TextUtils.isEmpty(orderId)){
			CommandTools.showToast("订单号不能为空");
			return;
		}

		mOrderInfo.setOrderID(orderId);
		mOrderInfo.setScanTime(CommandTools.getTime());
		
		loadingHeaderDao.deleteById(mOrderInfo);//插入表之前先执行一次删除
		
		int len = dataList.size();
		for(int i=0; i<len; i++){

			OrderInfo infoTemp = dataList.get(i);
			if(infoTemp.getOrderID().equals(orderId)){

				final int iTemp = i;
				CommandTools.showChooseDialog(this, "订单号已经存在，是否要删除？选择[是]将同时删除该订单号的所有扫描数据！", new CommandToolsCallback() {

					@Override
					public void callback(int position) {
						// TODO Auto-generated method stub
						if(position == 0){

							dataList.remove(iTemp);
							commonAdapter.notifyDataSetChanged();
							
							loadingHeaderDao.deleteById(mOrderInfo);
							loadingScanDao.deleteById(mOrderInfo);
							loadingDetailDao.deleteById(mOrderInfo);
							
							CommandTools.showToast("删除成功");
							getHeaderData();
							return;
						}
					}
				});
			}
		}

		loadingHeaderDao.addData(mOrderInfo);
		dataList.add(mOrderInfo);
		commonAdapter.notifyDataSetChanged();

		currPos = -1;
		tvCount.setText(dataList.size() + "");
		edtOrderId.setText("");
	}

	public void toNext(View v){
		
		if(TextUtils.isEmpty(mOrderInfo.getOrderID())){
			CommandTools.showToast("请选择一条数据");
			return;
		}

		Intent intent = new Intent(this, GetOnScanActivity.class);
		intent.putExtra("order_info", mOrderInfo);
		startActivity(intent);
	}

	public void getHeaderData(){
		
		String actionName = null;
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			actionName = API.LoadingQueryOrderHeader;
		}else{
			actionName = API.UnloadingQueryOrderHeader;
		}
		
		PresenterUtil.LoadingQueryOrderHeader(this, actionName, mOrderInfo, new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub

				OrderInfo info = (OrderInfo) data;
				dataList.add(info);
				commonAdapter.notifyDataSetChanged();
				
				tvCount.setText(dataList.size() + "");
			}
		});
	}
	
	public void onStop(){
		super.onStop();

		MyApplication.getEventBus().unregister(this);
	}
}
