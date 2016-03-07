package com.itjfr.jfr.utils;


import java.io.IOException;
import java.io.InputStream;

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
	
	public static String loadStringFromAssets(Context context,String fileName){
		try {
			InputStream inputStream = context.getAssets().open(fileName);
			String stream2String = FileTool.inputStream2String(inputStream);
			return stream2String;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
