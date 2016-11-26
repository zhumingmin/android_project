package cn.minxing.activity;

import cn.minxing.restwebservice.MinYiZhengJiService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class SheZhiActivity extends Activity {

	private Button tongxunlu, tianqichaxun, xinxichaxun, xianliao, shezhi,
			toupiao;
	private ImageView gerenxinxi, jiantou;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.fragment_shezhi);

		gerenxinxi = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.right_permsg_center_img_usericon);
		jiantou = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.jiantou);
		tongxunlu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.tongxunlu);
		// tianqichaxun = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.tianqichaxun);
		xinxichaxun = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.xinxichaxun);
		xianliao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.xianliao);
		shezhi = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shezhi);
		toupiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.toupiao);

		toupiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, MinYiZhengJiService.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
		tongxunlu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, TongXunLuActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
		// tianqichaxun.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(SheZhi.this, TianQiChaXun.class);
		// startActivity(intent);
		// SheZhi.this.finish();
		// }
		//
		// });
		xinxichaxun.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, ReadTagActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}

		});
		xianliao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, ChatRobtActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
		shezhi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, SZ_SheZhiActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
		gerenxinxi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, SZ_GRXXActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
		jiantou.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SheZhiActivity.this, SZ_GRXXActivity.class);
				startActivity(intent);
				SheZhiActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(SheZhi.this, CaiDanLan.class);
			// startActivity(intent);
			SheZhiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
