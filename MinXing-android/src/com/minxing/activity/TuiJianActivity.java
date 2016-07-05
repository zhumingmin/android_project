package com.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import com.minxing.activity.HuJiGuanLiActivity;
import com.minxing.activity.SerachView;
import com.minxing.activity.ShiShiXinWenActivity;
import com.minxing.activity.ZhangHaoAnQuanActivity;
import com.minxing.fragment.NewsFragment;
import com.minxing.restfulwebservice.CertificateRest;
import com.minxing.view.CategoryTabStrip;
import com.zhumingmin.vmsofminxing.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class TuiJianActivity extends FragmentActivity {
	private CategoryTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private ImageButton sousuo, gengduo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);

		tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
		pager = (ViewPager) findViewById(R.id.view_pager);
		sousuo = (ImageButton) findViewById(R.id.sousuo);
		gengduo = (ImageButton) findViewById(R.id.gengduoxianshi);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		tabs.setViewPager(pager);
		sousuo.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TuiJianActivity.this, SerachView.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_right_out,
						R.anim.activity_left_in);
				// TuiJian.this.finish();

			}
		});
		gengduo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final String[] items = getResources().getStringArray(
						R.array.item3);
				new AlertDialog.Builder(TuiJianActivity.this)
						.setTitle("请点击跳转相关页面！")
						.setItems(items, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								new AlertDialog.Builder(TuiJianActivity.this)
										.setTitle("你选择了:" + items[which])
										.setMessage("准备跳转，请确认")
										.setPositiveButton(
												"确认跳转",
												new android.content.DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface arg0,
															int arg1) {
														// TODO Auto-generated
														// method stub
														arg0.dismiss();
														Intent in2 = new Intent();
														in2.setAction(Intent.ACTION_VIEW);
														if (items.toString() == "网易新闻") {
															in2.setData(Uri
																	.parse("http://news.163.com"));
															startActivity(in2);
														} else if (items
																.toString() == "新浪新闻") {
															in2.setData(Uri
																	.parse("http://news.sina.com.hk/"));
															startActivity(in2);
														} else if (items
																.toString() == "腾讯新闻") {
															in2.setData(Uri
																	.parse("http://news.qq.com/"));
															startActivity(in2);
														} else if (items
																.toString() == "搜狐新闻") {
															in2.setData(Uri
																	.parse("http://news.sohu.com/"));
															startActivity(in2);
														} else {
															in2.setData(Uri
																	.parse("http://news.ifeng.com/"));
															startActivity(in2);
														}

													}
												})
										.setNegativeButton(
												"下次阅读",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int which) {
														// 这里点击取消之后可以进行的操作
													}
												}).show();
							}
						}).show();
			}
		});
		// gengduo.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// DisplayToast("稍后开通，敬请关注！");
		//
		// }
		// });

	}

	protected void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final List<String> catalogs = new ArrayList<String>();

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			catalogs.add(getString(R.string.category_hot));
			catalogs.add("\u672c\u5730");
			catalogs.add(getString(R.string.category_nongyexinwen));// nongyexinwen
			catalogs.add(getString(R.string.category_nongyezhengce));// nongyezhengce
			catalogs.add(getString(R.string.category_shengchanzhidao));// shengchanzhidao
			catalogs.add(getString(R.string.category_qita));// qita
			/*
			 * 这里注释后滑动出现光斑
			 */
			// catalogs.add(getString(R.string.category_finance));
			// catalogs.add(getString(R.string.category_military));
			// catalogs.add(getString(R.string.category_world));
			// catalogs.add(getString(R.string.category_image_ppmm));
			// catalogs.add(getString(R.string.category_health));
			// catalogs.add(getString(R.string.category_government));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return catalogs.get(position);
		}

		@Override
		public int getCount() {
			return catalogs.size();
		}

		@Override
		public Fragment getItem(int position) {
			return NewsFragment.newInstance(position);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			TuiJianActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
