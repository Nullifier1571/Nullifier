package com.itjfr.jfr.domain;

import java.util.List;

import com.itjfr.jfr.fragment.FragmentFactory;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * 本类负责在内存中记录用户登录情况，此对象为空则用户没有登录
 * 
 * @author Nullifier
 * 
 */
public class UserLoginInfo {
	private String phoneNumber;
	private String password;
	private String nickName;
	private String sex;
	// 住址 北京#1#海淀#2#西二旗#3 省 ID 市 ID 县 ID
	private String location;
	// 个性签名
	private String sign;
	// 生日 格式1992-11-17
	private String birthday;
	private String score;
	// 个人简介
	private String profile;
	private List<UserTag> lable;
	private String token;
	private String user_id;
	private static UserLoginInfo userLoginInfo;

	private UserLoginInfo() {

	}

	public static UserLoginInfo getUserLoginInfo() {
		if (userLoginInfo == null) {
			userLoginInfo = new UserLoginInfo();
		}
		return userLoginInfo;
	}

	public static void logoutUser() {
		userLoginInfo = null;
		FragmentFactory.cleanCenterRadioGroup();
		FragmentFactory.cleanAllFragment();
	}

	public String getNetPhotoPath(String id) {

		return Environment.getExternalStorageDirectory() + "/user_net_photo"
				+ id + ".png";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalPhoto(String id) {
		return Environment.getExternalStorageDirectory() + "/user_photo" + id
				+ ".png";
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<UserTag> getLable() {
		return lable;
	}

	public void setLable(List<UserTag> lable) {
		this.lable = lable;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
