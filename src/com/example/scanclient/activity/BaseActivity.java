package com.example.scanclient.activity;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/** 
 * ����
 * 
 * @author yxx
 *
 * @date 2017-11-27 ����5:09:41
 * 
 */
public abstract class BaseActivity extends Activity {

	private LinearLayout layoutTopBar;
	private LinearLayout layoutBody;
	protected View contentView;

	private TextView tvLeft;
	private TextView tvTitle;
	private TextView tvRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);

		layoutTopBar = (LinearLayout) findViewById(R.id.durian_head_layout);
		layoutBody = (LinearLayout) findViewById(R.id.baseAct_body);
		MyApplication.getInstance().addActivity(this);

		onBaseCreate(savedInstanceState);
		findViewById();
		initView();
		initData();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
	}

	private void findViewById() {

		tvLeft = (TextView) findViewById(R.id.tv_item_left);
		tvTitle = (TextView) findViewById(R.id.tv_item_center);
		tvRight = (TextView) findViewById(R.id.tv_item_right);
	}

	public void setContentViewId(int layoutId) {

		contentView = getLayoutInflater().inflate(layoutId, null);
		if (layoutBody.getChildCount() > 0) {
			layoutBody.removeAllViews();
		}

		if (contentView != null) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			layoutBody.addView(contentView, params);
		}
	}

	/**
	 * ��������
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void onBaseCreate(Bundle savedInstanceState);

	/**
	 * ��ʼ���ؼ�
	 */
	public abstract void initView();

	/**
	 * ��ʼ������
	 */
	public abstract void initData();

	public void hidenTopBar(){

		layoutTopBar.setVisibility(View.GONE);
	}

	/**
	 * ���ñ���
	 * 
	 * @param mReturn
	 */
	public void setTitle(String mTitle) {
		tvTitle.setText(mTitle);
	}

	/**
	 * �����Ҳ����(��Ҫ������ʾ)
	 * @param mTitle
	 */
	public void setRightTitle(String mTitle){
		tvRight.setText(mTitle);
	}

	/**
	 * ������෵�ؼ�
	 */
	public void hidenLeftMenu(){
		tvLeft.setVisibility(View.INVISIBLE);
	}

	/**
	 * ������෵�ؼ�
	 */
	public void hidenRightMenu(){
		tvRight.setVisibility(View.INVISIBLE);
	}

	public void back(View v){
		finish();
	}

	/**
	 * �Ҳఴť����¼�
	 * ����ֱ�Ӹ�д�÷�������
	 * @param v
	 */
	public void clickRight(View v){

	}
}
