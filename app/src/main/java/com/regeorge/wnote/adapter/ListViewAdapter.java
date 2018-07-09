package com.regeorge.wnote.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.regeorge.wnote.R;
import com.regeorge.wnote.activity.ShowContent;
import com.regeorge.wnote.database.NotesDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ListViewAdapter extends BaseSwipeAdapter{



    private Context context;
    private Cursor cursor;
    private TextView contentv;
    private TextView timev;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;


    public ListViewAdapter(Context context, Cursor cursor, NotesDB notesDB) {
        this.context = context;
        this.cursor = cursor;
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
    public View generateView(final int position, ViewGroup parent) {
        // 获取cell布局文件v
        return LayoutInflater.from(context).inflate(R.layout.listview_item,null);

    }

   /* @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }*/

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void fillValues(final int position, View convertView) {
        contentv = (TextView) convertView.findViewById(R.id.list_content);
        timev = (TextView) convertView.findViewById(R.id.list_time);

        cursor.moveToPosition(position);
        String content = cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT));
        contentv.setText(content);
        timev.setText(getTime());

        final SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.list_trash));
            }
        });
        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看getOpenItems方法的返回值再用相同类型的变量比较??
                List<Integer> A = getOpenItems();//未打开则为-1
                int a = A.get(0);
                List<Integer> B = Arrays.asList(-1);
                int b = B.get(0);

                if(B.equals(A)) {
                    cursor.moveToPosition(position);
                    Intent j = new Intent(context, ShowContent.class);
                    j.putExtra(NotesDB.ID, cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                    j.putExtra(NotesDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                    j.putExtra(NotesDB.TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                    context.startActivity(j);
                }else {
                    closeAllItems();
                }
            }
        });

        convertView.findViewById(R.id.list_delete).setOnClickListener(new View.OnClickListener() {
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

}
