package com.regeorge.wnote;

import android.content.Intent;
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
    private static int FLAG = 0;


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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

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

    public void checkOptionMenu() {
        switch (FLAG) {
            case 0:
                aMenu.findItem(R.id.switcher1).setVisible(true);
                aMenu.findItem(R.id.switcher2).setVisible(false);
                break;
            case 1:
                aMenu.findItem(R.id.switcher1).setVisible(false);
                aMenu.findItem(R.id.switcher2).setVisible(true);
                break;
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.switcher1:
                FLAG = 1;
                lv.setVisibility(View.VISIBLE);
                gv.setVisibility(View.GONE);
                checkOptionMenu();

                break;
            case R.id.switcher2:
                FLAG = 0;
                lv.setVisibility(View.GONE);
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
            case R.id.nav_share:
                break;
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
