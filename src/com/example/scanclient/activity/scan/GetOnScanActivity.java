package com.example.scanclient.activity.scan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.db.dao.LoadingDetailDao;
import com.example.scanclient.db.dao.LoadingScanDao;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.API;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
import com.example.scanclient.util.Res;
import com.example.scanclient.util.CommandTools.CommandToolsCallback;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GetOnScanActivity extends BaseActivity {

	@ViewInject(R.id.geton_scan_carplate) TextView tvCarPlate;
	@ViewInject(R.id.geton_scan_orderid) TextView tvOrderId;
	@ViewInject(R.id.geton_scan_cargoid) EditText edtCargoId;
	@ViewInject(R.id.geton_scan_count) EditText edtCount;
	@ViewInject(R.id.geton_scan_remark) EditText edtRemark;

	@ViewInject(R.id.geton_scan_count2) TextView tvCount;
	@ViewInject(R.id.geton_scan_order) TextView tvOrder;

	@ViewInject(R.id.lv_public) ListView listView;
	List<OrderInfo> dataList = new ArrayList<OrderInfo>();
	CommonAdapter<OrderInfo> commonAdapter;

	private int currPos = -1;

	private static final int TAKE_PICTURE = 0x000001;
	private Bitmap mBitmap;

	private LoadingDetailDao loadingDetailDao = new LoadingDetailDao();
	private LoadingScanDao loadingScanDao = new LoadingScanDao();
	private OrderInfo mOrderInfo;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_get_on_scan);
		ViewUtils.inject(this);
		MyApplication.getEventBus().register(this);
	}

	@Override
	public void initView() {

		commonAdapter = new CommonAdapter<OrderInfo>(this, dataList, R.layout.item_layout_geton_scan) {

			@Override
			public void convert(ViewHolder helper, OrderInfo item) {

				if(item.isSelected()){
					helper.setLayoutResource(R.id.item_layout_geton_scan_top, Res.getColor(R.color.blue));
				}else{
					helper.setLayoutResource(R.id.item_layout_geton_scan_top, Res.getColor(R.color.transparent));
				}

				helper.setText(R.id.item_layout_geton_scan_1, item.getCph());
				helper.setText(R.id.item_layout_geton_scan_2, item.getOrderID());
				helper.setText(R.id.item_layout_geton_scan_3, item.getCargoID());
				helper.setText(R.id.item_layout_geton_scan_4, item.getScanTime());
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

		mOrderInfo = (OrderInfo) getIntent().getSerializableExtra("order_info");
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			setTitle("装车扫描");
		}else{
			setTitle("卸车扫描");
		}

		tvCarPlate.setText(mOrderInfo.getCph());
		tvOrderId.setText(mOrderInfo.getOrderID());

		dataList.addAll(loadingDetailDao.selectAllData(mOrderInfo));
		int len = dataList.size();
		if(dataList.size() == 0){

			String actionName = "";
			if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
				actionName = API.LoadingQueryOrderDetail;
			}else{
				actionName = API.UnloadingQueryOrderDetail;
			}

			PresenterUtil.LoadingQueryOrderDetail(this, actionName, mOrderInfo, new ObjectCallback() {

				@Override
				public void callback(boolean success, String message, Object data) {
					// TODO Auto-generated method stub

					dataList.clear();
					dataList.addAll((Collection<? extends OrderInfo>) data);
					commonAdapter.notifyDataSetChanged();
					tvCount.setText(dataList.size() + "");
					return;	
				}
			});
		}

		commonAdapter.notifyDataSetChanged();
		tvCount.setText(len + "");
	}

	public void onEventMainThread(Object event) {  

		String billcode = event.toString();  
		edtCargoId.setText(billcode);
		save(null);
	}  

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {

			mBitmap = (Bitmap) data.getExtras().get("data");
			mBitmap = CommandTools.resetBitmap(mBitmap);
		}
	}

	public void toBack(View v){

		dataList.clear();
		finish();
	}

	public void toTakePhoto(View v){

		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	public void toCompare(View v){


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

					loadingScanDao.deleteById(mOrderInfo);
					dataList.remove(currPos);
					commonAdapter.notifyDataSetChanged();
					currPos = -1;
					CommandTools.showToast("删除成功");
				}
			}
		});
	}

	public void toComplete(View v){

		if(dataList.size() == 0){
			CommandTools.showToast("当前没有数据");
			return;
		}

		String actionName = "";
		if(mOrderInfo.getScanType().equals(Constant.SCANTYPE_GETON)){
			actionName = API.LoadingUpload;
		}else{
			actionName = API.UnloadingUpload;
		}

		PresenterUtil.LoadingUpload(this, actionName, dataList, mOrderInfo, new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub

				int len = dataList.size();
				for(int i=0; i<len; i++){

					OrderInfo info = dataList.get(i);
					info.setFlag("1");//上传成功后修改状态
					loadingScanDao.updateData(info);
				}

				dataList.clear();
				commonAdapter.notifyDataSetChanged();
				edtCargoId.setText("");
				tvCount.setText(dataList.size() + "");

				edtRemark.setText("");
			}
		});
	}

	public void save(View v){

		String cargoId = edtCargoId.getText().toString();
		if(TextUtils.isEmpty(cargoId)){
			CommandTools.showToast("物料编码不能为空");
			return;
		}

		String count = edtCount.getText().toString();
		if(TextUtils.isEmpty(count)){
			CommandTools.showToast("数量不能为空");
			return;
		}
		
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCph(mOrderInfo.getCph());
		orderInfo.setOrderID(mOrderInfo.getOrderID());
		orderInfo.setCargoID(cargoId);
		orderInfo.setCount(count);
		orderInfo.setRemark(edtRemark.getText().toString());
		orderInfo.setScanTime(CommandTools.getTime());

		loadingScanDao.addData(orderInfo);
		dataList.add(orderInfo);
		commonAdapter.notifyDataSetChanged();

		tvCount.setText(dataList.size() + "");
		edtCargoId.setText("");
	}

	public void onStop(){
		super.onStop();

		MyApplication.getEventBus().unregister(this);
	}
}
