package com.itjfr.jfr.adapter;

import java.util.List;

import com.itjfr.jfr.R;
import com.itjfr.jfr.domain.UserTag;
import com.itjfr.jfr.utils.ResourceLoadTool;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

public class UserLableAdapter extends AdapterTemplent<UserTag> {

	private Context context;
	private List<UserTag> list;

	public UserLableAdapter(List<UserTag> list,Context context) {
		super(list);
		this.list=list;
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = new TextView(context);
		}
		LayoutParams params=new LayoutParams(260,120);
		convertView.setLayoutParams(params);
		TextView tv=(TextView)convertView;
		tv.setText(list.get(position).getName());
		tv.setTextColor(ResourceLoadTool.loadColor(context, R.color.color_text_gray));
		tv.setBackgroundColor(Color.WHITE);
		tv.setTextSize(17);
		tv.setGravity(Gravity.CENTER);
		return convertView;
	}

}
