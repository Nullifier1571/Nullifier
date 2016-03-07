package com.itjfr.jfr.adapter;

import java.util.List;

import com.itjfr.jfr.R;
import com.itjfr.jfr.utils.ResourceLoadTool;
import com.itjfr.jfr.utils.UITool;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class ListPopupWindowAdapter extends AdapterTemplent<String> {

	private Context context;
	private List<String> list;

	public ListPopupWindowAdapter(List<String> list, Context context) {
		super(list);
		this.list=list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = new TextView(context);
		}
		//UITool.dip2px(context, 130)
		LayoutParams params=new LayoutParams(UITool.dip2px(context, 110),UITool.dip2px(context, 45));
		convertView.setLayoutParams(params);
		TextView tv=(TextView)convertView;
		tv.setText(list.get(position));
		tv.setTextColor(ResourceLoadTool.loadColor(context, R.color.color_text_gray));
		tv.setBackgroundColor(Color.WHITE);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setTextSize(17);
		return convertView;
	}

}
