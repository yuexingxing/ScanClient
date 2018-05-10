package com.example.scanclient.fragment;

import com.example.scanclient.LoginActivity;
import com.example.scanclient.R;
import com.example.scanclient.R.layout;
import com.example.scanclient.activity.scan.TiHuoDetailActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingFragment extends Fragment implements OnClickListener{

	private View view;
	@ViewInject(R.id.setting_fragment_zhuxiao) Button btnZhuXiao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_setting_fragment, container, false);

		ViewUtils.inject(this, view);
		setOnClick();
		return view;
	}

	public void setOnClick(){

		btnZhuXiao.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == btnZhuXiao.getId()){

			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
	}
}
