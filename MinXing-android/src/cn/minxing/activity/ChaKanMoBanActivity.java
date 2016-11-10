package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class ChaKanMoBanActivity extends Activity {

	public ChaKanMoBanActivity() {
		// TODO Auto-generated constructor stub
	}

	Bitmap bp = null;
	private TextView yuanyin;
	private EditText tianxieyuanyin;
	private Button tudihetong, dianpuhetong;
	private ImageButton fanhuishangyiye;
	int m_count = 0;
	ProgressDialog m_pDialog;
	ImageView hetong, hetong1;
	float scaleWidth;
	float scaleHeight;
	int h;
	boolean num = false;
	LinearLayout ly_fanhui;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_chakanmoban);
		fanhuishangyiye = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHuiShangYiYe);
		tudihetong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.tudihetong);
		dianpuhetong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.dianpuhetong);
		hetong = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.hetong);
		hetong1 = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.hetong1);
		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_chakanmuban);

		Display display = getWindowManager().getDefaultDisplay();
		bp = BitmapFactory.decodeResource(getResources(), R.drawable.hetong);
		int width = bp.getWidth();
		int height = bp.getHeight();
		int w = display.getWidth();
		int h = display.getHeight();
		scaleWidth = ((float) w) / width;
		scaleHeight = ((float) h) / height;
		hetong.setImageBitmap(bp);
		hetong1.setImageBitmap(bp);
		hetong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (num == true) {
					Matrix matrix = new Matrix();
					matrix.postScale(scaleWidth, scaleHeight);

					Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0,
							bp.getWidth(), bp.getHeight(), matrix, true);
					hetong.setImageBitmap(newBitmap);
					num = false;
				} else {
					Matrix matrix = new Matrix();
					matrix.postScale(1.0f, 1.0f);
					Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0,
							bp.getWidth(), bp.getHeight(), matrix, true);
					hetong.setImageBitmap(newBitmap);
					num = true;
				}
			}
		});
		hetong1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (num == true) {
					Matrix matrix = new Matrix();
					matrix.postScale(scaleWidth, scaleHeight);

					Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0,
							bp.getWidth(), bp.getHeight(), matrix, true);
					hetong1.setImageBitmap(newBitmap);
					num = false;
				} else {
					Matrix matrix = new Matrix();
					matrix.postScale(1.0f, 1.0f);
					Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0,
							bp.getWidth(), bp.getHeight(), matrix, true);
					hetong1.setImageBitmap(newBitmap);
					num = true;
				}
			}
		});
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ChaKanMoBanActivity.this, HeTongGuanLiActivity.class);
				startActivity(intent);
				ChaKanMoBanActivity.this.finish();

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			Intent intent = new Intent();
			intent.setClass(ChaKanMoBanActivity.this, HeTongGuanLiActivity.class);
			startActivity(intent);
			ChaKanMoBanActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
