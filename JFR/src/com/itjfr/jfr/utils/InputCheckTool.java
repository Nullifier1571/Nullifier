package com.itjfr.jfr.utils;

import android.text.TextUtils;
/**
 * 本类负责检测各种输入文本的合法性
 * @author Nullifier
 *
 */
public class InputCheckTool {
	/**
	 * 检查是否是手机号码
	 * @param string 待检测字符串
	 * @return true合法 false为非法
	 */
	public static boolean isPhoneNumber(String string) {
		if (TextUtils.isEmpty(string)||string.length()!=11) {
			return false;
		}
		if(string.matches("")){
			
		}
		return true;
	}
	/**
	 * 检测是否为合法的验证码
	 * @param string 待检测字符串
	 * @return true合法 false为非法
	 */
	public static boolean isToken(String string) {
		if (TextUtils.isEmpty(string)||string.length()!=5) {
			return false;
		}
		if(string.matches("")){
			
		}
		return true;
	}
	
	/**
	 * 检测是否为合法的密码
	 * @param string 待检测字符串
	 * @return true合法 false为非法
	 */
	public static boolean isPassword(String string) {
		if (TextUtils.isEmpty(string)) {
			return false;
		}
		if(string.matches("")){
			
		}
		return true;
	}
}
