package com.itjfr.jfr.utils;

import java.io.InputStream;
import java.security.MessageDigest;

public class MessageSecurityTool {
	/**
	 * 适用于上G大的文件
	 */
	public static String getFileSha1(InputStream inputStram) {
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("SHA-1");
			byte[] buffer = new byte[1024 * 1024 * 10];
			int len = 0;

			while ((len = inputStram.read(buffer)) > 0) {
				// 该对象通过使用 update（）方法处理数据
				messagedigest.update(buffer, 0, len);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byte2hexString(messagedigest.digest()); 
	}
	
	public static String byte2hexString(byte[] by){
		return new String(by);
	}
}
