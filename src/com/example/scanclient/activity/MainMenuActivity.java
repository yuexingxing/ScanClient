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
 * 主菜单
 * @author yxx
 *
 */
public class MainMenuActivity extends FragmentActivity {

	public Context mContext;

	private ViewPager mViewPager;	//实现Tab滑动效果
	private int currIndex = 0;//当前页卡编号
	private ArrayList<Fragment> fragmentArrayList;//存放Fragment
	private FragmentManager fragmentManager;	//管理Fragment

	//动画图片偏移量
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
	 * 初始化动画
	 */
	private void InitImageView() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 获取分辨率宽度
		int screenW = dm.widthPixels;
		int bmpW = (screenW/3);

		//动画图片偏移量赋值
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
	 * 初始化页卡内容区
	 */
	private void InitViewPager() {

		mViewPager = (ViewPager) findViewById(R.id.vPager);
		mViewPager.setAdapter(new MFragmentPagerAdapter(fragmentManager, fragmentArrayList));

		//让ViewPager缓存3个页面
		mViewPager.setOffscreenPageLimit(3);

		//设置默认打开第一页
		mViewPager.setCurrentItem(0);

		//设置viewpager页面滑动监听事件
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化Fragment，并添加到ArrayList中
	 */
	private void InitFragment(){

		fragmentArrayList = new ArrayList<Fragment>();
		fragmentArrayList.add(new ScanFragment());
		fragmentArrayList.add(new InqueryFragment());
		fragmentArrayList.add(new SettingFragment());

		fragmentManager = getSupportFragmentManager();
	}

	/**
	 * 头标点击监听
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
	 * 页卡切换监听
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
	 * 设置动画
	 * @param position
	 * @param durationMillis 动画持续时间毫秒
	 */
	private void setAnimation(int position, long durationMillis){

		Animation animation = null ;

		switch (currIndex){
		case 0:
			if(position == 1){
				animation = new TranslateAnimation(0, position_one, 0, 0);
			}else if(position == 2){//从页卡1跳转转到页卡3
				animation = new TranslateAnimation(0, position_two, 0, 0);
			}
			break;
		case 1:
			if(position == 0){
				animation = new TranslateAnimation(position_one, 0, 0, 0);
			}else if(position == 2){//从页卡1跳转转到页卡3
				animation = new TranslateAnimation(position_one, position_two, 0, 0);
			}
			break;
		case 2:
			if(position == 0){
				animation = new TranslateAnimation(position_two, 0, 0, 0);
			}else if(position == 1){//从页卡1跳转转到页卡3
				animation = new TranslateAnimation(position_two, position_one, 0, 0);
			}
			break;
		default:

			break;
		}

		if(animation != null){
			animation.setFillAfter(true);// true:图片停在动画结束位置
			animation.setDuration(durationMillis);
			imgCursor.startAnimation(animation);
		}

		currIndex = position;
	}

	/**
	 * 更新顶部菜单栏颜色
	 * @param pos
	 */
	private void setMenuColor(int pos){

		tvWait.setTextColor(Res.getColor(R.color.gray));
		tvDis.setTextColor(Res.getColor(R.color.gray));
		tvSigned.setTextColor(Res.getColor(R.color.gray));

		if(pos == 0){
			tvWait.setTextColor(Res.getColor(R.color.black));
		}else if(pos == 1){
			tvDis.setTextColor(Res.getColor(R.color.black));
		}else if(pos == 2){
			tvSigned.setTextColor(Res.getColor(R.color.black));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // 获取 back键

			exit();
		}
		return false;
	}
	
	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage("确认退出程序？")
		.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				MyApplication.finishAllActivities();
				finish();
			}
		}).setNegativeButton("取消", null).show();
	}


}
