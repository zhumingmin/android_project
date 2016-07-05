package com.minxing.activity;

import com.minxing.PushMessage.ExitApplication;
import com.minxing.PushMessage.XiaoMiTuiSong;
import com.minxing.restwebservice.LoginService;
import com.minxing.restwebservice.YiBaoService;
import com.minxing.util.JianChaGengXin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SZ_SheZhiActivity extends Activity {

	private Button zhanghaoanquan, bangzhu, guanyu, shezhi_zhanghaoanquan,
			shezhi_gengxin, zhuxiaodenglu;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_shezhi_shezhi);
		ExitApplication.getInstance().addActivity(this);
		zhanghaoanquan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi_zhanghaoanquan);
		shezhi_gengxin = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi_gengxin);
		bangzhu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi_bangzhu);
		guanyu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi_guanyu);
		zhuxiaodenglu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhuxiaodenglu);
		shezhi_zhanghaoanquan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi_zhanghaoanquan);

		zhuxiaodenglu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent logoutIntent = new Intent(SZ_SheZhi.this,
				// DengLuJieMian.class);
				// logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				// | Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(logoutIntent);

				Intent intent = new Intent(SZ_SheZhiActivity.this,
						LoginService.class);
				intent.putExtra("from", 1);
				startActivity(intent);
				ExitApplication.getInstance().exit();
				finish();

			}
		});
		shezhi_gengxin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("将使用第三方工具实现，如小米提供的检查更新的功能！");
				Intent intent = new Intent(SZ_SheZhiActivity.this,
						JianChaGengXin.class);

				startActivity(intent);

			}
		});
		zhanghaoanquan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SZ_SheZhiActivity.this,
						ZhangHaoAnQuanActivity.class);
				startActivity(intent);
				SZ_SheZhiActivity.this.finish();
			}

		});

		bangzhu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SZ_SheZhiActivity.this, HelpActivity.class);
				startActivity(intent);
				SZ_SheZhiActivity.this.finish();
			}

		});
		guanyu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SZ_SheZhiActivity.this,
						GuanYuMinXingZhiJiaActivity.class);
				startActivity(intent);
				SZ_SheZhiActivity.this.finish();
			}
		});

	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(SZ_SheZhi.this, SheZhi.class);
			// startActivity(intent);
			SZ_SheZhiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
