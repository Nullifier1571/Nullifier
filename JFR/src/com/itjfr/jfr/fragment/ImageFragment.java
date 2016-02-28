package com.itjfr.jfr.fragment;

import com.itjfr.jfr.R;
import com.itjfr.jfr.utils.UITool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageFragment extends BaseFragment {

	private String url;

	public ImageFragment setIamgeUrl(String url) {
		this.url = url;
		return this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView imageView = new ImageView(getActivity());
		
		imageView.setBackgroundResource(R.drawable.electronic_business_test);
		imageView.setLayoutParams(new LayoutParams(-1, -2));
		return imageView;
	}
}
