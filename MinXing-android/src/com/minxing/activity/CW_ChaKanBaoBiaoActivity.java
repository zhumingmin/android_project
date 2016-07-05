package com.minxing.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CW_ChaKanBaoBiaoActivity extends Activity {

	public CW_ChaKanBaoBiaoActivity() {
		// TODO Auto-generated constructor stub
	}

	private TextView yuanyin;
	private EditText tianxieyuanyin;
	private ImageButton fanhuishangyiye;
	private Button tijiaoxinxi, tijiaohetong;
	int m_count = 0;
	ProgressDialog m_pDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_zaixiantianxiehetong);
		fanhuishangyiye = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHuiShangYiYe);
		tijiaohetong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.tijiaohetong);
		tijiaohetong.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				m_count = 0;

				m_pDialog = new ProgressDialog(CW_ChaKanBaoBiaoActivity.this);

				m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

				m_pDialog.setTitle("提示");

				m_pDialog.setMessage("正在提交，请稍后……");

				m_pDialog
						.setIcon(com.zhumingmin.vmsofminxing.R.drawable.tubiao1);

				m_pDialog.setProgress(100);

				m_pDialog.setIndeterminate(false);

				m_pDialog.setCancelable(true);

				m_pDialog.show();

				new Thread() {
					public void run() {
						try {
							while (m_count <= 100) {

								m_pDialog.setProgress(m_count++);
								Thread.sleep(100);
							}
							m_pDialog.cancel();
						} catch (InterruptedException e) {
							m_pDialog.cancel();
						}
					}
				}.start();

			}
		});
		fanhuishangyiye.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CW_ChaKanBaoBiaoActivity.this, HeTongGuanLiActivity.class);
				startActivity(intent);
				CW_ChaKanBaoBiaoActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			Intent intent = new Intent();
			intent.setClass(CW_ChaKanBaoBiaoActivity.this, HeTongGuanLiActivity.class);
			startActivity(intent);
			CW_ChaKanBaoBiaoActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
