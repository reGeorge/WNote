package com.regeorge.wnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.regeorge.wnote.R;
import com.regeorge.wnote.adapter.GridViewAdapter;
import com.regeorge.wnote.adapter.ListViewAdapter;
import com.regeorge.wnote.database.NotesDB;
import com.regeorge.wnote.utils.EverNoteUtils;

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
    private EverNoteUtils mEverNoteUtils;
    private static int FLAG = 1;
    private SharedPreferences settings;
    private DataSetObserver observer;
    private DataSetObserver observer2;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 初始化界面 listview 和 gridview 和 一个新建按钮
    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        gv = (GridView) findViewById(R.id.grid);

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
                        //i.putExtra("flag", "1");
                        startActivity(i);
                        break;
                }
            }
        });



    }

    // 返回键
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 初始化toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar** if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 显示toolbar菜单
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //menu.clear();
        aMenu = menu;
        checkOptionMenuClickCallback();
        return super.onPrepareOptionsMenu(menu);
    }

    private Menu aMenu;

    //切换列表按钮和网格按钮
    public void checkOptionMenuClickCallback() {
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

    // 切换列表模式和网格模式
    @RequiresApi(api = Build.VERSION_CODES.M)
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
                checkOptionMenuClickCallback();
                selectDB();
                break;
            case R.id.switcher_grid:
                editor.putInt("itemMode", 0);
                editor.commit();
                lv.setVisibility(GONE);
                gv.setVisibility(View.VISIBLE);
                checkOptionMenuClickCallback();
                selectDB();
                break;
            default:

        }
        return true;
    }


    //侧边栏菜单
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_author:
                Intent i = new Intent(MainActivity.this,AboutMe.class);
                startActivity(i);
                break;
            case R.id.nav_rv_pic:
                Intent j = new Intent(MainActivity.this,RvActivity.class);
                startActivity(j);
                break;
            case R.id.nav_sync:
                mEverNoteUtils = new EverNoteUtils();
                if (!mEverNoteUtils.isLogin()) {
                    Intent k = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(k);
                }
                Toast.makeText(this, "开始同步...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                mEverNoteUtils = new EverNoteUtils();
                if (mEverNoteUtils.isLogin()) {
                    mEverNoteUtils.logout();
                    Toast.makeText(this, "已退出当前登录的EverNote账号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_img:
                Intent l = new Intent(MainActivity.this,GlideTest.class);
                startActivity(l);
                break;
            default:
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }


    // 读数据库
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void selectDB() {
        Cursor cursor = dbReader.query(NotesDB.TABLE_NAME, null,
                null, null, null, null, NotesDB.TIME+" desc");
        adapter = new ListViewAdapter(this,cursor,notesDB);
        adapter2 = new GridViewAdapter(this, cursor, notesDB);
        lv.setAdapter(adapter);
        gv.setAdapter(adapter2);
        //滑动时关闭删除按钮
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                adapter.closeAllItems();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                adapter2.closeAllItems();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //对adapter添加观察者监听
        observer = new DataSetObserver(){
            public void onChanged() {
                selectDB();
            }
        };
        adapter.registerDataSetObserver(observer);
        observer2 = new DataSetObserver(){
            public void onChanged() {
                selectDB();
            }
        };
        adapter2.registerDataSetObserver(observer2);
    }

}
