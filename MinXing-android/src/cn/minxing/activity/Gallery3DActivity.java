package cn.minxing.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gallery3DActivity extends Activity {
	private Button gengduo, hezuo;
	private TextView minxingcun, minxingcun_01, minxingcun_02, minxingcun_03;
	private ViewPager viewPager;
	private List<ImageView> imageViews;

	private String[] titles;
	private int[] imageResId;
	private List<View> dots;

	private TextView tv_title;
	private int currentItem = 0;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.fragment_minxingzhijia);
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
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());

		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		gengduo = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.GengDuo);
		hezuo = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.HeZuo);
		// minxingcun = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.minxingcun);
		// minxingcun_01 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.minxingcun01);
		// minxingcun_02 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.minxingcun02);
		// minxingcun_03 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.minxingcun03);

		// AssetManager mgr = getAssets();// 得到AssetManager
		// Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");//
		// 根据路径得到Typeface
		// gengduo.setTypeface(tf);
		// hezuo.setTypeface(tf);

		// minxingcun.setTypeface(tf);
		// minxingcun_01.setTypeface(tf);
		// minxingcun_02.setTypeface(tf);
		// minxingcun_03.setTypeface(tf);
		hezuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:18826487090"));
				startActivity(intent);
			}

		});
		gengduo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Gallery3DActivity.this, MinXingCunJieShaoActivity.class);
				startActivity(intent);
				Gallery3DActivity.this.finish();
			}

		});

	}

	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(Gallery3DActivity.this, YeWuBanLiActivity.class);
			startActivity(intent);
			Gallery3DActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}