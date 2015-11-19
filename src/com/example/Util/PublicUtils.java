package com.example.Util;

import android.content.Context;
import android.util.DisplayMetrics;

public class PublicUtils {

	static private float density = -1;
	static private int screenWidth = -1;
	static private int screenHeight = -1;

	public static void init(Context context) {
		if (density == -1) {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			density = metrics.density;
			if (metrics.widthPixels < metrics.heightPixels) {
				screenWidth = metrics.widthPixels;
				screenHeight = metrics.heightPixels;
			} else { // 部分机器使用application的context宽高反转
				screenHeight = metrics.widthPixels;
				screenWidth = metrics.heightPixels;
			}
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static float getDensity() {
		return density;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static int dpToPx(float dp) {
		return Math.round(dp * getDensity());
	}

	public static int PxToDp(float px) {
		return Math.round(px / getDensity());
	}
}
