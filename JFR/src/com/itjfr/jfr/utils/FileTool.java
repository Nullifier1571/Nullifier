package com.itjfr.jfr.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.itjfr.jfr.domain.PhoneImage;

public class FileTool {
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	public static boolean Bitmap2File(final Bitmap bitmap, final String fileName) {

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				if (file.createNewFile()) {
					LogUtils.e("文件为空创建成功！！");
				} else {
					LogUtils.e("文件为空创建失败！！");
				}
			}
			// 将截图保存在SD卡根目录的test.png图像文件中
			FileOutputStream fos = new FileOutputStream(file);
			// 将Bitmap对象中的图像数据压缩成png格式的图像数据，并将这些数据保存在test.png文件中
			bitmap.compress(CompressFormat.PNG, 100, fos);
			// 关闭文件输出流
			fos.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("文件保存失败！！");
			return false;
		}
	}

	public static File inputstreamtofile(InputStream ins) {
		File file = null;
		try {
			file = new File(Environment.getExternalStorageDirectory() + "/"
					+ "avater.png");
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 得到手机的视频 安卓系统：在开启完成后或者sdcard插入完成可用的时候 媒体扫描器 就会扫描视频
	 */
	public static void getAllAudio(final Context context, final Handler handler) {
		new Thread() {
			public void run() {
				List<PhoneImage> phoneLists = new ArrayList<PhoneImage>();
				// 加载视频
				ContentResolver resolver = context.getContentResolver();
				Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				String[] projection = { MediaStore.Images.Media.DISPLAY_NAME,
						MediaStore.Images.Media.SIZE,
						MediaStore.Images.Media.DATA };
				Cursor cursor = resolver.query(uri, projection, null, null,
						null);
				while (cursor.moveToNext()) {
					// AudioItem videoItem = new AudioItem();
					PhoneImage phoneImage = new PhoneImage();
					String title = cursor.getString(0);
					String size = cursor.getString(1);
					String path = cursor.getString(2);
					phoneImage.setName(title);
					phoneImage.setSize(size);
					phoneImage.setPath(path);
					phoneLists.add(phoneImage);
					// String data = cursor.getString(3);
					/*
					 * videoItem.setTitle(title); videoItem.setSize(size);
					 * videoItem.setDuration(duration); videoItem.setData(data);
					 * audioLists.add(videoItem);
					 */
				}
				cursor.close();
				Message obtain = Message.obtain();
				obtain.obj = phoneLists;
				handler.sendMessage(obtain);
			};
		}.start();
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
