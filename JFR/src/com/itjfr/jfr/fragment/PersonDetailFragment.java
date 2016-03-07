package com.itjfr.jfr.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.itjfr.jfr.R;
import com.itjfr.jfr.activity.BirthDayPickerActivity;
import com.itjfr.jfr.activity.InputActivity;
import com.itjfr.jfr.activity.LocationPickerActivity;
import com.itjfr.jfr.activity.SexPickerActivity;
import com.itjfr.jfr.adapter.ListPopupWindowAdapter;
import com.itjfr.jfr.adapter.SexWheelAdapter;
import com.itjfr.jfr.adapter.UserLableAdapter;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.domain.UserTag;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.itjfr.jfr.listener.OnWheelChangedListener;
import com.itjfr.jfr.utils.FileTool;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.itjfr.jfr.utils.UITool;
import com.itjfr.jfr.view.WheelView;
import com.lidroid.xutils.BitmapUtils;

public class PersonDetailFragment extends BaseFragment implements
		RequestNetListener, OnClickListener, OnItemClickListener {
	private ImageView personal_profile_iv_photo;
	// 头像设置
	private RelativeLayout rl_1;
	// 昵称
	private RelativeLayout rl_2;
	// 性别
	private RelativeLayout rl_3;
	// 生日
	private RelativeLayout rl_4;
	// 所在地
	private RelativeLayout rl_5;
	// 个人简介
	private RelativeLayout rl_6;
	// 个性标签gridView
	private GridView personal_gv_lable;
	private ImageView menu_back;

	private HttpTools httpTools;
	private UserLableAdapter userLableAdapter;
	private ListPopupWindow listPopupWindow;
	private ArrayList<String> photoGetWayList;
	private ListPopupWindowAdapter listPopupWindowAdapter;

	private boolean isShowPopu = false;
	private SexWheelAdapter sexWheelAdapter;
	private SexWheelListener sexWheelListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_personal_detail, null);
		// 初始化组件
		initView(view);
		initData();

		return view;
	}

	private void initData() {
		LogUtils.e("PersonDetail initData()方法执行");
		// 从内存中拿到用户对象
		UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
		// 从内存中拿到头像，这个理论上来说已经拿到了
		if (userLoginInfo.getLocalPhoto(userLoginInfo.getPhoneNumber()) != null) {
			// 设置头像ImageView
			personal_profile_iv_photo.setImageBitmap(BitmapFactory
					.decodeFile(userLoginInfo.getLocalPhoto(userLoginInfo
							.getPhoneNumber())));

		}

		// 以lable为标记是否请求过detail接口 如果lable为空都去请求网路
		if (userLoginInfo.getLable() == null) {

			// 如果内存中没有个人信息则初始化网络请求工具从网络中下载
			httpTools = HttpTools.getHttpToolsInstance();
			httpTools.setRequestNetListener(this);

			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				httpTools.doPost(requestArgs, Config.USER_PERSON_DETAIL, 0);
			}
			// 不为空则内存中有数据则直接加载(加载标签和头像)
		} else {
			setLable();
		}
	}

	private void initView(View view) {
		menu_back = (ImageView) view.findViewById(R.id.menu_back);
		menu_back.setVisibility(View.VISIBLE);
		menu_back.setOnClickListener(this);
		personal_profile_iv_photo = (ImageView) view
				.findViewById(R.id.personal_profile_iv_photo);

		rl_1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		rl_3 = (RelativeLayout) view.findViewById(R.id.rl_3);
		rl_4 = (RelativeLayout) view.findViewById(R.id.rl_4);
		rl_5 = (RelativeLayout) view.findViewById(R.id.rl_5);
		rl_6 = (RelativeLayout) view.findViewById(R.id.rl_6);

		rl_1.setOnClickListener(this);
		rl_2.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		rl_4.setOnClickListener(this);
		rl_5.setOnClickListener(this);
		rl_6.setOnClickListener(this);
		personal_profile_iv_photo.setOnClickListener(this);
		personal_gv_lable = (GridView) view
				.findViewById(R.id.personal_gv_lable);

		listPopupWindow = new ListPopupWindow(getActivity());
		photoGetWayList = new ArrayList<String>();
		photoGetWayList.add("从相册中选取");
		photoGetWayList.add("拍照");
		listPopupWindowAdapter = new ListPopupWindowAdapter(photoGetWayList,
				getActivity());
		listPopupWindow.setAdapter(listPopupWindowAdapter);
		listPopupWindow.setAnchorView(personal_profile_iv_photo);
		listPopupWindow.setWidth(UITool.dip2px(getActivity(), 140));
		listPopupWindow.setHeight(-2);
		listPopupWindow.setOnItemClickListener(this);
	}

	@Override
	public void getResultData(boolean isOk, int errorCode, String result,
			int Tag) {
		switch (Tag) {
		case 0:
			if (isOk) {
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

					// bitmapUtils.display(personal_profile_iv_photo, headImg);

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
					UserLoginInfo userLoginInfo = UserLoginInfo
							.getUserLoginInfo();
					userLoginInfo.setBirthday(birthYear + "-" + "-"
							+ birthMonth + "-" + birthDay);
					userLoginInfo.setLocation(provinceName + "#" + provinceId
							+ "#" + cityName + "#" + cityId + "#"
							+ districtName + "#" + districtId);
					userLoginInfo.setSex(sex);
					userLoginInfo.setProfile(profile);
					// TODO 暂时设置为空
					// 头像如果从网络获取那么就直接更新到本地文件，这个字段只存储在头像在本地的文件路径
					// userLoginInfo.setPhoto(null);

					String[] strings = SharePrefaceTool.readout(getActivity(),
							new String[] { "birthday", "sex", "location",
									"profile" });
					// 如果sp中没有就把网络请求到的些进去
					if (TextUtils.isEmpty(strings[0])) {
						modifyBirthday(userLoginInfo.getBirthday());
					} else {
						// 否则就是本地与网络不同步就上传
						modifyBirthday(strings[0]);
					}

					if (TextUtils.isEmpty(strings[1])) {
						modifySex(userLoginInfo.getSex());
					} else {
						modifySex(strings[1]);
					}

					if (TextUtils.isEmpty(strings[2])) {
						modifyLocation(userLoginInfo.getLocation());
					} else {
						modifyLocation(strings[2]);
					}

					if (TextUtils.isEmpty(strings[3])) {
						modifyProfile(userLoginInfo.getProfile());
					} else {
						modifyProfile(strings[3]);
					}
					userLoginInfo.setSex(SharePrefaceTool.readout(
							getActivity(), "sex"));
					userLoginInfo.setProfile(SharePrefaceTool.readout(
							getActivity(), "profile"));
					userLoginInfo.setLocation(SharePrefaceTool.readout(
							getActivity(), "location"));
					userLoginInfo.setBirthday(SharePrefaceTool.readout(
							getActivity(), "birthday"));
					userLoginInfo.setLable(userTagList);

					setLable();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			break;
		// 上传用户头像
		case 1:
			if (isOk) {
				LogUtils.e("头像上传成功");
			}
			break;
		// 上传用户昵称
		case 2:
			if (isOk) {
				LogUtils.e("昵称上传成功");
			}
			break;

		// 上传用户性别
		case 3:
			if (isOk) {
				LogUtils.e("性别上传成功");
			}
			break;
		// 上传用户生日
		case 4:
			if (isOk) {
				LogUtils.e("生日上传成功");
			}
			break;
		// 上传用户住址
		case 5:
			if (isOk) {
				LogUtils.e("住址上传成功");
			}
			break;
		// 上传用户简介
		case 6:
			if (isOk) {
				LogUtils.e("简介上传成功");
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 加载标签
	 */
	private void setLable() {
		UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
		if (userLableAdapter == null) {
			userLableAdapter = new UserLableAdapter(userLoginInfo.getLable(),
					getActivity());
			personal_gv_lable.setAdapter(userLableAdapter);
		} else {
			userLableAdapter.refresh(null);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回按钮
		case R.id.menu_back:
			switchPage(FragmentFactory.getFragment(FragmentType.ME));
			break;
		// 选择头像
		case R.id.rl_1:
			if (!isShowPopu) {
				isShowPopu = true;
				listPopupWindow.show();
			} else {
				isShowPopu = false;
				listPopupWindow.dismiss();
			}

			break;
		// 修改昵称
		case R.id.rl_2:
			Intent nickIntent = new Intent(getActivity(), InputActivity.class);
			nickIntent.putExtra("type", 0);
			startActivityForResult(nickIntent, 2);
			break;
		// 修改性别
		case R.id.rl_3:
			// startActivityForResult(new
			// Intent(getActivity(),SexPickerActivity.class), 3);
			showSexPicker();
			break;
		// 修改生日
		case R.id.rl_4:
			// startActivityForResult(new
			// Intent(getActivity(),BirthDayPickerActivity.class), 4);
			showDatePicker();
			break;
		// 修改所在地
		case R.id.rl_5:
			startActivityForResult(new Intent(getActivity(),
					LocationPickerActivity.class), 5);
			break;
		// 修改个人简介
		case R.id.rl_6:
			Intent profileIntent = new Intent(getActivity(),
					InputActivity.class);
			profileIntent.putExtra("type", 1);
			startActivityForResult(profileIntent, 6);
			break;
		// 点击了头像
		case R.id.personal_profile_iv_photo:
			LogUtils.e("点击了头像");
			PopupWindow popupWindow = new PopupWindow();
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageDrawable(personal_profile_iv_photo.getDrawable());
			popupWindow.setContentView(imageView);
			popupWindow.showAsDropDown(rl_1);
			break;
		default:
			break;
		}

	}

	private void showSexPicker() {
		// 通过自定义控件AlertDialog实现
		AlertDialog.Builder sexBuilder = new AlertDialog.Builder(getActivity());
		View view = View
				.inflate(getActivity(), R.layout.dialog_sexpicker, null);

		WheelView wv_sex = (WheelView) view.findViewById(R.id.sex_picker);
		sexWheelAdapter = new SexWheelAdapter(getActivity());
		wv_sex.setAdapter(sexWheelAdapter);
		wv_sex.setCyclic(false);
		wv_sex.setVisibleItems(3);
		wv_sex.setCurrentItem(0);
		sexWheelListener = new SexWheelListener();

		wv_sex.addChangingListener(sexWheelListener);

		// 设置date布局
		sexBuilder.setView(view);
		sexBuilder.setTitle("设置性别信息");
		sexBuilder.setPositiveButton("确  定", sexWheelListener);
		sexBuilder.setNegativeButton("取  消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		sexBuilder.create().show();
	}

	private void showDatePicker() {
		// 通过自定义控件AlertDialog实现
		AlertDialog.Builder birthdayBuilder = new AlertDialog.Builder(
				getActivity());
		View view = View.inflate(getActivity(), R.layout.dialog_datepicker,
				null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.date_picker);
		// 设置日期简略显示 否则详细显示 包括:星期周
		datePicker.setCalendarViewShown(false);

		String olderBirthday = SharePrefaceTool.readout(getActivity(),
				"birthday");
		// 如果有存储的生日则显示否则显示系统当前的时间
		if (!TextUtils.isEmpty(olderBirthday)) {
			String[] split = olderBirthday.split("-");

			int year = Integer.valueOf(split[0]);
			int monthOfYear = Integer.valueOf(split[1]);
			int dayOfMonth = Integer.valueOf(split[2]);
			datePicker.setCalendarViewShown(false);
			datePicker.init(year, monthOfYear, dayOfMonth, null);
		} else {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			int year = calendar.get(Calendar.YEAR);
			int monthOfYear = calendar.get(Calendar.MONTH);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			datePicker.setCalendarViewShown(false);
			datePicker.init(year, monthOfYear, dayOfMonth, null);
		}

		// 设置date布局
		birthdayBuilder.setView(view);
		birthdayBuilder.setTitle("设置日期信息");
		birthdayBuilder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 日期格式
						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						LogUtils.e("测试项目-------------------" + sb);
						// 修改本地同步网络
						modifyBirthday(sb.toString());
						dialog.cancel();
					}
				});
		birthdayBuilder.setNegativeButton("取  消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		birthdayBuilder.create().show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		// 从相册中选取
		case 0:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);// 裁剪框比例
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 108);// 输出图片大小
			intent.putExtra("outputY", 108);
			intent.putExtra("return-data", true);
			startActivityForResult(Intent.createChooser(intent, "选择图片"), 0);

			break;
		// 拍照
		case 1:
			Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(i, 1);
			break;

		default:
			break;
		}
		listPopupWindow.dismiss();
	}

	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");

		intent.setDataAndType(uri, "image/*");

		intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 2);

		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", outputX);

		intent.putExtra("outputY", outputY);

		intent.putExtra("scale", true);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		intent.putExtra("return-data", false);

		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

		intent.putExtra("noFaceDetection", true); // no face detection

		startActivityForResult(intent, requestCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		switch (requestCode) {
		// 从相册中选取
		case 0:
			Bitmap bitmap = data.getParcelableExtra("data");
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 将文件存入到文件中
				boolean bitmap2File = FileTool.Bitmap2File(
						bitmap,
						UserLoginInfo.getUserLoginInfo().getLocalPhoto(
								UserLoginInfo.getUserLoginInfo()
										.getPhoneNumber()));
				personal_profile_iv_photo.setImageBitmap(bitmap);
				if (bitmap2File) {
					Bundle requestArgs = new Bundle();
					// requestArgs.putString("id",
					// UserLoginInfo.getUserLoginInfo().getPhoneNumber());
					requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
					File file = new File(UserLoginInfo.getUserLoginInfo()
							.getLocalPhoto(
									UserLoginInfo.getUserLoginInfo()
											.getPhoneNumber()));
					httpTools.doPost(requestArgs, Config.USER_UP_AVATER, "pic",
							file, 1);
				}

			}
			break;
		// 从拍照中获取
		case 1:
			Bundle extras = data.getExtras();
			Bitmap bmp = (Bitmap) extras.get("data");

			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 将文件存入到文件中
				boolean bitmap2File = FileTool.Bitmap2File(
						bmp,
						UserLoginInfo.getUserLoginInfo().getLocalPhoto(
								UserLoginInfo.getUserLoginInfo()
										.getPhoneNumber()));
				personal_profile_iv_photo.setImageBitmap(bmp);
				if (bitmap2File) {
					Bundle requestArgs = new Bundle();
					// requestArgs.putString("id",
					// UserLoginInfo.getUserLoginInfo().getPhoneNumber());
					requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
					File file = new File(UserLoginInfo.getUserLoginInfo()
							.getLocalPhoto(
									UserLoginInfo.getUserLoginInfo()
											.getPhoneNumber()));
					httpTools.doPost(requestArgs, Config.USER_UP_AVATER, "pic",
							file, 1);
				}

			}
			break;
		// 获取昵称文字
		case 2:
			String newNickName = data.getStringExtra("result");
			modifyNickname(newNickName);
			break;
		// 获取性别
		case 3:
			String sex = data.getStringExtra("result");
			modifySex(sex);
			break;
		// 生日
		case 4:
			String birthday = data.getStringExtra("result");
			modifyBirthday(birthday);
			break;
		// 所在地
		case 5:
			String location = data.getStringExtra("result");
			modifyLocation(location);

			break;
		// 个人简介
		case 6:
			String profile = data.getStringExtra("result");
			modifyProfile(profile);
			break;
		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void modifyNickname(String newNickName) {
		String olderNickname = SharePrefaceTool.readout(getActivity(),
				"nickname");
		// 校验有更改去请求网络
		if (!olderNickname.equals(newNickName)) {
			// 将新的昵称存入本地
			SharePrefaceTool.writein(getActivity(), "nickname", newNickName);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setNickName(newNickName);
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				requestArgs.putString("username", newNickName);
				httpTools.doPost(requestArgs, Config.USER_UP_NICKNAME, 2);
			}
		}
	}

	private void modifySex(String sex) {
		String olderSex = SharePrefaceTool.readout(getActivity(), "sex");
		if (!sex.equals(olderSex)) {
			SharePrefaceTool.writein(getActivity(), "sex", sex);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setSex(sex);
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				requestArgs.putString("sex", sex);
				httpTools.doPost(requestArgs, Config.USER_UP_SEX, 3);
			}
		}
	}

	private void modifyBirthday(String birthday) {
		// String birthday = sb.toString();
		String olderBirthday = SharePrefaceTool.readout(getActivity(),
				"birthday");
		if (!birthday.equals(olderBirthday)) {
			SharePrefaceTool.writein(getActivity(), "birthday", birthday);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setBirthday(birthday);
				// 设置参数
				Bundle requestArgs = new Bundle();
				String[] split = birthday.split("-");
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("year", split[0]);
				requestArgs.putString("month", split[1]);
				requestArgs.putString("day", split[2]);
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				httpTools.doPost(requestArgs, Config.USER_UP_BIRTHDAY, 4);
			}
		}
	}

	private void modifyProfile(String profile) {
		String olderProfile = SharePrefaceTool
				.readout(getActivity(), "profile");
		if (!profile.equals(olderProfile)) {
			SharePrefaceTool.writein(getActivity(), "profile", profile);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setProfile(profile);
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("introduction", profile);
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				httpTools.doPost(requestArgs, Config.USER_UP_PROFILES, 6);
			}
		}
	}

	private void modifyLocation(String location) {
		String olderLocation = SharePrefaceTool.readout(getActivity(),
				"location");
		if (!location.equals(olderLocation)) {
			SharePrefaceTool.writein(getActivity(), "location", location);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setLocation(location);
				// 设置参数
				Bundle requestArgs = new Bundle();
				String[] split = location.split("#");
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("province_name", split[0]);
				requestArgs.putString("province_id", split[1]);
				requestArgs.putString("city_name", split[2]);
				requestArgs.putString("city_id", split[3]);
				requestArgs.putString("district_name", split[4]);
				requestArgs.putString("district_id", split[5]);
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				httpTools.doPost(requestArgs, Config.USER_UP_ADDRESS, 5);
			}
		}
	}

	class SexWheelListener implements OnWheelChangedListener,
			DialogInterface.OnClickListener {

		private int newValue;
		private int oldValue;

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			this.newValue = newValue;
			this.oldValue = oldValue;

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String newValueData = sexWheelAdapter.getData().get(newValue);
			String olderValueData = sexWheelAdapter.getData().get(oldValue);
			LogUtils.e("测试项目-------------------" + newValueData + "---"
					+ olderValueData);
			// 修改本地同步网络
			modifySex(newValue + "");
			dialog.cancel();
		}
	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		// TODO Auto-generated method stub

	}

}
