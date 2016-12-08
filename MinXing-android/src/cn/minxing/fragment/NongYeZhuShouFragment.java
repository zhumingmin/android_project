package cn.minxing.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.minxing.PushMessage.ExitApplication;

import cn.minxing.rsystem.SerachActivity;

import cn.minxing.util.ActionItem;

import cn.minxing.view.CategoryTabStrip;
import cn.minxing.view.TitlePopup;

import com.zhumingmin.vmsofminxing.R;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class NongYeZhuShouFragment extends Fragment {
	private CategoryTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private ImageButton sousuo, gengduo;
	private TitlePopup titlePopup;

	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/news";
	private static final String TAG = "NongYeZhuShouFragment";

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_nongyezhushou, container,
				false);
		ExitApplication.getInstance().addActivity(getActivity());
		// gengduo = (ImageButton) v.findViewById(R.id.gengduoxianshi);
		// gengduo.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// titlePopup.show(v);
		// }
		// });
		titlePopup = new TitlePopup(getActivity(), LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		initData();
		tabs = (CategoryTabStrip) v.findViewById(R.id.category_strip);
		pager = (ViewPager) v.findViewById(R.id.view_pager);

		ArrayList<View> viewList = new ArrayList<View>();
		ListView listView1 = (ListView) (inflater.inflate(R.layout.listview,
				null)).findViewById(R.id.list);

		sousuo = (ImageButton) v.findViewById(R.id.sousuo);

		adapter = new MyPagerAdapter(getChildFragmentManager());

		pager.setAdapter(adapter);

		tabs.setViewPager(pager);

		sousuo.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent = new Intent(getActivity(), SerachActivity.class);
				startActivity(intent);

			}
		});

		return v;
	}

	private void initData() {

		titlePopup.addAction(new ActionItem(getActivity(), "仅中文",
				R.drawable.mm_title_btn_compose_normal));
		titlePopup.addAction(new ActionItem(getActivity(), "仅英文",
				R.drawable.mm_title_btn_receiver_normal));
		titlePopup.addAction(new ActionItem(getActivity(), "中英混合",
				R.drawable.mm_title_btn_keyboard_normal));
		titlePopup.addAction(new ActionItem(getActivity(), "推荐设置",
				R.drawable.mm_title_btn_qrcode_normal));
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
			return ZiXunFragment.newInstance(position);
		}

	}
}
