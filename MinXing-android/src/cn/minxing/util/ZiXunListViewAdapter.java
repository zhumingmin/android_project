package cn.minxing.util;

import java.util.ArrayList;
import java.util.List;

import com.zhumingmin.vmsofminxing.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ZiXunListViewAdapter extends ArrayAdapter<ZiXun> {
	private int resourceId;

	public ZiXunListViewAdapter(Context context, int textViewResourceId,
			List<ZiXun> object) {
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ZiXun zixun = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView biaoti = (TextView) view.findViewById(R.id.zx_biaoti);
		TextView laiyuan = (TextView) view.findViewById(R.id.zx_laiyuan);
		TextView shijian = (TextView) view.findViewById(R.id.zx_shijian);
		TextView yuedu = (TextView) view.findViewById(R.id.zx_yuedu);
		ImageView tupian = (ImageView) view.findViewById(R.id.zx_tupian);
		biaoti.setText(zixun.getBiaoTi());
		laiyuan.setText(zixun.getLaiYuan());
		shijian.setText(zixun.getShiJian());
		yuedu.setText(zixun.getYueDu());
		VolleyLoadPicture vlp = new VolleyLoadPicture(getContext(), tupian);
		vlp.getmImageLoader().get(zixun.getTuPian(), vlp.getOne_listener());
		return view;
	}

}
