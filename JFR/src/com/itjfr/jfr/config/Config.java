package com.itjfr.jfr.config;

/**
 * 本类为网络请求地址配置文件
 * 
 * @author Nullifier
 * 
 */
public class Config {
	public final static String TestHostAddress = "http://192.168.2.99";
	public final static String HostAddress = "http://api.ruifi.com";

	// 新用户注册获取验证码
	public final static String USER_REGIST_GET_TOKEN = TestHostAddress+ "/regist/code";
	// 新用户注册
	public final static String USER_REGIST = TestHostAddress + "/regist";
	// 用户登录
	public final static String USER_LOGIN = TestHostAddress + "/login";
	// 用户中心
	public final static String USER_PERSONAL = TestHostAddress+ "/personalcenter/index";
	// 个人信息详情
	public final static String USER_PERSON_DETAIL = TestHostAddress+ "/personalcenter/detail";

}
