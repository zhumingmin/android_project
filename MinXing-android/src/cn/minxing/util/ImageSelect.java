package cn.minxing.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class ImageSelect extends GridView {
	private Context context;

	private List<Map<String, Bitmap>> data_list;
	private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
	private SimpleAdapter sim_adapter;
	// 图片封装为一个数组
	private int[] icon = { R.drawable.camera, R.drawable.gridview_addpic };

	public interface OnImageSelectClickListener {
		public void onClick(int id);
	}

	private OnImageSelectClickListener listener = null;

	public void setOnImageSelectClickListener(
			OnImageSelectClickListener listener) {
		this.listener = listener;
	}

	public ImageSelect(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ImageSelect(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public void init() {
		// 新建List
		data_list = new ArrayList<Map<String, Bitmap>>();
		// 获取数据
		getData();
		// 新建适配器
		updateImage();

		setBackgroundResource(R.drawable.buttonshape3);
		setNumColumns(4);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		setGravity(Gravity.CENTER);
	}

	private List<Map<String, Bitmap>> getData() {
		// cion和iconName的长度是相同的，这里任选其一都可以
		Bitmap bmp;
		for (int i = 0; i < icon.length; i++) {
			Map<String, Bitmap> map = new HashMap<String, Bitmap>();
			bmp = BitmapFactory.decodeResource(getResources(), icon[i]);
			map.put("image", bmp);
			data_list.add(map);
		}
		return data_list;
	}

	public void addImageByRes(int resid) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resid);
		Map<String, Bitmap> map = new HashMap<String, Bitmap>();
		map.put("image", bmp);
		data_list.add(1, map);
		updateImage();
	}

	private void updateImage() {
		String[] from = { "image" };
		int[] to = { R.id.image };
		sim_adapter = new SimpleAdapter(context, data_list,
				R.layout.list_imageselect, from, to);
		sim_adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		// 配置适配器
		this.setAdapter(sim_adapter);
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// if(listener!=null)listener.onClick(position);
				if (position == 0) {
					camera();
				} else if (position == data_list.size() - 1) {
					gallery();
				} else {
					if (listener != null) {
						listener.onClick(position);
					}
				}
			}
		});
		setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 0) {
				} else if (position == data_list.size() - 1) {
				} else {
					deleteImageByBitmap(position);
				}
				return true;// 消费事件，不传递
			}
		});
	}

	public void addImageByBitmap(Bitmap bitmap) {
		if (images.size() >= 6) {
			Toast.makeText(context, "上传图片不能超过6张", 0).show();
			return;
		}
		images.add(bitmap);
		Map<String, Bitmap> map = new HashMap<String, Bitmap>();
		map.put("image", bitmap);
		data_list.add(1, map);
		updateImage();
	}

	public void deleteImageByBitmap(int index) {
		if (images.size() >= 6) {
			Toast.makeText(context, "上传图片不能超过6张", 0).show();
			return;
		}
		images.remove(index - 1);
		data_list.remove(index);
		updateImage();
	}

	public ArrayList<Bitmap> getImages() {
		return images;
	}

	public final static int PHOTO_REQUEST_GALLERY = 1;
	public final static int PHOTO_REQUEST_CAREMA = 2;
	public final static int PHOTO_REQUEST_CUT = 3;
	public File tempFile;
	public final static String PHOTO_FILE_NAME = "photo";

	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
		((Activity) context).startActivityForResult(intent,
				PHOTO_REQUEST_GALLERY);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		// 激活相机
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		// if (hasSdcard()) {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				PHOTO_FILE_NAME);
		// 从文件中创建uri
		Uri uri = Uri.fromFile(tempFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		// }
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
		((Activity) context).startActivityForResult(intent,
				PHOTO_REQUEST_CAREMA);
	}

	/*
	 * 剪切图片
	 */
	public void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 240);
		intent.putExtra("outputY", 240);

		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		((Activity) context).startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	/*
	 * 这里注释的代码应放到相应的activity里面
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { if (requestCode ==
	 * ImageSelect.PHOTO_REQUEST_GALLERY) { // 从相册返回的数据 if (data != null) { //
	 * 得到图片的全路径 Uri uri = data.getData(); imageSelect.crop(uri); } } else if
	 * (requestCode == ImageSelect.PHOTO_REQUEST_CAREMA) { // 从相机返回的数据 // if
	 * (hasSdcard()) { imageSelect.crop(Uri.fromFile(imageSelect.tempFile)); //
	 * } else { // Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！",
	 * 0).show(); // }
	 * 
	 * } else if (requestCode == ImageSelect.PHOTO_REQUEST_CUT) { // 从剪切图片返回的数据
	 * if (data != null) { Bitmap bitmap = data.getParcelableExtra("data");
	 * imageSelect.addImageByBitmap(bitmap); } try { // 将临时文件删除 if
	 * (imageSelect.tempFile!=null) { imageSelect.tempFile.delete(); } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * super.onActivityResult(requestCode, resultCode, data); }
	 */
}
