package com.example.scanclient.fragment;

import com.example.scanclient.R;
import com.example.scanclient.activity.inquery.OrderInqueryActivity;
import com.example.scanclient.activity.scan.GetOffActivity;
import com.example.scanclient.activity.scan.GetOnActivity;
import com.example.scanclient.activity.scan.OutStorageActivity;
import com.example.scanclient.activity.scan.ReStrokeActivity;
import com.example.scanclient.activity.scan.ScanCompareActivity;
import com.example.scanclient.activity.scan.SignActivity;
import com.example.scanclient.activity.scan.TiHuoDetailActivity;
import com.example.scanclient.activity.scan.TiHuoDetailScanActivity;
import com.example.scanclient.util.CommandTools;
import com.example.scanclient.util.Constant;
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
 * ɨ��
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
	@ViewInject(R.id.scan_fragment_check) Button btnCheck;

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
		btnCheck.setOnClickListener(this);
	}

	@Override
	public void onResume(){
		super.onResume();

	}

	@Override
	public void onClick(View arg0) {

		if(arg0.getId() == btnTiHuo.getId()){
			
			Intent intent = new Intent(getActivity(), OrderInqueryActivity.class);
			startActivity(intent);
		}else if(arg0.getId() == btnShangChe.getId()){

			Intent intent = new Intent(getActivity(), GetOnActivity.class);
			intent.putExtra("scan_type", Constant.SCANTYPE_GETON);
			startActivity(intent);
		}else if(arg0.getId() == btnXiaChe.getId()){

			Intent intent = new Intent(getActivity(), GetOnActivity.class);
			intent.putExtra("scan_type", Constant.SCANTYPE_GETOFF);
			startActivity(intent);
		}else if(arg0.getId() == btnSign.getId()){

			CommandTools.showToast("������");
//			Intent intent = new Intent(getActivity(), SignActivity.class);
//			startActivity(intent);
		}else if(arg0.getId() == btnChuKu.getId()){

			CommandTools.showToast("������");
//			Intent intent = new Intent(getActivity(), OutStorageActivity.class);
//			startActivity(intent);
		}else if(arg0.getId() == btnChongDa.getId()){

			CommandTools.showToast("������");
//			Intent intent = new Intent(getActivity(), ReStrokeActivity.class);
//			startActivity(intent);
		}else if(arg0.getId() == btnCheck.getId()){

			Intent intent = new Intent(getActivity(), ScanCompareActivity.class);
			startActivity(intent);
		}
	}
}
