package com.regeorge.wnote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.regeorge.wnote.R;


public class ListViewAdapter extends BaseSwipeAdapter {

    private Context context;
    private Cursor cursor;
    private TextView contentv;
    private TextView timev;


    public ListViewAdapter(Context context,Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        //View v = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        // 获取布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =  inflater.inflate(R.layout.cell,null);
        //返回文件中的view
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        contentv = (TextView) convertView.findViewById(R.id.list_content);
        timev = (TextView) convertView.findViewById(R.id.list_time);
        /*TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1) + ".");//已经替换*/


        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        convertView.findViewById(R.id.c_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
            }
        });

        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex("content"));
        contentv.setText(content);
        String time = cursor.getString(cursor.getColumnIndex("time"));
        timev.setText(time);
    }


}
