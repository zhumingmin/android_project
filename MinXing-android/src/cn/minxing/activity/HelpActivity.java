package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	private LinearLayout ly_fanhui;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_help);
		String str = "民兴之家是华南农业大学与民兴村进行产学研合作的产物。民兴之家主要用于民兴村的村务管理以及提供农务信息服务。民兴之家的开发从最初的需求分析到实际开发，都在考虑如何让村名使得更加方便。我们从村民的角度出发，最后再回到村民的手中。民兴之家客户端要求手机支持NFC功能，且固件等级要求4.0+，如有更多问题，请联系：\n"
				+ "手机:18826487090\n"
				+ "QQ:1070612553\n"
				+ "微信:z1070612553\n"
				+ "欢迎大家一起完善这个项目，谢谢！";
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_help);
		TextView guanyu = (TextView) findViewById(R.id.guanyu);
		Button liuyan = (Button) findViewById(R.id.liuyanban);
		guanyu.setText(str);
		liuyan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HelpActivity.this, LiuYanActivity.class);
				startActivity(intent);

			}

		});
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(HelpActivity.this, SZ_SheZhiActivity.class);
			startActivity(intent);
			HelpActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
