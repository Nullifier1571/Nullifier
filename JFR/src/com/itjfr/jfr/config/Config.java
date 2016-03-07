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
	// 上传用户头像
	public final static String USER_UP_AVATER = TestHostAddress+ "/userinfo/avatar";
	// 上传用户昵称
	public final static String USER_UP_NICKNAME = TestHostAddress+ "/userinfo/nickname";
	// 上传用户性别
	public final static String USER_UP_SEX = TestHostAddress+ "/userinfo/sex";
	// 上传用户生日
	public final static String USER_UP_BIRTHDAY = TestHostAddress+ "/userinfo/birth";
	// 上传用户住址
	public final static String USER_UP_ADDRESS = TestHostAddress+ "/userinfo/place";
	// 上传用户个人简介
	public final static String USER_UP_PROFILES = TestHostAddress+ "/userinfo/resume";
	//上传个人鉴别
	public final static String USER_UP_SIGNWORD = TestHostAddress+ "/userinfo/autograph";
	// 首页新闻
	public final static String HOME_NEWS = TestHostAddress + "/news/list";
	// 新闻详情
	public final static String NEWS_DETAIL = TestHostAddress + "/news/view";

}
