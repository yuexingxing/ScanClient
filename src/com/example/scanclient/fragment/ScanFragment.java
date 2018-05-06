package com.example.scanclient.fragment;

import com.example.scanclient.R;
import com.example.scanclient.util.CommandTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * …®√Ë
 * @author yxx
 *
 */
public class ScanFragment extends Fragment implements OnClickListener {

	private View view;
	@ViewInject(R.id.scan_fragment_tihuo) Button btnTiHuo;
	@ViewInject(R.id.scan_fragment_shangche) Button btnShangChe;
	@ViewInject(R.id.scan_fragment_xiache) Button btnXiaChe;
	@ViewInject(R.id.scan_fragment_sign) Button btnSign;
	@ViewInject(R.id.scan_fragment_chuku) Button btnChuKu;
	@ViewInject(R.id.scan_fragment_chongda) Button btnChongDa;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.activity_scan_fragment, container, false);

		ViewUtils.inject(this, view);
		setOnClick();
		return view;
	}

	public void setOnClick(){

		btnTiHuo.setOnClickListener(this);
		btnShangChe.setOnClickListener(this);
		btnXiaChe.setOnClickListener(this);
		btnSign.setOnClickListener(this);
		btnChuKu.setOnClickListener(this);
		btnChongDa.setOnClickListener(this);
	}

	@Override
	public void onResume(){
		super.onResume();

	}

	@Override
	public void onClick(View arg0) {

		if(arg0.getId() == btnTiHuo.getId()){
			CommandTools.showToast("btnTiHuo");
		}else if(arg0.getId() == btnShangChe.getId()){

		}else if(arg0.getId() == btnXiaChe.getId()){

		}else if(arg0.getId() == btnSign.getId()){

		}else if(arg0.getId() == btnChuKu.getId()){

		}else if(arg0.getId() == btnChongDa.getId()){

		}
	}
}
