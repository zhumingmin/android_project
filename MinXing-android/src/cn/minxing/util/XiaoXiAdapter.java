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

public class XiaoXiAdapter extends ArrayAdapter<XiaoXi> {
	private int resourceId;

	public XiaoXiAdapter(Context context, int textViewResourceId,
			List<XiaoXi> object) {
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		XiaoXi xiaoxi = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView biaoti = (TextView) view.findViewById(R.id.xx_biaoti);
		TextView shijian = (TextView) view.findViewById(R.id.xx_shijian);
		TextView zhaiyao = (TextView) view.findViewById(R.id.xx_zhaiyao);

		biaoti.setText(xiaoxi.getBiaoTi());
		shijian.setText(xiaoxi.getShiJian());
		zhaiyao.setText(xiaoxi.getZhaiYao());

		return view;
	}

}
