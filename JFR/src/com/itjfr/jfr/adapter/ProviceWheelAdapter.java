package com.itjfr.jfr.adapter;

import java.util.Arrays;
import java.util.List;

import com.itjfr.jfr.domain.Region.City;
import com.itjfr.jfr.utils.LogUtils;

import android.content.Context;

public class ProviceWheelAdapter implements WheelAdapter {

	private List<City> provices;

	public ProviceWheelAdapter(Context context, List<City> provices) {
		this.provices = provices;
	}

	@Override
	public int getItemsCount() {
		return provices == null ? 0 : provices.size();
	}

	@Override
	public String getItem(int index) {
		if(provices==null){
			LogUtils.e("provices为空");
		}
		
		String result=index <= provices.size() - 1 ? provices.get(index).name : null;
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

	public List<City> getData() {
		return provices;
	}

}
