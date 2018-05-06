package com.example.scanclient.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scanclient.MyApplication;
import com.example.scanclient.R;
import com.example.scanclient.adapter.MFragmentPagerAdapter;
import com.example.scanclient.fragment.InqueryFragment;
import com.example.scanclient.fragment.ScanFragment;
import com.example.scanclient.fragment.SettingFragment;
import com.example.scanclient.util.Res;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ���˵�
 * @author yxx
 *
 */
public class MainMenuActivity extends FragmentActivity {

	public Context mContext;

	private ViewPager mViewPager;	//ʵ��Tab����Ч��
	private int currIndex = 0;//��ǰҳ�����
	private ArrayList<Fragment> fragmentArrayList;//���Fragment
	private FragmentManager fragmentManager;	//����Fragment

	//����ͼƬƫ����
	private int position_one;
	private int position_two;
	private ImageView imgCursor;
	
	@ViewInject(R.id.layout_ordermenu_pager_1) LinearLayout layoutScan;
	@ViewInject(R.id.layout_ordermenu_pager_2) LinearLayout layoutInquery;
	@ViewInject(R.id.layout_ordermenu_pager_3) LinearLayout layoutSetting;

	@ViewInject(R.id.tv_order_pager_wait) TextView tvWait;
	@ViewInject(R.id.tv_order_pager_sending) TextView tvDis;
	@ViewInject(R.id.tv_order_pager_signed) TextView tvSigned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		ViewUtils.inject(this);

		InitImageView();
		InitFragment();
		InitViewPager();
		
		setAnimation(currIndex, 0);
		setMenuColor(currIndex);
		
		layoutScan.setOnClickListener(new MyOnClickListener(0));
		layoutInquery.setOnClickListener(new MyOnClickListener(1));
		layoutSetting.setOnClickListener(new MyOnClickListener(2));
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {

		mContext = MainMenuActivity.this;
		return super.onCreateView(name, context, attrs);
	}

	/**
	 * ��ʼ������
	 */
	private void InitImageView() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// ��ȡ�ֱ��ʿ��
		int screenW = dm.widthPixels;
		int bmpW = (screenW/3);

		//����ͼƬƫ������ֵ
		position_one = (int) (screenW / 3.0);
		position_two = position_one * 2;

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_order_menu_bottom_line);
		imgCursor = new ImageView(MainMenuActivity.this);
		imgCursor.setBackgroundColor(Res.getColor(R.color.main_color));

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(bmpW, 5);
		layoutParams.topMargin = 0;
		layoutParams.leftMargin = 0;
		layoutParams.rightMargin = bmpW;
		layoutParams.bottomMargin = 0;

		layout.addView(imgCursor, layoutParams);
	}

	/**
	 * ��ʼ��ҳ��������
	 */
	private void InitViewPager() {

		mViewPager = (ViewPager) findViewById(R.id.vPager);
		mViewPager.setAdapter(new MFragmentPagerAdapter(fragmentManager, fragmentArrayList));

		//��ViewPager����3��ҳ��
		mViewPager.setOffscreenPageLimit(3);

		//����Ĭ�ϴ򿪵�һҳ
		mViewPager.setCurrentItem(0);

		//����viewpagerҳ�滬�������¼�
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ��ʼ��Fragment������ӵ�ArrayList��
	 */
	private void InitFragment(){

		fragmentArrayList = new ArrayList<Fragment>();
		fragmentArrayList.add(new ScanFragment());
		fragmentArrayList.add(new InqueryFragment());
		fragmentArrayList.add(new SettingFragment());

		fragmentManager = getSupportFragmentManager();
	}

	/**
	 * ͷ��������
	 * @author weizhi
	 * @version 1.0
	 */
	public class MyOnClickListener implements View.OnClickListener{

		private int index = 0 ;
		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			
			mViewPager.setCurrentItem(index);
		}
	}

	/**
	 * ҳ���л�����
	 * @author weizhi
	 * @version 1.0
	 */
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

		@Override
		public void onPageSelected(int position) {

			setMenuColor(position);
			setAnimation(position, 300);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	/**
	 * ���ö���
	 * @param position
	 * @param durationMillis ��������ʱ�����
	 */
	private void setAnimation(int position, long durationMillis){

		Animation animation = null ;

		switch (currIndex){
		case 0:
			if(position == 1){
				animation = new TranslateAnimation(0, position_one, 0, 0);
			}else if(position == 2){//��ҳ��1��תת��ҳ��3
				animation = new TranslateAnimation(0, position_two, 0, 0);
			}
			break;
		case 1:
			if(position == 0){
				animation = new TranslateAnimation(position_one, 0, 0, 0);
			}else if(position == 2){//��ҳ��1��תת��ҳ��3
				animation = new TranslateAnimation(position_one, position_two, 0, 0);
			}
			break;
		case 2:
			if(position == 0){
				animation = new TranslateAnimation(position_two, 0, 0, 0);
			}else if(position == 1){//��ҳ��1��תת��ҳ��3
				animation = new TranslateAnimation(position_two, position_one, 0, 0);
			}
			break;
		default:

			break;
		}

		if(animation != null){
			animation.setFillAfter(true);// true:ͼƬͣ�ڶ�������λ��
			animation.setDuration(durationMillis);
			imgCursor.startAnimation(animation);
		}

		currIndex = position;
	}

	/**
	 * ���¶����˵�����ɫ
	 * @param pos
	 */
	private void setMenuColor(int pos){

		tvWait.setTextColor(Res.getColor(R.color.black));
		tvDis.setTextColor(Res.getColor(R.color.black));
		tvSigned.setTextColor(Res.getColor(R.color.black));

		if(pos == 0){
			tvWait.setTextColor(Res.getColor(R.color.main_color));
		}else if(pos == 1){
			tvDis.setTextColor(Res.getColor(R.color.main_color));
		}else if(pos == 2){
			tvSigned.setTextColor(Res.getColor(R.color.main_color));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // ��ȡ back��

			exit();
		}
		return false;
	}
	
	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("��ʾ")
		.setMessage("ȷ���˳�����")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				MyApplication.finishAllActivities();
				finish();
			}
		}).setNegativeButton("ȡ��", null).show();
	}


}
