package cn.minxing.activity;

import cn.minxing.rsystem.SerachActivity;
import cn.minxing.rsystem.SerachListActivity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

/*
 *这里需要完成的操作有：
 *1.获取某个关键词的相关标题
 *2.获取每个标题的阅读量，推荐数，不推荐
 */
public class TianJiaHuiDaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webservice_ugc);
		LinearLayout ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_tianjia);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TianJiaHuiDaActivity.this,
						SerachListActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
