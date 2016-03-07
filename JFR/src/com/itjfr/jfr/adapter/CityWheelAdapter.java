package com.itjfr.jfr.adapter;

import java.util.Arrays;
import java.util.List;

import com.itjfr.jfr.domain.Region.City;
import com.itjfr.jfr.domain.Region.City.Area;
import com.itjfr.jfr.utils.LogUtils;

import android.content.Context;

public class CityWheelAdapter implements WheelAdapter {

	private List<Area> citys;

	public CityWheelAdapter(Context context, List<Area> citys) {
		this.citys = citys;
	}

	@Override
	public int getItemsCount() {
		return citys == null ? 0 : citys.size();
	}

	@Override
	public String getItem(int index) {
		if(citys==null){
			LogUtils.e("provices为空");
		}
		
		String result=index <= citys.size() - 1 ? citys.get(index).name : null;
		return result;
	}

	@Override
	public int getMaximumLength() {
		return 5;
	}

	@Override
	public String getCurrentId(int index) {
		return "";
	}

	public List<Area> getData() {
		return citys;
	}

}
