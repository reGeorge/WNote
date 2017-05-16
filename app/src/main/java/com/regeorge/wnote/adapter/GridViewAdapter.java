package com.regeorge.wnote.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.regeorge.wnote.database.NotesDB;
import com.regeorge.wnote.R;
import com.regeorge.wnote.activity.ShowContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GridViewAdapter extends BaseSwipeAdapter {

    private Context context;
    private Cursor cursor;
    private TextView contentv;
    private TextView timev;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    public GridViewAdapter(Context context, Cursor cursor, NotesDB notesDB) {
        this.context = context;
        this.cursor = cursor;
        this.notesDB = notesDB;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        contentv = (TextView) convertView.findViewById(R.id.grid_content);
        timev = (TextView) convertView.findViewById(R.id.grid_time);

        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT));
        contentv.setText(content);
        timev.setText(getTime());

        final SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swipeLayout.getOpenStatus() == SwipeLayout.Status.Open) {
                    swipeLayout.toggle();
                }
                else if(swipeLayout.getOpenStatus() == SwipeLayout.Status.Close){
                    cursor.moveToPosition(position);
                    Intent j = new Intent(context, ShowContent.class);
                    j.putExtra(NotesDB.ID, cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                    j.putExtra(NotesDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                    j.putExtra(NotesDB.TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                    context.startActivity(j);
                }
            }
        });
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.grid_trash));
            }
        });
        convertView.findViewById(R.id.grid_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                dbWriter = notesDB.getWritableDatabase();
                dbWriter.delete(NotesDB.TABLE_NAME, "_id=" + cursor.getInt(
                        cursor.getColumnIndex(NotesDB.ID)), null);
                notifyDataSetChanged();
            }
        });
    }
    public String getTime() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy");

        SimpleDateFormat format4 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format5 = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat format6 = new SimpleDateFormat("yyyy年MM月");
        Date date = null;
        Date today = new Date();
        try {
            date = format1.parse(cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time = null;
        if (format2.format(today).equals(format2.format(date))) {
            time = format4.format(date);
        }else if(format3.format(today).equals(format3.format(date))) {
            time = format5.format(date);
        }else {
            time = format6.format(date);
        }
        return time;
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

}