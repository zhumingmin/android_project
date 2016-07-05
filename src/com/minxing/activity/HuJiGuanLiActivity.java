package com.minxing.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
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
import org.json.JSONObject;

import com.minxing.restwebservice.PictureService;
import com.minxing.restwebservice.RegisterService;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class HuJiGuanLiActivity extends TabActivity {
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/hujiqianchu";
	private static final String TAG = "HuJiGuanLi";
	private Button paizhao, shangchuan, paizhao1, shangchuan1, tijiao, tijiao1;
	private ImageButton fanhui;
	TabHost mTabHost;
	private ImageView imageView;
	private EditText tianxiexingming, tianhaoma, tianxieliyou,
			tianxiexingming1, tianhaoma1, tianxieliyou1;
	private TextView xingming, shengfenzhenghaoma, liyou, xingming1,
			shengfenzhenghaoma1, liyou1, shangchuanzhaopian,
			shangchuanzhaopian1, hujiguanli;
	ProgressDialog m_pDialog;
	int m_count = 0;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_hujiguanli);
		mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1")
				.setIndicator("户口迁入", null)
				.setContent(com.zhumingmin.vmsofminxing.R.id.HuKouQianRu));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2")
				.setIndicator("户口迁出", null)
				.setContent(com.zhumingmin.vmsofminxing.R.id.HuKouQianChu));
		mTabHost.setBackgroundColor(TRIM_MEMORY_BACKGROUND);
		mTabHost.setCurrentTab(0);
		TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		tv.setTextSize(20);

		TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1)
				.findViewById(android.R.id.title);
		tv1.setTextSize(20);

		hujiguanli = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.hujiguanli);
		xingming = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.XingMing);
		xingming1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.XingMing1);
		shengfenzhenghaoma = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ShengFenZhengHaoMa);
		shengfenzhenghaoma1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ShengFenZhengHaoMa1);
		liyou = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.LiYou);
		liyou1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.LiYou1);
		shangchuanzhaopian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ShangChuanZhaoPian);
		shangchuanzhaopian1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ShangChuanZhaoPian1);
		shangchuan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ShangChuan);
		shangchuan1 = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ShangChuan1);
		tijiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.TiJiao);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		tianxiexingming = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieXingMing);
		tianhaoma = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianHaoMa);
		tianxieliyou = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieLiYou);
		tianxiexingming1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieXingMing1);
		tianhaoma1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianHaoMa1);
		tianxieliyou1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieLiYou1);
		tianxiexingming.setHint("如张三");
		tianhaoma.setHint("如1234567890");
		tianxieliyou.setHint("如小孩出生");
		tianxiexingming1.setHint("如张三");
		tianhaoma1.setHint("如1234567890");
		tianxieliyou1.setHint("如出嫁");

		boolean ismessagepush = true;// 不开启就设置为false;
		if (ismessagepush) {
			Intent intent = new Intent();
			intent.setAction("ymw.MY_SERVICE");
			startService(intent);
		}
		hujiguanli.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("办理户籍迁入迁出等业务！");
			}
		});
		tianxieliyou.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("填写完毕记得提交哦！");
			}
		});
		fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HuJiGuanLiActivity.this, YeWuBanLiActivity.class);
				startActivity(intent);
				HuJiGuanLiActivity.this.finish();
			}
		});

		paizhao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// Intent intent = new
				// Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
				// Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				// startActivityForResult(intent, 1);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "temp.jpg")));
				startActivityForResult(intent, PHOTOHRAPH);
			}
		});
		shangchuan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(Intent.ACTION_PICK, null);
				// intent.setDataAndType(
				// MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				// IMAGE_UNSPECIFIED);
				// startActivityForResult(intent, PHOTOZOOM);
				Intent intent = new Intent();
				// intent.setClass(HuJiGuanLi.this, UploadImage.class);
				intent.setClass(HuJiGuanLiActivity.this, PictureService.class);
				startActivity(intent);
				HuJiGuanLiActivity.this.finish();

			}
		});

		paizhao1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// Intent intent = new
				// Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
				// Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				// startActivityForResult(intent, 1);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "temp.jpg")));
				startActivityForResult(intent, PHOTOHRAPH);
			}
		});
		// tijiao.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (tianxiexingming.getContext().toString() == null
		// || tianhaoma.getContext().toString() == null
		// || tianxieliyou.getContext().toString() == null) {
		// DisplayToast("信息未填写完整！");
		// } else {
		// m_count = 0;
		//
		// m_pDialog = new ProgressDialog(HuJiGuanLi.this);
		//
		// m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//
		// m_pDialog.setTitle("提示");
		//
		// m_pDialog.setMessage("正在上传，请稍后……");
		//
		// m_pDialog
		// .setIcon(com.zhumingmin.vmsofminxing.R.drawable.tubiao1);
		//
		// m_pDialog.setProgress(100);
		//
		// m_pDialog.setIndeterminate(false);
		//
		// m_pDialog.setCancelable(true);
		//
		// m_pDialog.show();
		//
		// new Thread() {
		// public void run() {
		// try {
		// while (m_count <= 100) {
		//
		// m_pDialog.setProgress(m_count++);
		// Thread.sleep(100);
		// }
		// m_pDialog.cancel();
		// } catch (InterruptedException e) {
		// m_pDialog.cancel();
		// }
		// }
		// }.start();
		//
		// }
		// }
		// });

		shangchuan1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(Intent.ACTION_PICK, null);
				// intent.setDataAndType(
				// MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				// IMAGE_UNSPECIFIED);
				// startActivityForResult(intent, PHOTOZOOM);
				Intent intent = new Intent();
				intent.setClass(HuJiGuanLiActivity.this,
						UploadImageActivity.class);
				// intent.setClass(HuJiGuanLi.this, PictureService.class);
				startActivity(intent);
				HuJiGuanLiActivity.this.finish();

			}
		});

	}

	// 上传信息到服务器 POST方法
	public void postData(View vw) {

		String xingming = tianxiexingming.getText().toString();
		String haoma = tianhaoma.getText().toString();
		String liyou = tianxieliyou.getText().toString();

		if (xingming.equals("") || haoma.equals("") || liyou.equals("")) {
			Toast.makeText(this, "Please enter in all required fields.",
					Toast.LENGTH_LONG).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Posting data...");

		wst.addNameValuePair("qianchurenxingming", xingming);
		wst.addNameValuePair("shenfenzhenghaoma", haoma);
		wst.addNameValuePair("qianchuliyou", liyou);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {

		tianxiexingming.setText("");
		tianhaoma.setText("");
		tianxieliyou.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			String xingming = jso.getString("qianchurenxingming");
			String haoma = jso.getString("shenfenzhenghaoma");
			String liyou = jso.getString("qianchuliyou");

			tianxiexingming.setText(xingming);
			tianhaoma.setText(haoma);
			tianxieliyou.setText(liyou);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) HuJiGuanLiActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(HuJiGuanLiActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
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

			hideKeyboard();
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

			handleResponse(response);
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
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params));
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}
		if (data == null)
			return;
		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
				imageView.setImageBitmap(photo);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽�?
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	/*
	 * public boolean onKeyDown(int keyCode, KeyEvent event) { if (keyCode ==
	 * KeyEvent.KEYCODE_BACK) return true; return true;
	 * 
	 * }
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			HuJiGuanLiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
