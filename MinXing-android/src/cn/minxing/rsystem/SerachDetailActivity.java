package cn.minxing.rsystem;

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

import cn.minxing.util.RS_News;
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

public class SerachDetailActivity extends Activity {
	private ImageButton jiafen, koufen, ib_share;
	private TextView title, category, readnumber, tuijian, butuijian, body;
	private LinearLayout ly_fanhui;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/newsnumber";
	String picturepath = null;
	private static final String TAG = "SerachDetailActivity";
	ImageView imageViewOne;
	final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN,
			SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
			SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN };
	ImageView ivPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail_rs);
		imageViewOne = (ImageView) findViewById(R.id.news_detail_iv);
		title = (TextView) findViewById(R.id.title);
		category = (TextView) findViewById(R.id.category);
		readnumber = (TextView) findViewById(R.id.readnumber);
		tuijian = (TextView) findViewById(R.id.tuijiannumber);
		butuijian = (TextView) findViewById(R.id.butuijiannumber);
		body = (TextView) findViewById(R.id.body);
		jiafen = (ImageButton) findViewById(R.id.jiafen);
		koufen = (ImageButton) findViewById(R.id.koufen);

		ivPlay = (ImageView) findViewById(R.id.ivPlay);

		ib_share = (ImageButton) findViewById(R.id.ib_share);
		ib_share.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("将使用第三方工具实现，如友盟提供的分享功能！");

				onActivityResult(0, 0, null);
				shareBySystem();

			}
		});
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_xiangqing);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String biaoti = title.getText().toString();
				String yueduliang = readnumber.getText().toString();
				String like = tuijian.getText().toString();
				String unlike = butuijian.getText().toString();

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachDetailActivity.this,
						"提交中，请稍候...");

				wst.addNameValuePair("title", biaoti);
				wst.addNameValuePair("read", yueduliang);
				wst.addNameValuePair("like", like);
				wst.addNameValuePair("unlike", unlike);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachDetailActivity";
				Intent intent = new Intent(SerachDetailActivity.this,
						SerachListActivity.class);
				intent.putExtra("classname", classname);
				startActivity(intent);
				finish();
			}
		});

		Intent intent = getIntent();
		intent.getExtras();
		Bundle data = intent.getExtras();
		int position = data.getInt("news_id");
		RS_News news = SerachListActivity.newslist.get(position);
		title.setText(news.getBiaoTi());
		category.setText(news.getLeiBie());
		readnumber.setText(String.valueOf(Integer.parseInt(news
				.getYueDuLiang2()) + 1));
		body.setText(news.getBody());
		tuijian.setText(news.getLike());
		butuijian.setText(news.getUnlike());
		picturepath = news.getTupiandizhi();
		if (picturepath == null) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final Bitmap bitmap = getHttpBitmap("http://img.tvb.com/inews_web/web/generic_thumbnail.jpg");
					imageViewOne.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							imageViewOne.setImageBitmap(bitmap);
						}
					});
				}
			}).start();
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final Bitmap bi = getBitmap(picturepath);// 这里调用的是后台传来的图片的base64字符串编码
					imageViewOne.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							imageViewOne.setImageBitmap(bi);
						}
					});
				}
			}).start();

		}
		jiafen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				jiafen.setBackgroundResource(R.drawable.like_pressed);
				DisplayToast("感谢您的反馈！");
				int a = Integer.parseInt(tuijian.getText().toString());
				a = a + 1;
				tuijian.setText(String.valueOf(a));
				koufen.setEnabled(false);// 设置这个属性
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
				jiafen.setEnabled(false);// 设置这个属性
			}
		});
		new UMShareListener() {
			@Override
			public void onResult(SHARE_MEDIA platform) {
				Toast.makeText(SerachDetailActivity.this, platform + " 分享成功啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(SHARE_MEDIA platform, Throwable t) {
				Toast.makeText(SerachDetailActivity.this, platform + " 分享失败啦",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(SerachDetailActivity.this, platform + " 分享取消了",
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	public void postData(View vw) {

		String biaoti = title.getText().toString();
		String yueduliang = readnumber.getText().toString();
		String like = tuijian.getText().toString();
		String unlike = butuijian.getText().toString();

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"提交中，请稍候...");

		wst.addNameValuePair("title", biaoti);
		wst.addNameValuePair("read", yueduliang);
		wst.addNameValuePair("like", like);
		wst.addNameValuePair("unlike", unlike);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

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
		intent.putExtra(Intent.EXTRA_TEXT, title.getText().toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
		ivPlay.setImageBitmap(null);
	}

	// 主要操作部分
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {

			// if (response != null) {
			// Toast.makeText(getApplicationContext(), "上传成功！", 0).show();
			//
			// } else {
			// Toast.makeText(getApplicationContext(), "上传失败！", 0).show();
			// }
			pDlg.dismiss();

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters 解决服务器端中文乱码的问题
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					// httppost.setEntity(new
					// StringEntity(params.toString(),HTTP.UTF_8));
					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

	}

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/*
	 * 解码后台传过来的已经编码的图片
	 */
	public Bitmap getBitmap(String iconBase64) {
		if (iconBase64 == null)
			return null;
		try {
			// Base64解码
			BASE64Decoder decoder = new BASE64Decoder();
			iconBase64 = iconBase64.replace("data:image/jpeg;base64,", "");

			byte[] b = decoder.decodeBuffer(iconBase64);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					// 调整异常数据
					b[i] += 256;
				}
			}
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} catch (Exception e) {
			return null;
		}
		// byte[] bitmapArray;
		// bitmapArray = Base64.decode(iconBase64);
		// return BitmapFactory
		// .decodeByteArray(bitmapArray, 0, bitmapArray.length);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			String biaoti = title.getText().toString();
			String yueduliang = readnumber.getText().toString();
			String like = tuijian.getText().toString();
			String unlike = butuijian.getText().toString();

			WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
					this, "提交中，请稍候...");

			wst.addNameValuePair("title", biaoti);
			wst.addNameValuePair("read", yueduliang);
			wst.addNameValuePair("like", like);
			wst.addNameValuePair("unlike", unlike);

			// the passed String is the URL we will POST to
			wst.execute(new String[] { SERVICE_URL });
			String classname = "SerachDetailActivity";
			Intent intent = new Intent(SerachDetailActivity.this,
					SerachListActivity.class);
			intent.putExtra("classname", classname);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
