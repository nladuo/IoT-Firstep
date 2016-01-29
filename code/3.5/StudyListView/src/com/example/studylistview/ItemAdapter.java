package com.example.studylistview;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter{
	List<ItemBean> datas;
	LayoutInflater inflater;
	
	public ItemAdapter(List<ItemBean> datas, Context context) {
		super();
		this.datas = datas;
		this.inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			System.out.println("inflate-->" + position);
			//渲染item_lv.xml到view中
			convertView = inflater.inflate(R.layout.item_lv, null);
			holder = new ViewHolder();
			//拿到convertView的TextView
			holder.tv = (TextView) convertView.findViewById(R.id.tv_item_lv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//取得该位置的bean对象
		ItemBean bean = datas.get(position);
		//为TextView设置Text
		holder.tv.setText(bean.getText());
		return convertView;
	}
	
	public class ViewHolder{
		public TextView tv;
	}
}