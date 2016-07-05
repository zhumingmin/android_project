package com.minxing.activity;

import com.minxing.restwebservice.LoginService;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

public class ZaiRuActivity extends Activity {

	private ImageView iv;
	private Handler handler;
	private Drawable[] drawables;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_flash);

		iv = (ImageView) findViewById(R.id.zairujiemian);
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

		drawables = new Drawable[] { getResources().getDrawable(
				R.drawable.flash) };

		iv.setImageDrawable(drawables[0]);

	}

	protected void jump() {
		// TODO jump to homepage activity
		//Intent intent = new Intent(ZaiRuActivity.this, DengLuJieMian.class);
		Intent intent = new Intent(ZaiRuActivity.this, LoginService.class);
		startActivity(intent);
		finish();
	}

}
