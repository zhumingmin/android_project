package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowDescriptionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rss_show_description);
		String content = null;

		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent
					.getBundleExtra("android.intent.extra.rssItem");
			if (bundle == null) {
				content = "不好意思程序出错啦";
			} else {
				content = bundle.getString("title") + "nn"
						+ bundle.getString("pubdate") + "nn"
						+ bundle.getString("description").replace('n', ' ')
						+ "\n\n详细信息请访问以下网址:\n" + bundle.getString("link");
			}
		} else {
			content = "不好意思程序出错啦";
		}

		TextView contentText = (TextView) this.findViewById(R.id.content);
		contentText.setText(content);

		Button backButton = (Button) this.findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
