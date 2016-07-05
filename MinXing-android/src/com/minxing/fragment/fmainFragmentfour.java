package com.minxing.fragment;

import com.minxing.PushMessage.ExitApplication;
import com.minxing.activity.ChatRobtActivity;
import com.minxing.activity.MinXingSheQuActivity;
import com.minxing.activity.MinYiZhengJiActivity;
import com.minxing.activity.ReadTagActivity;
import com.minxing.activity.RssActivity;
import com.minxing.activity.SZ_GRXXActivity;
import com.minxing.activity.SZ_SheZhiActivity;
import com.minxing.activity.SerachView;
import com.minxing.activity.SheZhiActivity;
import com.minxing.activity.TianQiChaXunActivity;
import com.minxing.activity.TongXunLuActivity;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.sdkmanager.ShareSDKManager;
import com.zhumingmin.vmsofminxing.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.comm.ui.fragments.CommunityMainFragment;

public class fmainFragmentfour extends Fragment {

	private TableRow tr_gerenxinxi, tr_toupiao, tr_shequ, tr_tongxun,
			tr_xinxichaxun, tr_tongxunlu, tr_xianliao, tr_shezhi, tr_rss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_shezhi, container, false);
		ExitApplication.getInstance().addActivity(getActivity());
		tr_gerenxinxi = (TableRow) v.findViewById(R.id.tr_gerenxinxi);
		tr_toupiao = (TableRow) v.findViewById(R.id.tr_toupiao);
		tr_shequ = (TableRow) v.findViewById(R.id.tr_shequ);
		tr_tongxun = (TableRow) v.findViewById(R.id.tr_tongxun);
		tr_xinxichaxun = (TableRow) v.findViewById(R.id.tr_xinxichaxun);
		tr_tongxunlu = (TableRow) v.findViewById(R.id.tr_tongxunlu);
		tr_xianliao = (TableRow) v.findViewById(R.id.tr_xianliao);
		tr_shezhi = (TableRow) v.findViewById(R.id.tr_shezhi);
		tr_rss = (TableRow) v.findViewById(R.id.tr_rss);
		tr_toupiao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), MinYiZhengJiActivity.class);
				startActivity(intent);
			}
		});
		tr_tongxunlu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), TongXunLuActivity.class);
				startActivity(intent);
			}
		});

		tr_shequ.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				DisplayToast("稍后开通！");
				// Intent intent = new Intent();
				// intent = new Intent(getActivity(), MinXingSheQu.class);
				// startActivity(intent);
				CommunitySDK mCommSDK = CommunityFactory
						.getCommSDK(getActivity());
				// 打开微社区的接口, 参数1为Context类型
				mCommSDK.openCommunity(getActivity());

			}

		});
		tr_tongxun.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				DisplayToast("稍后开通！");
			}

		});

		tr_xinxichaxun.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), ReadTagActivity.class);
				startActivity(intent);
			}

		});
		tr_xianliao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), ChatRobtActivity.class);
				startActivity(intent);
			}
		});
		tr_rss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), RssActivity.class);
				startActivity(intent);
			}
		});
		tr_shezhi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), SZ_SheZhiActivity.class);
				startActivity(intent);
			}
		});
		tr_gerenxinxi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent = new Intent(getActivity(), SZ_GRXXActivity.class);
				startActivity(intent);
			}
		});

		return v;
	}

	protected void DisplayToast(String string) {

		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
	}

}
