package com.regeorge.wnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.regeorge.wnote.adapter.GridViewAdapter;
import com.regeorge.wnote.adapter.ListViewAdapter;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FloatingActionButton newbtn;
    private ListView lv;
    private GridView gv;
    //private View deleteBtn;
    private Intent i;
    private ListViewAdapter adapter;
    private GridViewAdapter adapter2;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    //private SQLiteDatabase dbWriter;
    private Cursor cursor;
    private static int FLAG = 1;
    private SharedPreferences settings;
    public static final String PREFS_NAME = "ItemMode_Setting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        initView();
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
        //dbWriter = notesDB.getWritableDatabase();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null,
                        null, null, NotesDB.TIME+" desc");
                //将数据库表与lv的item映射；
                cursor.moveToPosition(position);
                Intent j = new Intent(MainActivity.this,ShowContent.class);
                j.putExtra(NotesDB.ID, cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                j.putExtra(NotesDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                j.putExtra(NotesDB.TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                startActivity(j);
            }
        });

        gv = (GridView) findViewById(R.id.grid);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null,
                        null, null, NotesDB.TIME+" desc");
                //将数据库表与lv的item映射；
                cursor.moveToPosition(position);
                Intent j = new Intent(MainActivity.this,ShowContent.class);
                j.putExtra(NotesDB.ID, cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                j.putExtra(NotesDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                j.putExtra(NotesDB.TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                startActivity(j);
            }
        });

        settings = getSharedPreferences(PREFS_NAME, 0);
        FLAG = settings.getInt("itemMode", 1);
        switch (FLAG) {
            case 0:
                lv.setVisibility(View.GONE);
                gv.setVisibility(View.VISIBLE);
                break;
            case 1:
                lv.setVisibility(View.VISIBLE);
                gv.setVisibility(View.GONE);
                break;
            default:
        }

        newbtn = (FloatingActionButton) findViewById(R.id.new_btn);
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, AddContent.class);
                switch(v.getId()) {
                    case R.id.new_btn:
                        //System.out.println("qwer1234");
                        i.putExtra("flag", "1");
                        startActivity(i);
                        break;
                }
            }
        });



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar** if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   /* @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //menu.clear();
        aMenu = menu;
        checkOptionMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    private Menu aMenu;

    //不能直接调用的方法，自己写逻辑，将该方法放到重写方法里，在需要调用的地方触发。（回调）
    public void checkOptionMenu() {
        settings = getSharedPreferences(PREFS_NAME, 0);
        FLAG = settings.getInt("itemMode", 1);
        switch (FLAG) {
            case 0:
                aMenu.findItem(R.id.switcher_list).setVisible(true);
                aMenu.findItem(R.id.switcher_grid).setVisible(false);
                break;
            case 1:
                aMenu.findItem(R.id.switcher_list).setVisible(false);
                aMenu.findItem(R.id.switcher_grid).setVisible(true);
                break;
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.switcher_list:
                editor.putInt("itemMode", 1);
                editor.commit();
                lv.setVisibility(View.VISIBLE);
                gv.setVisibility(GONE);
                checkOptionMenu();

                break;
            case R.id.switcher_grid:
                editor.putInt("itemMode", 0);
                editor.commit();
                lv.setVisibility(GONE);
                gv.setVisibility(View.VISIBLE);
                checkOptionMenu();
                break;
            default:

        }
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_author:
                Intent i = new Intent(MainActivity.this,AboutMe.class);
                startActivity(i);
                break;
            default:
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    public void selectDB() {
        Cursor cursor = dbReader.query(NotesDB.TABLE_NAME, null,
                null, null, null, null, NotesDB.TIME+" desc");
        adapter = new ListViewAdapter(this,cursor,notesDB);
        adapter2 = new GridViewAdapter(this, cursor, notesDB);
        lv.setAdapter(adapter);
        gv.setAdapter(adapter2);
        //对adapter添加观察者监听
        DataSetObserver observer = new DataSetObserver(){
            public void onChanged() {
                selectDB();
            }
        };
        adapter.registerDataSetObserver(observer);
        DataSetObserver observer2 = new DataSetObserver(){
            public void onChanged() {
                selectDB();
            }
        };
        adapter2.registerDataSetObserver(observer2);
    }




}
