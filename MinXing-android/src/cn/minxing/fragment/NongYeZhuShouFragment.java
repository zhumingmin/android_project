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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class NongYeZhuShouFragment extends MyFragment {
	private CategoryTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private ImageButton sousuo, gengduo;
	private TitlePopup titlePopup;

	private static final String TAG = "NongYeZhuShouFragment";
	View v;
	private boolean isReady = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ExitApplication.getInstance().addActivity(getActivity());
		if (v == null) {

			v = inflater.inflate(R.layout.fragment_nongyezhushou, container,
					false);
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
		if (sousuo == null) {
			init();
		}
	}

	public void init() {
		titlePopup = new TitlePopup(getActivity(), LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		initData();
		tabs = (CategoryTabStrip) v.findViewById(R.id.category_strip);
		pager = (ViewPager) v.findViewById(R.id.view_pager);
		sousuo = (ImageButton) v.findViewById(R.id.sousuo);

		// ArrayList<View> viewList = new ArrayList<View>();
		// ListView listView1 = (ListView) (inflater.inflate(R.layout.listview,
		// null)).findViewById(R.id.list);

		// 关闭预加载，默认一次只加载一个Fragment
		// pager.setOffscreenPageLimit(1);
		pager.setCurrentItem(1);
		adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);

		/*
		 * 在oncreateview里面只是加载fragment的根布局。
		 * 然后在viewpager的onpagerselectlistener里面， 当选中到当前页的时候判断当前页面是否填充
		 */

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			// 设置ViewPager的OnPageChangeListener监听器
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				if (positionOffsetPixels == 0 && positionOffset == 0) {
					// 在这里面刷新数据
					// flushData();
					tabs.setViewPager(pager);
				}
			}

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		sousuo.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent = new Intent(getActivity(), SerachActivity.class);
				startActivity(intent);

			}
		});
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
