package com.example.scanclient.fragment;

import com.example.scanclient.R;
import com.example.scanclient.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.activity_setting_fragment, container, false);

		return view;
	}

}
