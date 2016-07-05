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
import android.widget.LinearLayout;
import android.widget.TextView;

public class WangJiMiMaActivity extends Activity {
	private Button tijiao, fanhui;
	private TextView zhucezhanghao, lianxidianhua;
	ProgressDialog m_pDialog;
	int m_count = 0;
	private LinearLayout ly_fanhui;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_wangjimima);
		tijiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.wjmm_TiJiao);
		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_wangjimima);
		zhucezhanghao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ZhuCeZhangHao);
		lianxidianhua = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.LianXiDianHua);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				WangJiMiMaActivity.this.finish();
			}
		});
		tijiao.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				m_count = 0;

				m_pDialog = new ProgressDialog(WangJiMiMaActivity.this);

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
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			WangJiMiMaActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
