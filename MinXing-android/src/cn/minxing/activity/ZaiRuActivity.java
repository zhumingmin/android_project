package cn.minxing.activity;

import cn.minxing.restwebservice.LoginService;
import cn.minxing.util.VolleyLoadPicture;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ZaiRuActivity extends Activity {
	boolean isFirstIn;
	public static String sMallId = "";
	private ImageView iv;
	private Handler handler;
	// private Drawable[] drawables;
	// public static final String picUrl =
	// "http://imgsrc.baidu.com/forum/w%3D580/sign=dbe111adbc315c6043956be7bdb0cbe6/6fc2a21ea8d3fd1f7a2b1f82304e251f95ca5f35.jpg";
	public static final String picUrl1 = "https://c2.staticflickr.com/4/3275/2732543799_07738a574d_b.jpg";
	public static final String picUrl2 = "http://ww1.sinaimg.cn/large/54916ae8jw1ds87vwzpjtj.jpg";

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_flash);

		iv = (ImageView) findViewById(R.id.zairujiemian);
		TextView tv = (TextView) findViewById(R.id.slogan);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/ziti.TTF");
		tv.setTypeface(typeFace);

		VolleyLoadPicture vlp = new VolleyLoadPicture(this, iv);

		// vlp.getmImageLoader().get(picUrl, vlp.getOne_listener());
		vlp.getmImageLoader().get(picUrl1, vlp.getOne_listener());
		vlp.getmImageLoader().get(picUrl2, vlp.getOne_listener());

		// handler = new Handler() {
		//
		// @SuppressLint("HandlerLeak")
		// @Override
		// public void handleMessage(Message msg) {
		// // TODO Auto-generated method stub
		// super.handleMessage(msg);
		// jump();
		//
		// }
		// };
		//
		// handler.sendEmptyMessageDelayed(0, 2000);

		// drawables = new Drawable[] { getResources().getDrawable(
		// R.drawable.flash) };
		// iv.setImageDrawable(drawables[0]);

		// 获取SharedPreferences对象
		SharedPreferences sp = ZaiRuActivity.this.getSharedPreferences("SP",
				MODE_PRIVATE);
		isFirstIn = sp.getBoolean("isFirstIn", true);
		if (isFirstIn) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					goGuide();
				}
			}, 2000);
			Editor editor = sp.edit();
			editor.putBoolean("isFirstIn", false);
			editor.commit();
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					goHome();
				}
			}, 2000);
		}
	}

	protected void jumpHome() {
		// TODO jump to homepage activity
		// Intent intent = new Intent(ZaiRuActivity.this, DengLuJieMian.class);
		Intent intent = new Intent(ZaiRuActivity.this, YeWuBanLiActivity.class);
		startActivity(intent);
		finish();
	}

	private void goHome() {

		Intent intent = new Intent(ZaiRuActivity.this, LoginService.class);
		startActivity(intent);
		finish();
	}

	private void goGuide() {
		Intent intent = new Intent(ZaiRuActivity.this,
				GuideViewPagerActivity.class);
		startActivity(intent);
		finish();
	}

}
