package kim.uno.base.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

public class DisplayUtil {

	final static float BASE_GUIDE_WIDTH = 720;
	final static float BASE_GUIDE_HEIGHT = 1280;

    public static float getDpi(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getPixelFromDp(Context context, float dp) {
        return (int) (getDpi(context) * dp);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
	
	public static int getWidth(Context context) {
		return getDisplayMetrics(context).widthPixels;
	}

	public static int getHeight(Context context) {
		return getDisplayMetrics(context).heightPixels;
	}
	
	/**
	 * 현재 가로 해상도에 맞춰 리턴
	 * @param px
	 * @return
	 */
	public static float fromW(Context context, float px) {
		return px > 0 ? getWidth(context) * (px / BASE_GUIDE_WIDTH) : 0;
	}
	
	/**
	 * 현재 세로 해상도에 맞춰 리턴
	 * @param px
	 * @return
	 */
	public static float fromH(Context context, float px) {
		return px > 0 ? getHeight(context) * (px / BASE_GUIDE_HEIGHT) : 0;
	}
	
	public static void setSizeFromW(Context context, View v, float w, float h) {
		setSize(v, fromW(context, w), fromW(context, h));
	}
	
	public static void setSizeFromH(Context context, View v, float w, float h) {
		setSize(v, fromH(context, w), fromH(context, h));
	}
	
	public static void setSize(View v, float w, float h) {
		if (w > 0) v.getLayoutParams().width = (int) w;
		if (h > 0) v.getLayoutParams().height = (int) h;
	}
	
	public static void setMarginFromW(Context context, View v, float l, float t, float r, float b) {
		setMargin(v, fromW(context, l), fromW(context, t), fromW(context, r), fromW(context, b));
	}
	
	public static void setMarginFromH(Context context, View v, float l, float t, float r, float b) {
		setMargin(v, fromH(context, l), fromH(context, t), fromH(context, r), fromH(context, b));
	}
	
	public static void setMargin(View v, float l, float t, float r, float b) {
		((MarginLayoutParams) v.getLayoutParams()).leftMargin = (int) l;
		((MarginLayoutParams) v.getLayoutParams()).topMargin = (int) t;
		((MarginLayoutParams) v.getLayoutParams()).rightMargin = (int) r;
		((MarginLayoutParams) v.getLayoutParams()).bottomMargin = (int) b;
	}
}
