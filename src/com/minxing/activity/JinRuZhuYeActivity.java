package com.minxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JinRuZhuYeActivity extends Activity {
	private Button jinruzhuye;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_zairujiemian);
		jinruzhuye = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.JinRuZhuYe);
		jinruzhuye.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(JinRuZhuYeActivity.this, DengLuJieMianActivity.class);
				startActivity(intent);
				JinRuZhuYeActivity.this.finish();
			}
		});
	}

}
