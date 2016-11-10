package cn.minxing.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter<T> extends ArrayAdapter<T> {
	public CustomArrayAdapter(Context ctx, T[] objects) {
		super(ctx, android.R.layout.simple_spinner_dropdown_item, objects);
	}

	// 其它构造函数

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		TextView text = (TextView) view.findViewById(android.R.id.text1);
		text.setTextColor(Color.RED);
		AssetManager mgr = getAssets();// 得到AssetManager
		Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");// 根据路径得到Typeface
		text.setTypeface(tf);

		/* } */

		return view;

	}

	private AssetManager getAssets() {
		// TODO Auto-generated method stub
		return null;
	}
}