package com.example.scanclient.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.scanclient.R;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.info.BillInfo;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 订单查询
 * @author yxx
 *
 */
public class OrderInqueryActivity extends BaseActivity {
	
	@ViewInject(R.id.lv_public) ListView listView;
	List<BillInfo> dataList = new ArrayList<BillInfo>();
	CommonAdapter<BillInfo> commonAdapter;

	@ViewInject(R.id.order_inquery_time) TextView tvTime;
	@ViewInject(R.id.order_inquery_count) TextView tvCount;
	private int mYear, mMonth, mDay;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_order_inquery);
		ViewUtils.inject(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("订单查询");
		
		commonAdapter = new CommonAdapter<BillInfo>(this, dataList, R.layout.item_layout_inquery_order) {

			@Override
			public void convert(ViewHolder helper, BillInfo item) {

				helper.setText(R.id.item_layout_orderquery_1, item.getBillcode());
				helper.setText(R.id.item_layout_orderquery_2, item.getScanTime());
				helper.setText(R.id.item_layout_orderquery_3, item.getStatus());
				helper.setText(R.id.item_layout_orderquery_4, item.getCusBillcode());
			}
		};
		listView.setAdapter(commonAdapter);
		
		Calendar ca = Calendar.getInstance();
		mYear = ca.get(Calendar.YEAR);
		mMonth = ca.get(Calendar.MONTH) + 1;
		mDay = ca.get(Calendar.DAY_OF_MONTH);
		
		tvTime.setText(mYear + "-" + mMonth + "-" + mDay);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	public void selTime(View v){

		new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

				mYear = arg1;
				mMonth = arg2 + 1;
				mDay = arg3;
				
				tvTime.setText(mYear + "-" + mMonth + "-" + mDay);
			}
		}, mYear, mMonth - 1, mDay).show();
	}

	public void inquery(View v){

		PresenterUtil.PupQueryOrderHeader(this, "11203609", mYear + "-" + mMonth + "-" + mDay, "admin", "122222222222312",  new ObjectCallback() {
			
			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub
				CommandTools.showToast(data.toString());
			}
		});
	}

	public void del(View v){


	}

}
