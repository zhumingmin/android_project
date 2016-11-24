package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.PushMessage.XiaoMiTuiSong;
import cn.minxing.restwebservice.LoginService;
import cn.minxing.restwebservice.YiBaoService;
import cn.minxing.util.JianChaGengXin;
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
import android.widget.LinearLayout;
import android.widget.Toast;

public class SZ_SheZhiActivity extends Activity {

	private Button zhanghaoanquan, bangzhu, guanyu, shezhi_zhanghaoanquan,
			shezhi_gengxin, zhuxiaodenglu;
	String data1, data2, data3;
	private LinearLayout ly_fanhui;

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
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_shezhi);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Intent intent = getIntent();
		data1 = intent.getStringExtra("data1");
		data2 = intent.getStringExtra("data2");
		data3 = intent.getStringExtra("data3");

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
				intent = new Intent(SZ_SheZhiActivity.this,
						ZhangHaoAnQuanActivity.class);
				intent.putExtra("data1", data1);
				intent.putExtra("data2", data2);
				intent.putExtra("data3", data3);
				startActivity(intent);
			}

		});

		bangzhu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SZ_SheZhiActivity.this, HelpActivity.class);
				startActivity(intent);

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
