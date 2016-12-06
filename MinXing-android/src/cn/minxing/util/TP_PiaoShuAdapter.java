package cn.minxing.util;

import java.util.List;
import com.zhumingmin.vmsofminxing.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

public class TP_PiaoShuAdapter extends ArrayAdapter<TP_PiaoShu> {
	private int resourceId;

	public TP_PiaoShuAdapter(Context context, int textViewResourceId,
			List<TP_PiaoShu> object) {
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TP_PiaoShu tp_piaoshu = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView canxuanren = (TextView) view.findViewById(R.id.tp_canxuanren);
		TextView canxuanzhiwu = (TextView) view
				.findViewById(R.id.tp_canxuanzhiwu);
		TextView gerenshiji = (TextView) view.findViewById(R.id.tp_gerenshiji);
		TextView piaoshu = (TextView) view.findViewById(R.id.tp_piaoshu);
		canxuanren.setText(tp_piaoshu.getCanXuanRen());
		canxuanzhiwu.setText(tp_piaoshu.getCanXuanZhiWu());
		gerenshiji.setText(tp_piaoshu.getGeRenShiJi());
		piaoshu.setText(tp_piaoshu.getPiaoShu());
		return view;
	}

}
