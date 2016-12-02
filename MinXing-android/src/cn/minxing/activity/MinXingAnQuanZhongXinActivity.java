package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import cn.minxing.restfulwebservice.CertificateRest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MinXingAnQuanZhongXinActivity extends Activity {

	private Button zhanghaoanquan, bangzhu, guanyu, shezhi_zhanghaoanquan,
			anquanzhongxin, ceshirukou;
	private TextView xianshizhanghao, xianshixingming, xianshishoujihao;
	private LinearLayout ly_fanhui;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_minxinganquanzhongxin);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_anquanzhongxin);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
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

			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
