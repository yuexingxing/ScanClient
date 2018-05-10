package com.example.scanclient.activity.scan;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.R;
import com.example.scanclient.R.color;
import com.example.scanclient.R.id;
import com.example.scanclient.R.layout;
import com.example.scanclient.activity.BaseActivity;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.db.dao.PupDetailDao;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Res;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 提货明细界面
 * @author xugang
 *
 */
public class TiHuoDetailActivity extends BaseActivity {
	
	
	@ViewInject(R.id.tihuo_detail_orderid) TextView tvOrderId;

	@ViewInject(R.id.lv_public) ListView listView;
	List<OrderInfo> dataList = new ArrayList<OrderInfo>();
	CommonAdapter<OrderInfo> commonAdapter;
	
	private PupDetailDao pupDetailDao;
	private String orderId;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_ti_huo_detail);
		ViewUtils.inject(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
		commonAdapter = new CommonAdapter<OrderInfo>(this, dataList, R.layout.item_layout_tihuo_detail) {

			@Override
			public void convert(ViewHolder helper, OrderInfo item) {

				if(item.isSelected()){
					helper.setLayoutResource(R.id.item_layout_tihuo_detail_top, Res.getColor(R.color.blue));
				}else{
					helper.setLayoutResource(R.id.item_layout_tihuo_detail_top, Res.getColor(R.color.transparent));
				}

				helper.setText(R.id.item_layout_tihuo_detail_1, item.getCargoID());
				helper.setText(R.id.item_layout_tihuo_detail_2, item.getCargoName());
				helper.setText(R.id.item_layout_tihuo_detail_3, item.getModel());
				helper.setText(R.id.item_layout_tihuo_detail_4, item.getBatchNo());
				helper.setText(R.id.item_layout_tihuo_detail_5, item.getCount());
				helper.setText(R.id.item_layout_tihuo_detail_6, item.getWeight());
				helper.setText(R.id.item_layout_tihuo_detail_7, item.getRemark());
				
//				CargoID,CargoName,Model,BatchNo,Count,Weight,Remark 
			}
		};
		listView.setAdapter(commonAdapter);
		
	}

	@Override
	public void initData() {
		
		setTitle("提货明细");
		pupDetailDao = new PupDetailDao();
		
		orderId = getIntent().getStringExtra("order_id");
		tvOrderId.setText(orderId);
		
		dataList.addAll(pupDetailDao.selectDataById(orderId));
		commonAdapter.notifyDataSetChanged();
	}
	
	public void toBack(View v){

		dataList.clear();
		finish();
	}

	public void tiHuo(View v){
		
		Intent intent = new Intent(TiHuoDetailActivity.this, TiHuoDetailScanActivity.class);
		intent.putExtra("order_id", orderId);
		startActivity(intent);
	}

}
