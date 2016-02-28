package com.itjfr.jfr.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePrefaceTool {

	/**
	 * 本方法将多条数据写入SharePreface中
	 * 
	 * @param context
	 *            上下文对象
	 * @param titles
	 *            保存的名字
	 * @param contexs
	 *            保存的内容
	 * @return 写入是否成功
	 */
	public static boolean writein(Context context, String[] titles,String[] contexs) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		if (titles.length != contexs.length) {
			return false;
		} else {
			for (int x = 0; x < titles.length; x++) {

				edit.putString(titles[x], contexs[x]);
			}
			edit.commit();
			return true;
		}
	}

	/**
	 * 本方法将单条数据写入SharePreface中
	 * 
	 * @param context
	 *            上下文对象
	 * @param titles
	 *            保存的名字
	 * @param contexs
	 *            保存的内容
	 * @return 写入是否成功
	 */
	public static boolean writein(Context context, String titles, String contexs) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(titles, contexs);
		edit.commit();
		return true;
	}
	
	
	public static boolean writein(Context context, String titles, boolean contexs) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean(titles, contexs);
		edit.commit();
		return true;
	}
	
	public static boolean readboolean(Context context, String titles) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);

		return sp.getBoolean(titles, true);

	}

	/**
	 * 本方法实现从SharedPreferences中读取多条数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param titles
	 *            要读取的名字
	 * @return 数据字符串数组没有返回空字符串
	 */
	public static String[] readout(Context context, String[] titles) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		String results[] = new String[titles.length];
		for (int x = 0; x < titles.length; x++) {

			results[x] = sp.getString(titles[x], "");
		}

		return results;

	}

	/**
	 * 本方法实现从SharedPreferences中读取单条数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param titles
	 *            要读取的名字
	 * @return 数据字符串数组没有返回空字符串
	 */
	public static String readout(Context context, String titles) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);

		return sp.getString(titles, "");

	}
}
