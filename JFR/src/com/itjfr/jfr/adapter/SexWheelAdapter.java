package com.itjfr.jfr.adapter;

import java.util.Arrays;
import java.util.List;

import com.itjfr.jfr.R;

import android.content.Context;


public class SexWheelAdapter implements WheelAdapter {
	
	private List<String> sex;
	
	public SexWheelAdapter(Context context){
		sex = Arrays.asList(context.getResources().getStringArray(R.array.sex_array));
	}

	@Override
	public int getItemsCount() {
		return sex == null ? 0 : sex.size();
	}

	@Override
	public String getItem(int index) {
		return index <= sex.size() - 1 ? sex.get(index) : null;
	}

	@Override
	public int getMaximumLength() {
		return 3;
	}
	
	@Override
	public String getCurrentId(int index) {
		return "";
	}
	
	public List<String> getData(){
		return sex;
	}

}
