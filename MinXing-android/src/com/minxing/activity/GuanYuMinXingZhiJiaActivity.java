package com.minxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

public class GuanYuMinXingZhiJiaActivity extends Activity {

	private TextView guanyu1, guanyu2, guanyu3;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_guanyuminxingzhijia);

		guanyu1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.guanyu1);
		guanyu2 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.guanyu2);
		guanyu3 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.guanyu3);

		//AssetManager mgr = getAssets();// 得到AssetManager
		//Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");// 根据路径得到Typeface
		//guanyu1.setTypeface(tf);
		//guanyu2.setTypeface(tf);
		//guanyu3.setTypeface(tf);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(GuanYuMinXingZhiJiaActivity.this, SZ_SheZhiActivity.class);
			startActivity(intent);
			GuanYuMinXingZhiJiaActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
