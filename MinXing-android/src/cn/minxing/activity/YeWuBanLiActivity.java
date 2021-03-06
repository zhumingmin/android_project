package cn.minxing.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.minxing.fragment.MinXingCunFragment;
import cn.minxing.fragment.NewNongYeZhuShouFragment;
import cn.minxing.fragment.NongYeZhuShouFragment;
import cn.minxing.fragment.WoDeFragment;
import cn.minxing.fragment.YeWuBanLiFragment;

import com.zhumingmin.vmsofminxing.R;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class YeWuBanLiActivity extends FragmentActivity implements
		View.OnClickListener {

	private ViewPager Mviewpager;
	private LinearLayout layoutone, layouttwo, layoutthree, layoutfour;
	private ImageView i01, i02, i03, i04;
	// static String biaoti, laiyuan, yuedu, neirong;
	private FragmentPagerAdapter adapter;
	private List<Fragment> mLists = new ArrayList<Fragment>();
	private TextView tv_minxingcun, tv_yewubanli, tv_nongyezhushou, tv_wode;
	private Fragment fr01, fr02, fr03, fr04;
	private static final String TAG = "YeWuBanLiActivity";
	int currentItem;
	static String isReqing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewpager);
		initView();
		initEvent();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		layoutfour.setOnClickListener(this);
		layoutone.setOnClickListener(this);
		layoutthree.setOnClickListener(this);
		layouttwo.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		Mviewpager = (ViewPager) findViewById(R.id.id_mainviewpager);
		Mviewpager.setOffscreenPageLimit(1);

		layoutone = getView(R.id.id_mainbuttom_one);
		layoutfour = getView(R.id.id_mainbuttom_four);
		layoutthree = getView(R.id.id_mainbuttom_three);
		layouttwo = getView(R.id.id_mainbuttom_two);

		i01 = getView(R.id.id_mainbutton_oneim);
		i02 = getView(R.id.id_mainbutton_twoim);
		i03 = getView(R.id.id_mainbutton_threeim);
		i04 = getView(R.id.id_mainbutton_fourim);
		tv_minxingcun = getView(R.id.tv_minxingcun);
		tv_yewubanli = getView(R.id.tv_yewubanli);
		tv_nongyezhushou = getView(R.id.tv_nongyezhushou);
		tv_wode = getView(R.id.tv_wode);

		fr01 = new MinXingCunFragment();
		fr02 = new YeWuBanLiFragment();
		fr03 = new NewNongYeZhuShouFragment();
		// fr03 = new NongYeZhuShouFragment();
		fr04 = new WoDeFragment();

		mLists.add(fr01);
		mLists.add(fr02);
		mLists.add(fr03);
		mLists.add(fr04);

		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mLists.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mLists.get(arg0);
			}
		};

		Mviewpager.setAdapter(adapter);
		Mviewpager.setCurrentItem(1);// 括号里的x变成你的默认页码
		i02.setImageResource(R.drawable.tab_address_pressed);
		tv_yewubanli.setTextColor(getResources().getColor(R.color.blue));
		Mviewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				currentItem = Mviewpager.getCurrentItem();
				setSelet(currentItem);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public String getisReqing() {

		return isReqing;

	}

	public void setisReqing(String isReqing) {
		this.isReqing = isReqing;
	}

	public int returncurrentItem() {
		return currentItem;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.id_mainbuttom_one:
			setSelet(0);
			break;

		case R.id.id_mainbuttom_two:
			setSelet(1);
			break;
		case R.id.id_mainbuttom_three:
			setSelet(2);
			break;
		case R.id.id_mainbuttom_four:
			setSelet(3);
			break;

		default:
			break;
		}

	}

	private void setSelet(int i) {
		// TODO Auto-generated method stub
		qieImage();

		switch (i) {
		case 0:
			i01.setImageResource(R.drawable.tab_weixin_pressed);
			tv_minxingcun.setTextColor(getResources().getColor(R.color.blue));
			tv_yewubanli.setTextColor(getResources()
					.getColor(R.color.ll_chaxun));
			tv_nongyezhushou.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_wode.setTextColor(getResources().getColor(R.color.ll_chaxun));
			Mviewpager.setCurrentItem(0);
			break;

		case 1:
			i02.setImageResource(R.drawable.tab_address_pressed);
			tv_yewubanli.setTextColor(getResources().getColor(R.color.blue));
			tv_minxingcun.setTextColor(getResources().getColor(
					R.color.ll_chaxun));

			tv_nongyezhushou.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_wode.setTextColor(getResources().getColor(R.color.ll_chaxun));
			Mviewpager.setCurrentItem(1);
			break;
		case 2:
			i03.setImageResource(R.drawable.tab_find_frd_pressed);
			tv_nongyezhushou
					.setTextColor(getResources().getColor(R.color.blue));

			tv_minxingcun.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_yewubanli.setTextColor(getResources()
					.getColor(R.color.ll_chaxun));

			tv_wode.setTextColor(getResources().getColor(R.color.ll_chaxun));
			Mviewpager.setCurrentItem(2);
			break;
		case 3:
			i04.setImageResource(R.drawable.tab_settings_pressed);
			tv_wode.setTextColor(getResources().getColor(R.color.blue));
			tv_minxingcun.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_yewubanli.setTextColor(getResources()
					.getColor(R.color.ll_chaxun));
			tv_nongyezhushou.setTextColor(getResources().getColor(
					R.color.ll_chaxun));

			Mviewpager.setCurrentItem(3);
			break;

		default:
			break;
		}
	}

	private void qieImage() {
		// TODO Auto-generated method stub
		i01.setImageResource(R.drawable.tab_weixin_normal);
		i02.setImageResource(R.drawable.tab_address_normal);
		i03.setImageResource(R.drawable.tab_find_frd_normal);
		i04.setImageResource(R.drawable.tab_settings_normal);
	}

	private <T extends View> T getView(int ViewId) {
		View v = findViewById(ViewId);
		return (T) v;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			isExit.setCancelable(false);
			// 设置对话框消息
			isExit.setMessage("确定要退出吗");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);

			// 显示对话框]
			isExit.show();

		}

		return false;

	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
}
