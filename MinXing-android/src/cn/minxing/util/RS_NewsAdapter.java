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
import android.widget.ListView;
import android.widget.TextView;

public class RS_NewsAdapter extends ArrayAdapter<RS_News> {
	private int resourceId;

	public RS_NewsAdapter(Context context, int textViewResourceId,
			List<RS_News> object) {
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RS_News news = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView biaoti = (TextView) view.findViewById(R.id.biaoti);
		TextView leibie = (TextView) view.findViewById(R.id.leibie);
		TextView fabushijian = (TextView) view.findViewById(R.id.fabushijian);
		TextView yueduliang2 = (TextView) view.findViewById(R.id.yueduliang2);
		biaoti.setText(news.getBiaoTi());
		leibie.setText(news.getLeiBie());
		yueduliang2.setText(news.getYueDuLiang2());
		long sysTime = System.currentTimeMillis();
		CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日", sysTime);
		fabushijian.setText(sysTimeStr);
		return view;
	}

}
