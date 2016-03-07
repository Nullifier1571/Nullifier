package com.itjfr.jfr.adapter;

import java.io.File;
import java.util.List;
import com.itjfr.jfr.R;
import com.itjfr.jfr.activity.NewsDetailActivity;
import com.itjfr.jfr.domain.NewsInfo;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.LogUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class NewsAdapter extends AdapterTemplent<NewsInfo> implements
		OnItemClickListener, RequestNetListener {

	private Context context;
	private List<NewsInfo> list;
	private HttpTools httpTools;

	public NewsAdapter(List<NewsInfo> list, Context context) {
		super(list);
		this.context = context;
		this.list = list;
		httpTools = HttpTools.getHttpToolsInstance();
		httpTools.setRequestNetListener(this);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_news, null);
			viewHolder = new ViewHolder();
			viewHolder.home_tv_news_content = (TextView) convertView
					.findViewById(R.id.home_tv_news_content);
			viewHolder.home_iv_news_image = (ImageView) convertView
					.findViewById(R.id.home_iv_news_image);

			viewHolder.rg_news = (RadioGroup) convertView
					.findViewById(R.id.rg_news);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置图片 暂时用本地图片的代替
		viewHolder.home_iv_news_image
				.setImageResource(R.drawable.item_home_news_image);
		viewHolder.home_tv_news_content.setText(list.get(position).getTitle());
		((RadioButton) viewHolder.rg_news.getChildAt(0)).setText("123");
		((RadioButton) viewHolder.rg_news.getChildAt(1)).setText("14");
		viewHolder.rg_news
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.rb_like:
							LogUtils.e(position + "点击事件触发 点赞");
							break;
						case R.id.rb_unlike:
							LogUtils.e(position + "点击事件触发 点踩");
							break;

						default:
							break;
						}

					}
				});
		return convertView;
	}

	class ViewHolder {
		ImageView home_iv_news_image;
		TextView home_tv_news_content;
		RadioGroup rg_news;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		LogUtils.e("新闻条目点击事件" + position);
		Intent intent = new Intent(context, NewsDetailActivity.class);
		intent.putExtra("news_id", list.get(position).getId());
		context.startActivity(intent);

	}

	@Override
	public void getResultData(boolean isOk, int errorCode, String result,
			int Tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		// TODO Auto-generated method stub

	}

}
