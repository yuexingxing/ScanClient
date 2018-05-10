package com.example.scanclient.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import com.example.scanclient.db.dao.PupDetailDao;
import com.example.scanclient.db.dao.PupHeaderDao;
import com.example.scanclient.db.dao.PupScanDao;
import com.example.scanclient.info.BillInfo;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.info.PupScan;
import com.example.scanclient.presenter.PresenterUtil;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.CommandTools.CommandToolsCallback;
import com.example.scanclient.util.OkHttpUtil.ObjectCallback;
import com.example.scanclient.util.Res;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 订单查询
 * @author yxx
 *
 */
public class OrderInqueryActivity extends BaseActivity {

	@ViewInject(R.id.lv_public) ListView listView;
	List<OrderInfo> dataList = new ArrayList<OrderInfo>();
	CommonAdapter<OrderInfo> commonAdapter;

	@ViewInject(R.id.order_inquery_billcode) EditText edtBillcode;
	@ViewInject(R.id.order_inquery_time) TextView tvTime;
	@ViewInject(R.id.order_inquery_count) TextView tvCount;
	private int mYear, mMonth, mDay;

	private int currPos = -1;

	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		setContentViewId(R.layout.activity_order_inquery);
		ViewUtils.inject(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("订单查询");

		commonAdapter = new CommonAdapter<OrderInfo>(this, dataList, R.layout.item_layout_inquery_order) {

			@Override
			public void convert(ViewHolder helper, OrderInfo item) {

				if(item.isSelected()){
					helper.setLayoutResource(R.id.item_layout_orderquery_top, Res.getColor(R.color.blue));
				}else{
					helper.setLayoutResource(R.id.item_layout_orderquery_top, Res.getColor(R.color.transparent));
				}

				helper.setText(R.id.item_layout_orderquery_1, item.getOrderID());
				helper.setText(R.id.item_layout_orderquery_2, item.getOrderDate());
				helper.setText(R.id.item_layout_orderquery_3, item.getStatus());
				helper.setText(R.id.item_layout_orderquery_4, item.getCrtBillNo());
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

		String billcode = edtBillcode.getText().toString();
		if(TextUtils.isEmpty(billcode)){
			CommandTools.showToast("请输入订单号");
			return;
		}

		String strTime = mYear + "-" + mMonth + "-" + mDay;
		PresenterUtil.PupQueryOrderHeader(this, billcode, strTime, MyApplication.mUserInfo.getName(), CommandTools.getMIME(this),  new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub

				dataList.clear();
				dataList.addAll((Collection<? extends OrderInfo>) data);
				commonAdapter.notifyDataSetChanged();
			}
		});
	}

	public void del(View v){

		if(currPos < 0){
			CommandTools.showToast("请先选择一条数据");
			return;
		}

		final String billcode = dataList.get(currPos).getOrderID();

		final PupHeaderDao pupHeaderDao = new PupHeaderDao();
		if(pupHeaderDao.checkData(billcode) < 1){
			CommandTools.showToast("本地表中没有数据");
		}else{
			CommandTools.showChooseDialog(this, "是否删除本地表中数据", new CommandToolsCallback() {

				@Override
				public void callback(int position) {
					// TODO Auto-generated method stub
					if(position == 0){
						pupHeaderDao.deleteById(billcode);

						new PupDetailDao().deleteById(billcode);
						new PupScanDao().deleteById(billcode);
						CommandTools.showToast("删除成功");
					}
				}
			});
		}
	}

	public void toBack(View v){

		dataList.clear();
		finish();
	}

	public void toDetail(View v){

		if(currPos < 0){
			CommandTools.showToast("请先选择一条数据");
			return;
		}

		final String billcode = dataList.get(currPos).getOrderID();
		PresenterUtil.PodQueryOrderDetail(this, billcode, MyApplication.mUserInfo.getName(), CommandTools.getMIME(this),  new ObjectCallback() {

			@Override
			public void callback(boolean success, String message, Object data) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(OrderInqueryActivity.this, TiHuoDetailActivity.class);
				intent.putExtra("order_id", billcode);
				startActivity(intent);
			}
		});
	}
}