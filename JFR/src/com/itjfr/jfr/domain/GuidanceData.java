package com.itjfr.jfr.domain;

public class GuidanceData {
	//商家的名字
	private String title;
	//点击后链接到的url
	private String url;
	//图片的url地址
	private String imageUrl;
	//图片在本地的资源id
	private int imageResource;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getImageResource() {
		return imageResource;
	}
	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}
	
}
