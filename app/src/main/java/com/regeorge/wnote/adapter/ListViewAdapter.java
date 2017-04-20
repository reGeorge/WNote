package com.regeorge.wnote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.regeorge.wnote.NotesDB;
import com.regeorge.wnote.R;


public class ListViewAdapter extends BaseSwipeAdapter {



    private Context context;
    private Cursor cursor;
    private TextView contentv;
    private TextView timev;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;


    public ListViewAdapter(Context context,Cursor cursor,NotesDB notesDB) {
        this.context = context;
        this.cursor = cursor;
        this.notesDB = notesDB;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setNotesDB(NotesDB notesDB) {
        this.notesDB = notesDB;
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
        // 获取cell布局文件v
        View v = LayoutInflater.from(context).inflate(R.layout.cell,null);
        //返回文件中的view

        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

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
        v.findViewById(R.id.c_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWriter = notesDB.getWritableDatabase();
                dbWriter.delete(NotesDB.TABLE_NAME, "_id=" + cursor.getInt(cursor.getColumnIndex(NotesDB.ID)), null);
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        contentv = (TextView) convertView.findViewById(R.id.list_content);
        timev = (TextView) convertView.findViewById(R.id.list_time);

        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT));
        contentv.setText(content);
        String time = cursor.getString(cursor.getColumnIndex(NotesDB.TIME));
        timev.setText(time);

    }

}
