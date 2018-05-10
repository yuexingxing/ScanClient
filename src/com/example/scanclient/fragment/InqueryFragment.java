package com.example.scanclient.fragment;

import com.example.scanclient.R;
import com.example.scanclient.activity.inquery.OrderInqueryActivity;
import com.example.scanclient.activity.inquery.StockInqueryActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ≤È—Ø
 * @author yxx
 *
 */
public class InqueryFragment extends Fragment implements OnClickListener  {

	private View view;
	@ViewInject(R.id.inquery_fragment_order) Button btnOrder;
	@ViewInject(R.id.inquery_fragment_kucun) Button btnKuCun;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.activity_inquery_fragment, container, false);

		ViewUtils.inject(this, view);
		setOnClick();
		return view;
	}

	public void setOnClick(){

		btnOrder.setOnClickListener(this);
		btnKuCun.setOnClickListener(this);
	}

	@Override
	public void onResume(){
		super.onResume();

	}

	@Override
	public void onClick(View arg0) {

		if(arg0.getId() == btnOrder.getId()){

			Intent intent = new Intent(InqueryFragment.this.getActivity(), OrderInqueryActivity.class);
			startActivity(intent);
		}else if(arg0.getId() == btnKuCun.getId()){

			Intent intent = new Intent(InqueryFragment.this.getActivity(), StockInqueryActivity.class);
			startActivity(intent);
		}
	}
}
