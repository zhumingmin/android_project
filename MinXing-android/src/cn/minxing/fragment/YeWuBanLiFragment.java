package cn.minxing.fragment;

import cn.minxing.PushMessage.ExitApplication;
import cn.minxing.activity.CatchActivity;
import cn.minxing.activity.HuJiGuanLiActivity;
import cn.minxing.activity.JiHuaShengYuActivity;
import cn.minxing.activity.MinXingCunJieShaoActivity;
import cn.minxing.activity.YiBaoSheBaoActivity;
import cn.minxing.restwebservice.BaoJianService;
import cn.minxing.restwebservice.HeTongGuanLiService;
import cn.minxing.restwebservice.HuJiService;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class YeWuBanLiFragment extends Fragment {

	private LinearLayout ll_hj, ll_yb, ll_js, ll_ht, ll_bj, ll_xw;
	private ImageButton ib_hj, ib_yb, ib_js, ib_ht, ib_bj, ib_xw;
	private Button dianhuazixun;
	private TextView mTime;
	private static final int msgKey1 = 1;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// if (isVisibleToUser) {
		// ZiXunFragment zx = new ZiXunFragment();
		// zx.onCreate(getArguments());
		// } else {
		// // 相当于Fragment的onPause
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater
				.inflate(R.layout.fragment_yewubanli, container, false);
		ExitApplication.getInstance().addActivity(getActivity());
		ll_hj = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_hj);
		ll_yb = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_yb);
		ll_js = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_js);
		ll_ht = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_ht);
		ll_bj = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_bj);
		ll_xw = (LinearLayout) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ll_xw);
		ib_hj = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_hj);
		ib_yb = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_yb);
		ib_js = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_js);
		ib_ht = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_ht);
		ib_bj = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_bj);
		ib_xw = (ImageButton) v
				.findViewById(com.zhumingmin.vmsofminxing.R.id.ib_xw);
		mTime = (TextView) v.findViewById(R.id.mytime);
		new TimeThread().start();
		// dianhuazixun = (Button) v
		// .findViewById(com.zhumingmin.vmsofminxing.R.id.DianHuaZiXun);

		ib_hj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// intent = new Intent(getActivity(), HuJiGuanLi.class);
				intent = new Intent(getActivity(), HuJiService.class);
				startActivity(intent);

			}
		});

		ib_yb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), YiBaoSheBaoActivity.class);
				startActivity(intent);
			}
		});
		ib_js.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), JiHuaShengYuActivity.class);
				startActivity(intent);
			}
		});
		ib_ht.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), HeTongGuanLiService.class);
				startActivity(intent);
			}
		});
		ib_bj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// intent = new Intent(getActivity(), BaoJianGuanLi.class);
				intent = new Intent(getActivity(), BaoJianService.class);
				startActivity(intent);
			}
		});
		ib_xw.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), CatchActivity.class);
				// intent = new Intent(getActivity(), NewsActivity.class);

				// intent = new Intent(getActivity(), NewsService.class);
				startActivity(intent);
			}
		});
		// dianhuazixun.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// final String[] items = getResources().getStringArray(
		// R.array.item);
		// new AlertDialog.Builder(getActivity()).setTitle("请点击选择咨询业务！")
		// .setItems(items, new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog,
		// int which) {
		// new AlertDialog.Builder(getActivity())
		// .setTitle("你选择了:" + items[which])
		// .setMessage("准备接通咨询，请确认")
		// .setPositiveButton(
		// "确认咨询",
		// new android.content.DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(
		// DialogInterface arg0,
		// int arg1) {
		// // TODO Auto-generated
		// // method stub
		// arg0.dismiss();
		// Intent in2 = new Intent();
		// in2.setAction(Intent.ACTION_CALL);
		// if (items.toString() == "户籍咨询") {
		// in2.setData(Uri
		// .parse("tel:18826487090"));
		// startActivity(in2);
		// } else if (items
		// .toString() == "医保咨询") {
		// in2.setData(Uri
		// .parse("tel:18826487090"));
		// startActivity(in2);
		// } else if (items
		// .toString() == "财务咨询") {
		// in2.setData(Uri
		// .parse("tel:18826487090"));
		// startActivity(in2);
		// } else if (items
		// .toString() == "计生咨询") {
		// in2.setData(Uri
		// .parse("tel:18826487090"));
		// startActivity(in2);
		// } else {
		// in2.setData(Uri
		// .parse("tel:18826487090"));
		// startActivity(in2);
		// }
		//
		// }
		// })
		// .setNegativeButton(
		// "下次咨询",
		// new DialogInterface.OnClickListener() {
		//
		// public void onClick(
		// DialogInterface dialog,
		// int which) {
		// // 这里点击取消之后可以进行的操作
		// }
		// }).show();
		// }
		// }).show();
		// }
		// });
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
