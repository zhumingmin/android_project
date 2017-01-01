package cn.minxing.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.fragment.MinXingCunFragment;
import cn.minxing.fragment.NongYeZhuShouFragment;
import cn.minxing.fragment.WoDeFragment;
import cn.minxing.fragment.YeWuBanLiFragment;
import cn.minxing.fragment.zixun.BenDiFragment;
import cn.minxing.fragment.zixun.NongYeXinWenFragment;
import cn.minxing.fragment.zixun.NongYeZhengCeFragment;
import cn.minxing.fragment.zixun.QiTaFragment;
import cn.minxing.fragment.zixun.ReDianFragment;
import cn.minxing.fragment.zixun.ShengChanZhiDaoFragment;
import cn.minxing.rsystem.SerachActivity;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class NewNongYeZhuShouFragment extends MyFragment implements
		View.OnClickListener {

	private ViewPager Mviewpager;
	private LinearLayout layoutone, layouttwo, layoutthree, layoutfour,
			layoutfive, layoutsix;
	private ImageButton sousuo;
	private FragmentPagerAdapter adapter;
	private List<Fragment> mLists = new ArrayList<Fragment>();
	private TextView tv_redian, tv_bendi, tv_nongyexinwen, tv_nongyezhengce,
			tv_shengchanzhidao, tv_qita;
	private Fragment fr01, fr02, fr03, fr04, fr05, fr06;
	private static final String TAG = "YeWuBanLiActivity";
	int currentItem;
	static String isReqing;
	View v;

	private boolean isReady = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ExitApplication.getInstance().addActivity(getActivity());
		if (v == null) {

			v = inflater.inflate(R.layout.zixun_viewpager, container, false);
			isReady = true;
			delayLoad();
			Log.d("info", "onCreateView");
		} else {
			Log.d("info", "rootView != null");
		}

		// Cache rootView.
		// remove rootView from its parent
		ViewGroup parent = (ViewGroup) v.getParent();
		if (parent != null) {
			parent.removeView(v);
		}

		return v;
	}

	@Override
	protected void delayLoad() {
		if (!isReady || !isVisible) {
			return;
		}

		// 　This is a random widget, it will be instantiation in init()
		if (tv_redian == null) {
			init();
		}
	}

	public void init() {
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		tv_redian.setOnClickListener(this);
		tv_bendi.setOnClickListener(this);
		tv_nongyexinwen.setOnClickListener(this);
		tv_nongyezhengce.setOnClickListener(this);
		tv_shengchanzhidao.setOnClickListener(this);
		tv_qita.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		Mviewpager = (ViewPager) v.findViewById(R.id.vp_zixun);
		Mviewpager.setOffscreenPageLimit(1);
		sousuo = (ImageButton) v.findViewById(R.id.sousuo);
		tv_redian = (TextView) v.findViewById(R.id.tv_redian);
		tv_bendi = (TextView) v.findViewById(R.id.tv_bendi);
		tv_nongyexinwen = (TextView) v.findViewById(R.id.tv_nongyexinwen);
		tv_nongyezhengce = (TextView) v.findViewById(R.id.tv_nongyezhengce);
		tv_shengchanzhidao = (TextView) v.findViewById(R.id.tv_shengchanzhidao);
		tv_qita = (TextView) v.findViewById(R.id.tv_qita);
		sousuo.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent = new Intent(getActivity(), SerachActivity.class);
				startActivity(intent);

			}
		});
		fr01 = new ReDianFragment();
		fr02 = new BenDiFragment();
		fr03 = new NongYeXinWenFragment();
		fr04 = new NongYeZhengCeFragment();
		fr05 = new ShengChanZhiDaoFragment();
		fr06 = new QiTaFragment();

		mLists.add(fr01);
		mLists.add(fr02);
		mLists.add(fr03);
		mLists.add(fr04);
		mLists.add(fr05);
		mLists.add(fr06);

		adapter = new FragmentPagerAdapter(getChildFragmentManager()) {

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
		//Mviewpager.setCurrentItem(1);// 括号里的x变成你的默认页码
		tv_redian.setTextColor(getResources().getColor(R.color.blue));
		tv_redian.setTextSize(20);
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
		case R.id.tv_redian:
			setSelet(0);
			break;

		case R.id.tv_bendi:
			setSelet(1);
			break;
		case R.id.tv_nongyexinwen:
			setSelet(2);
			break;
		case R.id.tv_nongyezhengce:
			setSelet(3);
			break;
		case R.id.tv_shengchanzhidao:
			setSelet(4);
			break;
		case R.id.tv_qita:
			setSelet(5);
			break;
		default:
			break;
		}

	}

	private void setSelet(int i) {
		// TODO Auto-generated method stub

		switch (i) {
		case 0:

			tv_redian.setTextColor(getResources().getColor(R.color.blue));
			tv_redian.setTextSize(20);
			tv_bendi.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_bendi.setTextSize(15);
			tv_nongyexinwen.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyexinwen.setTextSize(15);
			tv_nongyezhengce.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyezhengce.setTextSize(15);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_shengchanzhidao.setTextSize(15);
			tv_qita.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_qita.setTextSize(15);
			Mviewpager.setCurrentItem(0);
			break;

		case 1:
			tv_redian.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_redian.setTextSize(15);
			tv_bendi.setTextColor(getResources().getColor(R.color.blue));
			tv_bendi.setTextSize(20);
			tv_nongyexinwen.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyexinwen.setTextSize(15);
			tv_nongyezhengce.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyezhengce.setTextSize(15);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_shengchanzhidao.setTextSize(15);
			tv_qita.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_qita.setTextSize(15);
			Mviewpager.setCurrentItem(1);
			break;
		case 2:
			tv_redian.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_redian.setTextSize(15);
			tv_bendi.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_bendi.setTextSize(15);
			tv_nongyexinwen.setTextColor(getResources().getColor(R.color.blue));
			tv_nongyexinwen.setTextSize(20);
			tv_nongyezhengce.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyezhengce.setTextSize(15);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_shengchanzhidao.setTextSize(15);
			tv_qita.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_qita.setTextSize(15);
			Mviewpager.setCurrentItem(2);
			break;
		case 3:
			tv_redian.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_redian.setTextSize(15);
			tv_bendi.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_bendi.setTextSize(15);
			tv_nongyexinwen.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyexinwen.setTextSize(15);
			tv_nongyezhengce
					.setTextColor(getResources().getColor(R.color.blue));
			tv_nongyezhengce.setTextSize(20);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_shengchanzhidao.setTextSize(15);
			tv_qita.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_qita.setTextSize(15);
			Mviewpager.setCurrentItem(3);
			break;
		case 4:
			tv_redian.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_redian.setTextSize(15);
			tv_bendi.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_bendi.setTextSize(15);
			tv_nongyexinwen.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyexinwen.setTextSize(15);
			tv_nongyezhengce.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyezhengce.setTextSize(15);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.blue));
			tv_shengchanzhidao.setTextSize(20);
			tv_qita.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_qita.setTextSize(15);
			Mviewpager.setCurrentItem(4);
			break;
		case 5:
			tv_redian.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_redian.setTextSize(15);
			tv_bendi.setTextColor(getResources().getColor(R.color.ll_chaxun));
			tv_bendi.setTextSize(15);
			tv_nongyexinwen.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyexinwen.setTextSize(15);
			tv_nongyezhengce.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_nongyezhengce.setTextSize(15);
			tv_shengchanzhidao.setTextColor(getResources().getColor(
					R.color.ll_chaxun));
			tv_shengchanzhidao.setTextSize(15);
			tv_qita.setTextColor(getResources().getColor(R.color.blue));
			tv_qita.setTextSize(20);
			Mviewpager.setCurrentItem(5);
			break;
		default:
			break;
		}
	}

}
