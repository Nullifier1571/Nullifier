package com.itjfr.jfr.domain;

import java.util.List;

import android.graphics.Bitmap;

/**
 * 本类负责在内存中记录用户登录情况，此对象为空则用户没有登录
 * @author Nullifier
 *
 */
public class UserLoginInfo {
	private String phoneNumber;
	private String password;
	private Bitmap photo;
	private String nickName;
	private String sex;
	//住址
	private String location;
	//个性签名
	private String sign;
	//生日 格式1992-11-17
	private String birthday;
	private String score;
	private List<UserTag> lable;
	
	private static UserLoginInfo userLoginInfo;
	private UserLoginInfo(){
		
	}
	
	public static UserLoginInfo getUserLoginInfo(){
		if(userLoginInfo==null){
			userLoginInfo = new UserLoginInfo();
		}
		return userLoginInfo;
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

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
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
	
	

}
