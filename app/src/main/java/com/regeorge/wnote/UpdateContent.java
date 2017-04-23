package com.regeorge.wnote;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateContent extends AppCompatActivity {
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private EditText u_edtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecontent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.a_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        }

        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        initView();
    }

    public void initView() {
        u_edtxt = (EditText) findViewById(R.id.u_edtext);
        u_edtxt. setCursorVisible (true) ;
        u_edtxt.setFocusable(true);
        u_edtxt.setText(this.getIntent().getStringExtra(NotesDB.CONTENT));
        //u_edtxt.setFocusableInTouchMode(true);
        u_edtxt.setSelection(u_edtxt.getText().toString().length());
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent i = new Intent(UpdateContent.this,MainActivity.class);
        switch (item.getItemId()) {
            case android.R.id.home:
                if("".equals(u_edtxt.getText().toString())
                        ||this.getIntent().getStringExtra(NotesDB.CONTENT).equals(u_edtxt.getText().toString()))
                {
                }
                else {
                    updateData();
                }
                //startActivity(i);
                finish();
                break;
            case R.id.check:
                if("".equals(u_edtxt.getText().toString()))
                {
                    deleteData();
                    //Toast.makeText(getBaseContext(), "已删除", Toast.LENGTH_SHORT).show();
                }
                else if(this.getIntent().getStringExtra(NotesDB.CONTENT).equals(u_edtxt.getText().toString()))
                {

                }
                else {
                    updateData();
                }
                //startActivity(i);
                finish();
                break;
            default:
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        //Intent i = new Intent(UpdateContent.this,MainActivity.class);
        if("".equals(u_edtxt.getText().toString())
                ||this.getIntent().getStringExtra(NotesDB.CONTENT).equals(u_edtxt.getText().toString()))
        {
        }
        else {
            updateData();
        }
        //startActivity(i);
        finish();
    }

    public void deleteData() {
        dbWriter.delete(NotesDB.TABLE_NAME, "_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
    }

    public void updateData() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,u_edtxt.getText().toString());
        cv.put(NotesDB.TIME, getTime());
        dbWriter.update(NotesDB.TABLE_NAME, cv,
                "_id="+getIntent().getIntExtra(NotesDB.ID, 0),null);
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }
}
