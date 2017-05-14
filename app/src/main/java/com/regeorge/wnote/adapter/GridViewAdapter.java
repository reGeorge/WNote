package com.regeorge.wnote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.regeorge.wnote.NotesDB;
import com.regeorge.wnote.R;


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
        String time = cursor.getString(cursor.getColumnIndex(NotesDB.TIME));
        timev.setText(time);

        convertView.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
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