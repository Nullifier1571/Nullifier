package com.itjfr.jfr.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itjfr.jfr.R;
import com.itjfr.jfr.adapter.UserLableAdapter;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.domain.UserTag;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PersonDetailActivity extends Activity implements RequestNetListener {
	// 头像组件
	@ViewInject(R.id.personal_profile_iv_photo)
	private ImageView personal_profile_iv_photo;
	// 头像设置
	@ViewInject(R.id.rl_1)
	private RelativeLayout rl_1;
	// 昵称
	@ViewInject(R.id.rl_2)
	private RelativeLayout rl_2;
	// 性别
	@ViewInject(R.id.rl_3)
	private RelativeLayout rl_3;
	// 生日
	@ViewInject(R.id.rl_4)
	private RelativeLayout rl_4;
	// 所在地
	@ViewInject(R.id.rl_5)
	private RelativeLayout rl_5;
	// 个人简介
	@ViewInject(R.id.rl_6)
	private RelativeLayout rl_6;
	//个性标签gridView
	@ViewInject(R.id.personal_gv_lable)
	private GridView personal_gv_lable;
	
	private HttpTools httpTools;
	private UserLableAdapter userLableAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal_detail);
		ViewUtils.inject(this);

		initData();
	}

	private void initData() {
		// 从内存中拿到用户对象
		UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
		//从内存中拿到头像，这个理论上来说已经拿到了
		if (userLoginInfo.getPhoto() != null) {
			// 设置头像ImageView
			personal_profile_iv_photo.setImageBitmap(userLoginInfo.getPhoto());
		}
		
		//以lable为标记是否请求过detail接口 如果头像为空或者lable为空都去请求网路
		if(userLoginInfo.getLable() == null||userLoginInfo.getPhoto() == null){

			// 如果内存中没有个人信息则初始化网络请求工具从网络中下载
			httpTools = HttpTools.getHttpToolsInstance();
			httpTools.setRequestNetListener(this);

			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				httpTools.doPost(requestArgs, Config.USER_PERSON_DETAIL, 0);
			}
		//不为空则内存中有数据则直接加载(加载标签和头像)
		}else {
			setLable();
		}
	}

	

	@Override
	public void getResultData(boolean isOk, String result, int Tag) {
		if (Tag == 0 && isOk) {
			try {
				// 获取用户个人页面的数据
				JSONObject dataResult = new JSONObject(result);
				String birthDay = dataResult.getString("birthDay");
				String birthMonth = dataResult.getString("birthMonth");
				String birthYear = dataResult.getString("birthYear");
				String cityId = dataResult.getString("cityId");
				String cityName = dataResult.getString("cityName");
				String districtId = dataResult.getString("districtId");
				String districtName = dataResult.getString("districtName");
				String headImg = dataResult.getString("headImg");
				String profile = dataResult.getString("profile");
				String provinceId = dataResult.getString("provinceId");
				String provinceName = dataResult.getString("provinceName");
				String sex = dataResult.getString("sex");
				
				ArrayList<UserTag> userTagList = new ArrayList<UserTag>();
				
				JSONArray tags = dataResult.getJSONArray("tags");
				for (int i = 0; i < tags.length(); i++) {
					JSONObject tag = tags.getJSONObject(i);
					UserTag userTag = new UserTag();
					userTag.setId(tag.getString("id"));
					userTag.setName(tag.getString("name"));
					userTag.setIsChecked("isCheck");
					userTagList.add(userTag);
				}

				// 设置进内存对象中
				UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
				userLoginInfo.setBirthday(birthYear+"-"+"-"+birthMonth+"-"+birthDay);
				userLoginInfo.setLocation(provinceName+"#"+cityName+districtName);
				//TODO 暂时设置为空
				userLoginInfo.setPhoto(null);
				userLoginInfo.setSex(sex);
				userLoginInfo.setLable(userTagList);
				setLable();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 加载标签和头像
	 */
	private void setLable() {
		UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
		personal_profile_iv_photo.setImageBitmap(userLoginInfo.getPhoto());
		if(userLableAdapter==null){
			userLableAdapter = new UserLableAdapter(userLoginInfo.getLable(),this);
		}else{
			userLableAdapter.notifyDataSetChanged();
		}
		
		personal_gv_lable.setAdapter(userLableAdapter);
	}
}
