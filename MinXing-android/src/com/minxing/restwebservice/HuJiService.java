package com.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import com.minxing.activity.UploadImageActivity;
import com.minxing.restwebservice.PictureService;
import com.minxing.restwebservice.RegisterService;
import com.minxing.util.ImagePath;
import com.minxing.util.ImageSelect;
import com.minxing.util.ImageSelect.OnImageSelectClickListener;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
@SuppressWarnings("deprecation")
public class HuJiService extends TabActivity {
	private ImageSelect imageSelect;
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/hujiqianchu";
	private static final String TAG = "HuJiGuanLi";
	private Button paizhao, shangchuan, paizhao1, shangchuan1, tijiao, tijiao1;
	private ImageButton fanhui;
	TabHost mTabHost;
	private ImageView imageView;
	LinearLayout ly_fanhui;
	private Uri uri;
	private EditText tianxiexingming, tianhaoma, tianxieliyou,
			tianxiexingming1, tianhaoma1, tianxieliyou1;
	private TextView xingming, shengfenzhenghaoma, liyou, xingming1,
			shengfenzhenghaoma1, liyou1, shangchuanzhaopian,
			shangchuanzhaopian1, hujiguanli;
	ProgressDialog m_pDialog;
	private ArrayList<Bitmap> images;// 上传的图片
	int m_count = 0;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private String picPath = null;
	private String up = null;
	public static final int TO_SELECT_PHOTO = 3;
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ImageSelect.PHOTO_REQUEST_GALLERY) {
			// 从相册返回的数据
			if (data != null) {
				// 得到图片的全路径

				uri = data.getData();

				imageSelect.crop(uri);

			}

		} else if (requestCode == ImageSelect.PHOTO_REQUEST_CAREMA) {
			// 从相机返回的数据
			// if (hasSdcard()) {
			imageSelect.crop(Uri.fromFile(imageSelect.tempFile));
			// } else {
			// Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！", 0).show();
			// }

		} else if (requestCode == ImageSelect.PHOTO_REQUEST_CUT) {
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");

				imageSelect.addImageByBitmap(bitmap);

			}

			try {
				// 将临时文件删除
				if (imageSelect.tempFile != null) {
					imageSelect.tempFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_hujiguanli);
		initImageSelect();
		initView();
	}

	private void initView() {
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

		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui);
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

		/*
		 * 户籍管理中的通知功能的实现
		 */
		boolean ismessagepush = false;// 不开启就设置为false;
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
		/*
		 * 提交的流程：首先判断网络是否连接，然后判断相关信息是否填写，图片是否选择！
		 */
		tijiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				images = imageSelect.getImages();
				String[] pojo = { MediaStore.Images.Media.DATA };
				if (uri != null) {
					Cursor cursor = managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
						cursor.moveToFirst();
						picPath = cursor.getString(columnIndex);
						up = imageToString(picPath);
						cursor.close();
					}
				}
				String uploadpicture = up;
				String xingming = tianxiexingming.getText().toString();
				String haoma = tianhaoma.getText().toString();
				String liyou = tianxieliyou.getText().toString();
				if (!isNetworkAvailable(HuJiService.this)) {

					Toast.makeText(getApplicationContext(), "网络未连接，请检查您的网络！", 0)
							.show();
					return;
				}
				if (tianxiexingming.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "迁入人姓名不能空！", 0)
							.show();
					return;
				}
				if (tianhaoma.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "身份证号码不能空！", 0)
							.show();
					return;
				}
				if (tianxieliyou.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "迁入理由不能空！", 0)
							.show();
					return;
				}
				if (images.size() == 0) {
					Toast.makeText(getApplicationContext(), "必须上传证明图片！", 0)
							.show();
					return;
				}

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, HuJiService.this, "正在上传中...");

				wst.addNameValuePair("qianchurenxingming", xingming);
				wst.addNameValuePair("shenfenzhenghaoma", haoma);
				wst.addNameValuePair("qianchuliyou", liyou);
				wst.addNameValuePair("uploadpicture", uploadpicture);
				wst.addNameValuePair("picPath", picPath);
				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				// 上传成功后的反馈信息
				if (wst.getHttpParams().toString() != null) {
					Toast.makeText(getApplicationContext(), "您已上传成功！", 0)
							.show();
				}
			}
		});
		tianxieliyou.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("填写完毕记得提交哦！");
			}
		});
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		shangchuan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HuJiService.this, PictureService.class);
				startActivity(intent);
				finish();

			}
		});

		shangchuan1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HuJiService.this, UploadImageActivity.class);
				startActivity(intent);
				HuJiService.this.finish();

			}
		});
	}

	/**
	 * 利用BASE64Encoder对图片进行base64转码将图片转为string
	 * 
	 * @param imgFile
	 *            文件路径
	 * @return 返回编码后的string
	 */
	public static String imageToString(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		String str = new String(Base64.encode(data));
		return str;
	}

	private void initImageSelect() {
		imageSelect = (ImageSelect) findViewById(R.id.imageSelect);
		imageSelect
				.setOnImageSelectClickListener(new OnImageSelectClickListener() {
					@Override
					public void onClick(int id) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "" + id, 0)
								.show();
					}
				});
	}

	// 上传信息到服务器 POST方法
	public void postData(View vw) {
		// imageToString(picPath);

		String uploadpicture = up;
		String xingming = tianxiexingming.getText().toString();
		String haoma = tianhaoma.getText().toString();
		String liyou = tianxieliyou.getText().toString();
		if (!isNetworkAvailable(HuJiService.this)) {

			Toast.makeText(getApplicationContext(), "网络未连接，请检查您的网络！", 0).show();
			return;
		}
		if (tianxiexingming.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "迁入人姓名不能空！", 0).show();
			return;
		}
		if (tianhaoma.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "身份证号码不能空！", 0).show();
			return;
		}
		if (tianxieliyou.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "迁入理由不能空！", 0).show();
			return;
		}
		if (images.size() == 0) {
			Toast.makeText(getApplicationContext(), "必须上传证明图片！", 0).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"正在上传中...");

		wst.addNameValuePair("qianchurenxingming", xingming);
		wst.addNameValuePair("shenfenzhenghaoma", haoma);
		wst.addNameValuePair("qianchuliyou", liyou);
		wst.addNameValuePair("uploadpicture", uploadpicture);
		wst.addNameValuePair("picPath", picPath);
		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {

		tianxiexingming.setText("");
		tianhaoma.setText("");
		tianxieliyou.setText("");
		String str = up;
		str = null;
		String str2 = null;
		try {

			JSONObject jso = new JSONObject(response);

			String xingming = jso.getString("qianchurenxingming");
			String haoma = jso.getString("shenfenzhenghaoma");
			String liyou = jso.getString("qianchuliyou");
			String uploadpicture = jso.getString("uploadpicture");
			String picpath = jso.getString("picPath");
			str = uploadpicture;
			str2 = picpath;
			tianxiexingming.setText(xingming);
			tianhaoma.setText(haoma);
			tianxieliyou.setText(liyou);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) HuJiService.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(HuJiService.this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
			if (response != null) {
				Toast.makeText(getApplicationContext(), "上传成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "上传失败！", 0).show();
			}
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
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
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
	 * 检测当的网络（WLAN、3G/2G）状态
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}
