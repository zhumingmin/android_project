package cn.minxing.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import cn.minxing.restwebservice.XiaoXiZhongXinService;
import cn.minxing.rsystem.BASE64Decoder;
import cn.minxing.util.RS_News;
import cn.minxing.util.XiaoXi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.jersey.core.util.Base64;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhumingmin.vmsofminxing.R;

public class XiaoXiDetailActivity extends Activity {
	private ImageButton ib_share;
	private TextView biaoti, shijian, neirong;
	private LinearLayout ly_fanhui;
	ImageView imageViewOne;
	ImageView ivPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_xiaoxi_detail);
		imageViewOne = (ImageView) findViewById(R.id.xx_detail_iv);
		biaoti = (TextView) findViewById(R.id.xx_biaoti);
		shijian = (TextView) findViewById(R.id.xx_shijian);

		neirong = (TextView) findViewById(R.id.xx_neirong);

		ivPlay = (ImageView) findViewById(R.id.xx_ivPlay);

		ib_share = (ImageButton) findViewById(R.id.xx_ib_share);
		ib_share.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("将使用第三方工具实现，如友盟提供的分享功能！");

				onActivityResult(0, 0, null);
				shareBySystem();

			}
		});
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_xiaoxi);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

		Intent intent = getIntent();
		intent.getExtras();
		Bundle data = intent.getExtras();
		int position = data.getInt("xiaoxi_id");
		XiaoXi xiaoxi = XiaoXiZhongXinService.xiaoxilist.get(position);
		biaoti.setText(xiaoxi.getBiaoTi());
		shijian.setText(xiaoxi.getShiJian());
		neirong.setText(xiaoxi.getNeiRong());

		new UMShareListener() {
			@Override
			public void onResult(SHARE_MEDIA platform) {
				Toast.makeText(XiaoXiDetailActivity.this, platform + " 分享成功啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(SHARE_MEDIA platform, Throwable t) {
				Toast.makeText(XiaoXiDetailActivity.this, platform + " 分享失败啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(XiaoXiDetailActivity.this, platform + " 分享取消了",
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	/**
	 * 截取全屏
	 * 
	 * @return
	 */
	public Bitmap captureScreenWindow() {
		getWindow().getDecorView().setDrawingCacheEnabled(true);
		Bitmap bmp = getWindow().getDecorView().getDrawingCache();
		return bmp;
	}

	/**
	 * 保存到内存卡
	 * 
	 * @param bitName
	 * @param mBitmap
	 */
	public void saveBitmapForSdCard(String bitName, Bitmap mBitmap) {
		// 创建file对象
		File f = new File("/sdcard/" + bitName + ".png");
		try {
			// 创建
			f.createNewFile();
		} catch (IOException e) {

		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 原封不动的保存在内存卡上
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** * 通过系统的组件进行分享 */
	private void shareBySystem() {
		Bitmap bitmap = captureScreenWindow();
		ivPlay.setImageBitmap(bitmap);
		long time = System.currentTimeMillis();
		saveBitmapForSdCard("img" + time, bitmap);

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Share");

		// 设置分享的内容
		intent.putExtra(Intent.EXTRA_TEXT, biaoti.getText().toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
		ivPlay.setImageBitmap(null);
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
