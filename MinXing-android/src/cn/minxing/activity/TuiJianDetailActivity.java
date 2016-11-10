package cn.minxing.activity;

import cn.minxing.fragment.NewsFragment;
import cn.minxing.util.News;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TuiJianDetailActivity extends Activity {

	private TextView news_detail_title;
	private TextView news_detail_author;
	private TextView news_detail_date;
	private TextView news_detail_commentcount;
	private TextView news_detail_body;
	private ImageButton fanhui1;
	LinearLayout ly_xinwenxiangqing;
	private ImageButton ib_share;

	final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN,
			SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
			SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tuijian_detail);
		fanhui1 = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.xinwen_fanhui);
		ly_xinwenxiangqing = (LinearLayout) findViewById(R.id.ly_xinwenxiangqing);
		ib_share = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.ib_share);
		ib_share.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("将使用第三方工具实现，如友盟提供的分享功能！");
				onActivityResult(0, 0, null);
				shareBySystem();

			}
		});

		new UMShareListener() {
			@Override
			public void onResult(SHARE_MEDIA platform) {
				Toast.makeText(TuiJianDetailActivity.this, platform + " 分享成功啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(SHARE_MEDIA platform, Throwable t) {
				Toast.makeText(TuiJianDetailActivity.this, platform + " 分享失败啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(TuiJianDetailActivity.this, platform + " 分享取消了",
						Toast.LENGTH_SHORT).show();
			}
		};

		ly_xinwenxiangqing.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TuiJianDetailActivity.this, NewsFragment.class);
				startActivity(intent);
				TuiJianDetailActivity.this.finish();

			}
		});
		this.initView();
		this.initData();
		this.testNewsData();
	}

	/** * 通过系统的组件进行分享 */
	private void shareBySystem() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
		// 设置分享的内容
		intent.putExtra(Intent.EXTRA_TEXT, "I have successfully)");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
	}

	private void initData() {

		Intent intent = getIntent();
		intent.getExtras();
		Bundle data = intent.getExtras();
		int position = data.getInt("news_id");
		Log.i("接收到的数据", String.valueOf(position));

		News news = NewsFragment.newsDataList.get(position - 1);
		news_detail_title.setText(news.getTitle());
		news_detail_author.setText(news.getAuthor());
		news_detail_date.setText(news.getPubDate());
		news_detail_commentcount
				.setText(String.valueOf(news.getCommentCount()));
		news_detail_body.setText(news.getBody());

	}

	private void initView() {
		news_detail_title = (TextView) findViewById(R.id.news_detail_title);
		news_detail_author = (TextView) findViewById(R.id.news_detail_author);
		news_detail_commentcount = (TextView) findViewById(R.id.news_detail_commentcount);
		news_detail_date = (TextView) findViewById(R.id.news_detail_date);
		news_detail_body = (TextView) findViewById(R.id.news_detail_body);
	}

	private void testNewsData() {

		Intent intent = getIntent();
		intent.getExtras();
		Bundle data = intent.getExtras();
		int position = data.getInt("news_id");
		Log.i("测试接点击列表项位置", String.valueOf(position));
		News news = NewsFragment.newsDataList.get(position - 1);
		Log.w("标题", news.getTitle());
		Log.w("作者", news.getAuthor());
		Log.w("发表日期", news.getPubDate());
		Log.w("评论数", String.valueOf(news.getCommentCount()));
		Log.w("内容", news.getBody());

	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

}
