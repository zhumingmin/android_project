package com.minxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SZ_GRXXActivity extends Activity {
	private TextView grxx_xingming_tv, grxx_lianxifangshi_tv,
			grxx_shenfenzhenghao_tv;
	private EditText grxx_xingming_et, grxx_lianxifangshi_et,
			grxx_shenfenzhenghao_et;
	private Button grxx_chuansong;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_gerenxinxi);
		grxx_xingming_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_xingming_tv);
		grxx_lianxifangshi_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_lianxifangshi_tv);
		grxx_shenfenzhenghao_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_shenfenzhenghao_tv);
		grxx_xingming_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_xingming_et);
		grxx_lianxifangshi_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_lianxifangshi_et);
		grxx_shenfenzhenghao_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_shenfenzhenghao_et);
		grxx_chuansong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_chuansong);
		grxx_xingming_et.setText("张三");
		grxx_lianxifangshi_et.setText("18826487090");
		grxx_shenfenzhenghao_et.setText("123009988433337657");

		grxx_chuansong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SZ_GRXXActivity.this, AndroidBeamMainActivity.class);
				intent.putExtra("GGXX", grxx_xingming_tv.getText().toString()
						.trim()
						+ grxx_xingming_et.getText().toString().trim()
						+ "\n"
						+ grxx_lianxifangshi_tv.getText().toString().trim()
						+ grxx_lianxifangshi_et.getText().toString().trim()
						+ "\n"
						+ grxx_shenfenzhenghao_tv.getText().toString().trim()
						+ grxx_shenfenzhenghao_et.getText().toString().trim());

				startActivity(intent);
				SZ_GRXXActivity.this.finish();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(SZ_GRXX.this, SheZhi.class);
			// startActivity(intent);
			SZ_GRXXActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
