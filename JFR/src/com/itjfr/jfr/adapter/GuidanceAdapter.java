package com.itjfr.jfr.adapter;

import java.util.List;

import com.itjfr.jfr.R;
import com.itjfr.jfr.domain.GuidanceData;
import com.itjfr.jfr.utils.ResourceLoadTool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class GuidanceAdapter extends AdapterTemplent<GuidanceData> {

	private List<GuidanceData> list;
	private Context context;

	public GuidanceAdapter(List<GuidanceData> list, Context context) {
		super(list);
		this.list = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(context);
		}
		LayoutParams params = new LayoutParams(260, -2);
		convertView.setLayoutParams(params);
		TextView tv = (TextView) convertView;
		tv.setText(list.get(position).getTitle());
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundColor(Color.WHITE);
		tv.setTextSize(17);
		Drawable drawable = context.getResources().getDrawable(list.get(position).getImageResource());
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
		tv.setCompoundDrawables(null, drawable, null, null);
		tv.setGravity(Gravity.CENTER);
		
		return convertView;
	}

}
