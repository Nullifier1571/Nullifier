package com.itjfr.jfr.activity;

import com.google.gson.Gson;
import com.itjfr.jfr.R;
import com.itjfr.jfr.adapter.CityWheelAdapter;
import com.itjfr.jfr.adapter.DistrictWheelAdapter;
import com.itjfr.jfr.adapter.ProviceWheelAdapter;
import com.itjfr.jfr.adapter.SexWheelAdapter;
import com.itjfr.jfr.domain.Region;
import com.itjfr.jfr.domain.Region.City;
import com.itjfr.jfr.listener.OnWheelChangedListener;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.ResourceLoadTool;
import com.itjfr.jfr.view.WheelView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LocationPickerActivity extends Activity implements
		OnWheelChangedListener {
	@ViewInject(R.id.provice_picker)
	private WheelView provice_picker;

	@ViewInject(R.id.city_picker)
	private WheelView city_picker;

	@ViewInject(R.id.district_picker)
	private WheelView district_picker;

	private ProviceWheelAdapter provinceWheelAdapter;

	private Region fromJson;

	private CityWheelAdapter cityWheelAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_picker);
		ViewUtils.inject(this);
		Gson gson = new Gson();
		String region = ResourceLoadTool.loadStringFromAssets(this,
				"region.txt");

		fromJson = gson.fromJson(region, Region.class);

		LogUtils.e("省的个数" + fromJson.contry.size());
		City city = fromJson.contry.get(0);
		LogUtils.e("市的个数" + city.city.size());

		LogUtils.e("县的个数" + city.city.get(0).district.size());

		provinceWheelAdapter = new ProviceWheelAdapter(this, fromJson.contry);
		provice_picker.setAdapter(provinceWheelAdapter);
		provice_picker.setCyclic(false);
		provice_picker.setVisibleItems(5);
		provice_picker.setCurrentItem(0);
		provice_picker.addChangingListener(this);
		
		
		city_picker.setCyclic(false);
		city_picker.setVisibleItems(5);
		city_picker.setCurrentItem(0);
		city_picker.addChangingListener(this);
		
		
		district_picker.setCyclic(false);
		district_picker.setVisibleItems(5);
		district_picker.setCurrentItem(0);
		district_picker.addChangingListener(this);
		

	}
	private int province =0;
	private int city =0;
	private int district =0;

	private DistrictWheelAdapter districtWheelAdapter;

	private String provinceName;
	private String provinceID;
	
	private String cityName;
	private String cityID;
	
	private String districtName;
	private String districtID;
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		
		switch (wheel.getId()) {
		case R.id.provice_picker:
			try {
				province=newValue;
				//省的名字
				LogUtils.e("省级选择器:" + "新的值:" + newValue + "旧的值:" + oldValue);
				cityWheelAdapter = new CityWheelAdapter(this, fromJson.contry.get(province).city);
				city_picker.setAdapter(cityWheelAdapter);
				districtWheelAdapter = new DistrictWheelAdapter(this, fromJson.contry.get(province).city.get(city).district);
				district_picker.setAdapter(districtWheelAdapter);
				//当省变动的时候获取省市县的名字和ID
				provinceName = fromJson.contry.get(province).name;
				provinceID = fromJson.contry.get(province).id;
				cityName = fromJson.contry.get(province).city.get(city).name;
				cityID = fromJson.contry.get(province).city.get(city).id;
				districtName=fromJson.contry.get(province).city.get(city).district.get(district).name;
				districtID=fromJson.contry.get(province).city.get(city).district.get(district).id;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.city_picker:
			try {
				city=newValue;
				LogUtils.e("市级选择器:" + "新的值:" + newValue + "旧的值:" + oldValue);
				districtWheelAdapter = new DistrictWheelAdapter(this, fromJson.contry.get(province).city.get(city).district);
				district_picker.setAdapter(districtWheelAdapter);
				//当市变动的时候获取市和县的名字ID
				cityName = fromJson.contry.get(province).city.get(city).name;
				cityID = fromJson.contry.get(province).city.get(city).id;
				districtName=fromJson.contry.get(province).city.get(city).district.get(district).name;
				districtID=fromJson.contry.get(province).city.get(city).district.get(district).id;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.district_picker:
			district=newValue;
			//当县变动的时候获取他的名字和ID
			districtName=fromJson.contry.get(province).city.get(city).district.get(district).name;
			districtID=fromJson.contry.get(province).city.get(city).district.get(district).id;
			LogUtils.e("县级选择器:" + "新的值:" + newValue + "旧的值:" + oldValue);
			break;

		default:
			break;
		}
		LogUtils.e("省:" +province +"市:" + city + "县:" + district);
	}
	
	@Override
	public void onBackPressed() {
		
		Intent intent = new Intent();
		intent.putExtra("result", provinceName+"#"+provinceID+"#"+cityName+"#"+cityID+"#"+districtName+"#"+districtID);
		setResult(0, intent);
		finish();
		super.onBackPressed();
	}
}
