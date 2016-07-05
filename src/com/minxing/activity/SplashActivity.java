package com.minxing.activity;

import com.minxing.restwebservice.LoginService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(com.zhumingmin.vmsofminxing.R.layout.splash);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 1000);

	}

	class splashhandler implements Runnable {

		public void run() {
			//startActivity(new Intent(getApplication(), DengLuJieMian.class));
			startActivity(new Intent(getApplication(), YeWuBanLiActivity.class));
			SplashActivity.this.finish();
		}

	}

}
