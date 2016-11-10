package cn.minxing.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

public class Common {

	/**
	 * 请忽略
	 * 
	 * @param imageView
	 * @return String
	 */
	public static byte[] getImageByte(Bitmap bitmap) {
		// imageView.setDrawingCacheEnabled(true);
		// Bitmap bitMap = imageView.getDrawingCache();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		return imageBytes;
	}
}
