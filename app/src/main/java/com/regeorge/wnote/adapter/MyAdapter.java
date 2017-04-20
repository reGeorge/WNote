/*//初始适配器
package com.regeorge.wnote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.regeorge.wnote.R;

public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	
	public MyAdapter(Context context,Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell,null);
		TextView contentv = (TextView) layout.findViewById(R.id.list_content);
		TextView timev = (TextView) layout.findViewById(R.id.list_time);
		cursor.moveToPosition(position);
		String content = cursor.getString(cursor.getColumnIndex("content"));
		contentv.setText(content);
		String time = cursor.getString(cursor.getColumnIndex("time"));
		timev.setText(time);
		
		return layout;
	}

}
*/
