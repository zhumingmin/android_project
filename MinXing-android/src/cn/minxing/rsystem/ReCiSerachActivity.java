package cn.minxing.rsystem;

import cn.minxing.util.RS_News;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 这里需要完成的操作有：
 * 1.从后台获取数据并显示
 * 2.上传阅读量，推荐数，不推荐到后台
 * 需要考虑的：
 * 1.需要显示图片
 * 2.如何上传数据到后台（返回即上传）
 */
public class ReCiSerachActivity extends Activity {
	private ImageButton jiafen, koufen;
	private TextView title, category, readnumber, tuijian, butuijian, body;
	private LinearLayout ly_fanhui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sousuoreci_rs);
		title = (TextView) findViewById(R.id.title);
		category = (TextView) findViewById(R.id.category);
		readnumber = (TextView) findViewById(R.id.readnumber);
		tuijian = (TextView) findViewById(R.id.tuijiannumber);
		butuijian = (TextView) findViewById(R.id.butuijiannumber);
		body = (TextView) findViewById(R.id.body);
		jiafen = (ImageButton) findViewById(R.id.jiafen);
		koufen = (ImageButton) findViewById(R.id.koufen);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_xiangqing);

		Intent intent = getIntent();
		title.setText(intent.getStringExtra("reci") + "的热门文章");

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		jiafen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jiafen.setBackgroundResource(R.drawable.like_pressed);
				DisplayToast("感谢您的反馈！");
				int a = Integer.parseInt(tuijian.getText().toString());
				a = a + 1;
				tuijian.setText(String.valueOf(a));
				// 这里后续的操作是上传阅读量，推荐数，不推荐数至后台

			}
		});
		koufen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				koufen.setBackgroundResource(R.drawable.unlike_pressed);
				DisplayToast("感谢您的反馈！");
				int a = Integer.parseInt(butuijian.getText().toString());
				a = a + 1;
				butuijian.setText(String.valueOf(a));
			}
		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
