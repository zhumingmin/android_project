package com.minxing.activity;

import com.minxing.restwebservice.HeTongService;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HeTongGuanLiActivity extends Activity {
	private Button chakanbaobiao, yiwenfankui;
	private ImageButton fanhui;
	private TextView caiwu;
	LinearLayout ly_hetong;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_caiwuguanli);
		caiwu = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.caiwu);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		chakanbaobiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ChaKanBaoBiao);
		yiwenfankui = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.YiWenFanKui);
		ly_hetong = (LinearLayout) findViewById(R.id.ly_hetong);

		caiwu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("查询村内财务情况以及处理疑问反馈！");
			}
		});
		ly_hetong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				HeTongGuanLiActivity.this.finish();

			}
		});
		yiwenfankui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HeTongGuanLiActivity.this, ChaKanMoBanActivity.class);

				startActivity(intent);
				HeTongGuanLiActivity.this.finish();
			}
		});
		chakanbaobiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// DisplayToast("网上查询，暂未开通");
				Intent intent = new Intent();
				// intent.setClass(CaiWuGuanLi.this, CW_ChaKanBaoBiao.class);
				intent.setClass(HeTongGuanLiActivity.this, HeTongService.class);
				startActivity(intent);
				HeTongGuanLiActivity.this.finish();
			}
		});
		// yiwenfankui.setOnClickListener(new Button.OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// TODO Auto-generated method stub
		// DisplayToast("方法一：拨打电话，方法二：网上反馈，暂未开通");
		// }
		// });
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(CaiWuGuanLi.this, CaiDanLan.class);
			// startActivity(intent);
			HeTongGuanLiActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
