package cn.minxing.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author yangyu
 *	���������������ڲ���������Ʊ����ͼ�꣩
 */
public class RS_ActionItem {
	//����ͼƬ����
	public Drawable mDrawable;
	//�����ı�����
	public CharSequence mTitle;
	
	public RS_ActionItem(Drawable drawable, CharSequence title){
		this.mDrawable = drawable;
		this.mTitle = title;
	}
	
	public RS_ActionItem(Context context, int titleId, int drawableId){
		this.mTitle = context.getResources().getText(titleId);
		this.mDrawable = context.getResources().getDrawable(drawableId);
	}
	
	public RS_ActionItem(Context context, CharSequence title, int drawableId) {
		this.mTitle = title;
		this.mDrawable = context.getResources().getDrawable(drawableId);
	}
}
