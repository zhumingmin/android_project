package cn.minxing.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.activity.Gallery3DActivity;
import cn.minxing.activity.MinXingCunJieShaoActivity;
import cn.minxing.activity.YeWuBanLiActivity;
import cn.minxing.util.TextJustification;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

@SuppressLint("NewApi")
public class MinXingCunFragment extends Fragment {
	private Button gengduo, hezuo;
	private TextView minxingcun, minxingcun_01, minxingcun_02, minxingcun_03,
			text_minxingzhijia;
	private ViewPager viewPager;
	private List<ImageView> imageViews;

	private String[] titles;
	private int[] imageResId;
	private List<View> dots;

	private TextView tv_title;
	private int currentItem = 0;
	static Point size;
	static float density;
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_minxingzhijia, container,
				false);
		ExitApplication.getInstance().addActivity(getActivity());
		text_minxingzhijia = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.text_minxingzhijia);
		String str = "民兴村有区一级小学、幼儿园各一所，镇人民医院分院一间，综合性市场一个。民兴村政村位于万顷沙镇三民岛中部，全村共有五百五十六户， 户籍人口两千一百五十人，辖区面积为两百六十七公顷，耕地面积三千三百八十亩。二零零八年村工农业总产值两千九百二十七万元。在上级党委和政府的正确领导和大力支持下，经多年努力创建，昔日的扶贫村、问题村已先后获得广州市文明村、南沙区基层党建示范点、南沙区文明村、万顷沙镇工作先进村等区、镇荣誉称号。近几年来，该村以发展经济为中心，以村镇规划为龙头，以改善民生为重点，积极推进新农村建设，全村呈现出了经济发展、生活安定、社会和谐的生动局面。"
				+ "\n";

		text_minxingzhijia.setText(str);

		// text_minxingzhijia.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// Display display = getActivity().getWindowManager()
		// .getDefaultDisplay();
		// DisplayMetrics dm = new DisplayMetrics();
		// display.getMetrics(dm);
		// int width = dm.widthPixels;
		// // 根据屏幕调整文字大小
		// text_minxingzhijia.setLineSpacing(0f, 1.5f);
		// text_minxingzhijia.setTextSize(8 * (float) width / 320f);
		// // 设置TextView
		// TextJustification.justify(text_minxingzhijia,
		// text_minxingzhijia.getWidth());
		// }
		//
		// });

		imageResId = new int[] { R.drawable.item1, R.drawable.item2,
				R.drawable.item3, R.drawable.item4, R.drawable.item5 };
		titles = new String[imageResId.length];
		titles[0] = "广东省广州市万顷沙镇民兴村美景欣赏";
		titles[1] = "广东省广州市万顷沙镇民兴村美景欣赏";
		titles[2] = "广东省广州市万顷沙镇民兴村美景欣赏";
		titles[3] = "广东省广州市万顷沙镇民兴村美景欣赏";
		titles[4] = "广东省广州市万顷沙镇民兴村美景欣赏";

		imageViews = new ArrayList<ImageView>();

		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(v.findViewById(R.id.v_dot0));
		dots.add(v.findViewById(R.id.v_dot1));
		dots.add(v.findViewById(R.id.v_dot2));
		dots.add(v.findViewById(R.id.v_dot3));
		dots.add(v.findViewById(R.id.v_dot4));

		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) v.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());

		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		// gengduo = (Button) v
		// .findViewById(com.zhumingmin.vmsofminxing.R.id.GengDuo);
		// hezuo = (Button)
		// v.findViewById(com.zhumingmin.vmsofminxing.R.id.HeZuo);
		//
		// hezuo.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(Intent.ACTION_CALL);
		// intent.setData(Uri.parse("tel:18826487090"));
		// startActivity(intent);
		// }
		//
		// });
		text_minxingzhijia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent = new Intent(getActivity(),
						MinXingCunJieShaoActivity.class);
				startActivity(intent);

			}

		});
		return v;
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {

		scheduledExecutorService.shutdown();
		super.onStop();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

}
