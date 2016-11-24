package cn.minxing.restwebservice;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import cn.minxing.activity.UploadImageActivity;
import cn.minxing.restwebservice.PictureService;
import cn.minxing.restwebservice.RegisterService;
import cn.minxing.util.ImagePath;
import cn.minxing.util.ImageSelect;
import cn.minxing.util.ImageSelect.OnImageSelectClickListener;

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
import android.net.ParseException;
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
	private ImageSelect imageSelect, imageSelect1;
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
			shangchuanzhaopian1, hujiguanli, tv, tv1;
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
		// 首先判断选中的是哪个tab
		if (getTabHost().getCurrentTab() == 0) {
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
				// Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！",
				// 0).show();
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
		} else {
			if (requestCode == ImageSelect.PHOTO_REQUEST_GALLERY) {
				// 从相册返回的数据
				if (data != null) {
					// 得到图片的全路径

					uri = data.getData();

					imageSelect1.crop(uri);

				}

			} else if (requestCode == ImageSelect.PHOTO_REQUEST_CAREMA) {
				// 从相机返回的数据
				// if (hasSdcard()) {

				imageSelect1.crop(Uri.fromFile(imageSelect1.tempFile));
				// } else {
				// Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！",
				// 0).show();
				// }

			} else if (requestCode == ImageSelect.PHOTO_REQUEST_CUT) {
				// 从剪切图片返回的数据
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");

					imageSelect1.addImageByBitmap(bitmap);

				}

				try {
					// 将临时文件删除

					if (imageSelect1.tempFile != null) {
						imageSelect1.tempFile.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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

		tv = (TextView) mTabHost.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		tv.setTextSize(20);

		tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1)
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
		// shangchuan = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.huji_ShangChuan);
		// shangchuan1 = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.huji_ShangChuan1);
		tijiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.huji_tijiao);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
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
		// boolean ismessagepush = false;// 不开启就设置为false;
		// if (ismessagepush) {
		// Intent intent = new Intent();
		// intent.setAction("ymw.MY_SERVICE");
		// startService(intent);
		// }
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

				if (getTabHost().getCurrentTab() == 0) {
					images = imageSelect.getImages();
					String[] pojo = { MediaStore.Images.Media.DATA };
					if (uri != null) {
						Cursor cursor = managedQuery(uri, pojo, null, null,
								null);
						if (cursor != null) {
							int columnIndex = cursor
									.getColumnIndexOrThrow(pojo[0]);
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

						Toast.makeText(getApplicationContext(),
								"网络未连接，请检查您的网络！", 0).show();
						return;
					}
					if (tianxiexingming.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "迁入人姓名不能空！", 0)
								.show();
						return;
					}
					if (tianhaoma.getText().toString().equals("")
							&& !IDCardValidate(tianhaoma.getText().toString())) {
						Toast.makeText(getApplicationContext(), "身份证号码不能空！", 0)
								.show();
						return;
					}
					if (!IDCardValidate(tianhaoma.getText().toString())) {
						Toast.makeText(getApplicationContext(), "身份证号码格式不正确！",
								0).show();
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
							WebServiceTask.POST_TASK, HuJiService.this,
							"正在上传中...");

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
				} else {
					images = imageSelect1.getImages();
					String[] pojo = { MediaStore.Images.Media.DATA };
					if (uri != null) {
						Cursor cursor = managedQuery(uri, pojo, null, null,
								null);
						if (cursor != null) {
							int columnIndex = cursor
									.getColumnIndexOrThrow(pojo[0]);
							cursor.moveToFirst();
							picPath = cursor.getString(columnIndex);
							up = imageToString(picPath);
							cursor.close();
						}
					}
					String uploadpicture = up;
					String xingming = tianxiexingming1.getText().toString();
					String haoma = tianhaoma1.getText().toString();
					String liyou = tianxieliyou1.getText().toString();
					if (!isNetworkAvailable(HuJiService.this)) {

						Toast.makeText(getApplicationContext(),
								"网络未连接，请检查您的网络！", 0).show();
						return;
					}
					if (tianxiexingming1.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "迁出人姓名不能空！", 0)
								.show();
						return;
					}
					if (tianhaoma1.getText().toString().equals("")
							&& !IDCardValidate(tianhaoma1.getText().toString())) {
						Toast.makeText(getApplicationContext(), "身份证号码不能空！", 0)
								.show();
						return;
					}
					if (!IDCardValidate(tianhaoma1.getText().toString())) {
						Toast.makeText(getApplicationContext(), "身份证号码格式不正确！",
								0).show();
						return;
					}
					if (tianxieliyou1.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "迁出理由不能空！", 0)
								.show();
						return;
					}
					if (images.size() == 0) {
						Toast.makeText(getApplicationContext(), "必须上传证明图片！", 0)
								.show();
						return;
					}

					WebServiceTask wst = new WebServiceTask(
							WebServiceTask.POST_TASK, HuJiService.this,
							"正在上传中...");

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
			}
		});

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		// shangchuan.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(HuJiService.this, PictureService.class);
		// startActivity(intent);
		// finish();
		//
		// }
		// });
		//
		// shangchuan1.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(HuJiService.this, UploadImageActivity.class);
		// startActivity(intent);
		// HuJiService.this.finish();
		//
		// }
		// });
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
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
						Toast.makeText(getApplicationContext(),
								"第" + id + "张图片", 0).show();
					}
				});
		imageSelect1 = (ImageSelect) findViewById(R.id.imageSelect1);
		imageSelect1
				.setOnImageSelectClickListener(new OnImageSelectClickListener() {
					@Override
					public void onClick(int id) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								"第" + id + "张图片", 0).show();
					}
				});
	}

	// // 上传信息到服务器 POST方法
	// public void postData(View vw) {
	// // imageToString(picPath);
	//
	// String uploadpicture = up;
	// String xingming = tianxiexingming.getText().toString();
	// String haoma = tianhaoma.getText().toString();
	// String liyou = tianxieliyou.getText().toString();
	// if (!isNetworkAvailable(HuJiService.this)) {
	//
	// Toast.makeText(getApplicationContext(), "网络未连接，请检查您的网络！", 0).show();
	// return;
	// }
	// if (tianxiexingming.getText().toString().equals("")) {
	// Toast.makeText(getApplicationContext(), "迁入人姓名不能空！", 0).show();
	// return;
	// }
	// if (tianhaoma.getText().toString().equals("")) {
	// Toast.makeText(getApplicationContext(), "身份证号码不能空！", 0).show();
	// return;
	// }
	// if (tianxieliyou.getText().toString().equals("")) {
	// Toast.makeText(getApplicationContext(), "迁入理由不能空！", 0).show();
	// return;
	// }
	// if (images.size() == 0) {
	// Toast.makeText(getApplicationContext(), "必须上传证明图片！", 0).show();
	// return;
	// }
	//
	// WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
	// "正在上传中...");
	//
	// wst.addNameValuePair("qianchurenxingming", xingming);
	// wst.addNameValuePair("shenfenzhenghaoma", haoma);
	// wst.addNameValuePair("qianchuliyou", liyou);
	// wst.addNameValuePair("uploadpicture", uploadpicture);
	// wst.addNameValuePair("picPath", picPath);
	// // the passed String is the URL we will POST to
	// wst.execute(new String[] { SERVICE_URL });
	//
	// }

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
