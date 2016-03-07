package com.itjfr.jfr.adapter;

import java.util.List;
import com.itjfr.jfr.domain.Region.City.Area.District;
import com.itjfr.jfr.utils.LogUtils;

import android.content.Context;

public class DistrictWheelAdapter implements WheelAdapter {

	private List<District> district;

	public DistrictWheelAdapter(Context context, List<District> citys) {
		this.district = citys;
	}

	@Override
	public int getItemsCount() {
		return district == null ? 0 : district.size();
	}

	@Override
	public String getItem(int index) {
		if(district==null){
			LogUtils.e("provices为空");
		}
		
		String result=index <= district.size() - 1 ? district.get(index).name : null;
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

	public List<District> getData() {
		return district;
	}

}
