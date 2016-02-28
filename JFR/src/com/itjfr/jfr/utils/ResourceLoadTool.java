package com.itjfr.jfr.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;

public class ResourceLoadTool {
	public static int loadColor(Context context, int colorId) {
		return context.getResources().getColor(colorId);
	}
	
	public static float loadTextSize(Context context, int dimenId) {
		return context.getResources().getDimension(dimenId);
	}
	
	public static Drawable loadImage(Context context, int imageId) {
		return context.getResources().getDrawable(imageId);
	}
}
