package cn.minxing.activity;

import cn.minxing.restwebservice.LoginService;
import cn.minxing.util.VolleyLoadPicture;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ZaiRuActivity extends Activity {

	private ImageView iv;
	private Handler handler;
	// private Drawable[] drawables;
	public static final String picUrl = "http://imgsrc.baidu.com/forum/w%3D580/sign=dbe111adbc315c6043956be7bdb0cbe6/6fc2a21ea8d3fd1f7a2b1f82304e251f95ca5f35.jpg";

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
		vlp.getmImageLoader().get(picUrl, vlp.getOne_listener());

		handler = new Handler() {

			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				jump();

			}
		};

		handler.sendEmptyMessageDelayed(0, 2000);

		// drawables = new Drawable[] { getResources().getDrawable(
		// R.drawable.flash) };
		//
		// iv.setImageDrawable(drawables[0]);

	}

	protected void jump() {
		// TODO jump to homepage activity
		// Intent intent = new Intent(ZaiRuActivity.this, DengLuJieMian.class);
		Intent intent = new Intent(ZaiRuActivity.this, LoginService.class);
		startActivity(intent);
		finish();
	}

}
