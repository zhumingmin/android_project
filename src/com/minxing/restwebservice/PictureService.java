package com.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.kobjects.base64.Base64;

import com.minxing.activity.DengLuJieMianActivity;
import com.minxing.activity.HuJiGuanLiActivity;
import com.minxing.activity.SelectPicActivity;
import com.minxing.activity.ZhuCeActivity;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PictureService extends Activity implements OnClickListener {

	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/uploadpicture";

	private static final String TAG = "PictureService";

	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	private Button selectButton, uploadButton;
	private ImageView imageView;
	private TextView uploadImageResult;
	private ProgressBar progressBar;

	private String picPath = null;
	private String up = null;
	private ProgressDialog progressDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.uploadimage);
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initView() {
		selectButton = (Button) this
				.findViewById(com.zhumingmin.vmsofminxing.R.id.selectImage);
		uploadButton = (Button) this
				.findViewById(com.zhumingmin.vmsofminxing.R.id.uploadImage);
		selectButton.setOnClickListener(this);
		// uploadButton.setOnClickListener(this);
		imageView = (ImageView) this
				.findViewById(com.zhumingmin.vmsofminxing.R.id.imageView);
		uploadImageResult = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.uploadImageResult);
		progressDialog = new ProgressDialog(this);
		progressBar = (ProgressBar) findViewById(com.zhumingmin.vmsofminxing.R.id.progressBar1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case com.zhumingmin.vmsofminxing.R.id.selectImage:
			Intent intent = new Intent(this, SelectPicActivity.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		// case com.zhumingmin.vmsofminxing.R.id.uploadImage:
		// if (picPath != null) {
		// handler.sendEmptyMessage(TO_UPLOAD_FILE);
		// } else {
		// Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
		// }
		// break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Log.i(TAG, "最终选择的图片=" + picPath);
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			up = imageToString(picPath);
			imageView.setImageBitmap(bm);
		}
		super.onActivityResult(requestCode, resultCode, data);
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

	// 上传信息到服务器 POST方法
	public void postData(View vw) {
		String uploadpicture = up;
		if (uploadpicture == null) {
			Toast.makeText(this, "照片选择不正确，请重新选择需要上传的图片", Toast.LENGTH_LONG)
					.show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"正在上传，请稍后……");

		wst.addNameValuePair("uploadpicture", uploadpicture);
		wst.addNameValuePair("picPath", picPath);

		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponse(String response) {
		String str = up;
		str = null;
		String str2 = null;
		try {

			JSONObject jso = new JSONObject(response);

			String uploadpicture = jso.getString("uploadpicture");
			String picpath = jso.getString("picPath");
			str = uploadpicture;
			str2 = picpath;

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(PictureService.this, HuJiService.class);
			startActivity(intent);
			PictureService.this.finish();
			overridePendingTransition(R.anim.activity_right_out,
					R.anim.activity_left_in);
		}
		return super.onKeyDown(keyCode, event);
	}
}
