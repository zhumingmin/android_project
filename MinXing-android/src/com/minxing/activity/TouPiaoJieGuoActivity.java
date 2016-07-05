package com.minxing.activity;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;

import android.view.Window;

import android.widget.Button;

import android.widget.Toast;

public class TouPiaoJieGuoActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_toupiaojieguo);

	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(TouPiaoJieGuoActivity.this, MinYiZhengJiActivity.class);
			startActivity(intent);
			TouPiaoJieGuoActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
