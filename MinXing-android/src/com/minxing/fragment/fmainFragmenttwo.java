package com.minxing.fragment;

import com.minxing.PushMessage.ExitApplication;
import com.minxing.activity.HeTongGuanLiActivity;
import com.minxing.activity.HuJiGuanLiActivity;
import com.minxing.activity.JiHuaShengYuActivity;
import com.minxing.activity.MinXingCunJieShaoActivity;
import com.minxing.activity.NewsActivity;
import com.minxing.activity.YiBaoSheBaoActivity;
import com.minxing.restwebservice.BaoJianService;
import com.minxing.restwebservice.HuJiService;
import com.zhumingmin.vmsofminxing.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class fmainFragmenttwo extends Fragment {

	private ImageButton hujiguanli, yibaoguanli, jihuashengyu, caiwuguanli,
			baojianguanli, shishixinwen;
	private Button dianhuazixun;
	private TextView hujiguanli1, yibaoguanli1, jihuashengyu1, caiwuguanli1,
			baojianguanli1, shishixinwen1, mTime;
	private static final int msgKey1 = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_newhome, container, false);
		ExitApplication.getInstance().addActivity(getActivity());
		hujiguanli = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.HuJiGuanLi);
		yibaoguanli = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.YiBaoGuanLi);
		jihuashengyu = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.JiHuaShengYu);
		caiwuguanli = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.CaiWuGuanLi);
		baojianguanli = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.BaoJianGuanLi);
		shishixinwen = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ShiShiXinWen);

		hujiguanli1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.HuJiGuanLi1);
		yibaoguanli1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.YiBaoGuanLi1);
		jihuashengyu1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.JiHuaShengYu1);
		caiwuguanli1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.CaiWuGuanLi1);
		baojianguanli1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.BaoJianGuanLi1);
		shishixinwen1 = (TextView) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ShiShiXinWen1);
		mTime = (TextView) v.findViewById(R.id.mytime);
		new TimeThread().start();
		dianhuazixun = (Button) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.DianHuaZiXun);

		hujiguanli.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// intent = new Intent(getActivity(), HuJiGuanLi.class);
				intent = new Intent(getActivity(), HuJiService.class);
				startActivity(intent);

			}
		});

		yibaoguanli.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), YiBaoSheBaoActivity.class);
				startActivity(intent);
			}
		});
		jihuashengyu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), JiHuaShengYuActivity.class);
				startActivity(intent);
			}
		});
		caiwuguanli.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), HeTongGuanLiActivity.class);
				startActivity(intent);
			}
		});
		baojianguanli.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// intent = new Intent(getActivity(), BaoJianGuanLi.class);
				intent = new Intent(getActivity(), BaoJianService.class);
				startActivity(intent);
			}
		});
		shishixinwen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent = new Intent(getActivity(), NewsActivity.class);
				// intent = new Intent(getActivity(), RssActivity.class);
				startActivity(intent);
			}
		});
		dianhuazixun.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final String[] items = getResources().getStringArray(
						R.array.item);
				new AlertDialog.Builder(getActivity()).setTitle("请点击选择咨询业务！")
						.setItems(items, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								new AlertDialog.Builder(getActivity())
										.setTitle("你选择了:" + items[which])
										.setMessage("准备接通咨询，请确认")
										.setPositiveButton(
												"确认咨询",
												new android.content.DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface arg0,
															int arg1) {
														// TODO Auto-generated
														// method stub
														arg0.dismiss();
														Intent in2 = new Intent();
														in2.setAction(Intent.ACTION_CALL);
														if (items.toString() == "户籍咨询") {
															in2.setData(Uri
																	.parse("tel:18826487090"));
															startActivity(in2);
														} else if (items
																.toString() == "医保咨询") {
															in2.setData(Uri
																	.parse("tel:18826487090"));
															startActivity(in2);
														} else if (items
																.toString() == "财务咨询") {
															in2.setData(Uri
																	.parse("tel:18826487090"));
															startActivity(in2);
														} else if (items
																.toString() == "计生咨询") {
															in2.setData(Uri
																	.parse("tel:18826487090"));
															startActivity(in2);
														} else {
															in2.setData(Uri
																	.parse("tel:18826487090"));
															startActivity(in2);
														}

													}
												})
										.setNegativeButton(
												"下次咨询",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int which) {
														// 这里点击取消之后可以进行的操作
													}
												}).show();
							}
						}).show();
			}
		});
		return v;
	}

	public class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = msgKey1;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case msgKey1:
				long sysTime = System.currentTimeMillis();
				CharSequence sysTimeStr = DateFormat.format(
						"当前时间:yyyy年MM月dd日hh:mm:ss，祝您生活愉快！", sysTime);
				mTime.setText(sysTimeStr);
				break;

			default:
				break;
			}
		}
	};

}
