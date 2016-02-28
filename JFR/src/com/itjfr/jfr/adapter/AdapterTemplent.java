package com.itjfr.jfr.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterTemplent<T> extends BaseAdapter {

	private List<T> list;

	public AdapterTemplent(List<T> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 刷新列表
	 * 
	 * @param newList
	 *            新的数据集合，如果为null则直接刷新否则加载新的数据在刷新
	 */
	public void refresh(List<T> newList) {
		if (newList != null) {
			list.clear();
			list.addAll(newList);
		}
		notifyDataSetChanged();
	}
}
