package com.regeorge.wnote.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.regeorge.wnote.R;
import com.regeorge.wnote.database.NotesDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowContent extends AppCompatActivity  {

	//private Button s_cancel,s_save;
	private EditText s_edtxt;
    private EditText s_time;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	//private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcontent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.s_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void initView() throws ParseException {
    	//s_delete = (Button) findViewById(R.id.s_deletebtn);
    	//s_cancel = (Button) findViewById(R.id.s_cancelbtn);
		//s_save = (Button) findViewById(R.id.s_savebtn);
    	s_edtxt = (EditText) findViewById(R.id.s_edtext);
        s_time = (EditText) findViewById(R.id.s_time);

    	notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
		s_edtxt.setText(this.getIntent().getStringExtra(NotesDB.CONTENT));
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = format.parse(this.getIntent().getStringExtra(NotesDB.TIME));
        format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = format.format(date);
        s_time.setText(time);

        s_time. setCursorVisible ( false ) ;
        s_time.setFocusable(false);
        s_time.clearFocus();
        s_edtxt. setCursorVisible ( false ) ;
        s_edtxt.setFocusable(false);
        s_edtxt.clearFocus();

        s_edtxt.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           //getSelectionStart();
                                           Intent i = ShowContent.this.getIntent();
                                           i.setClass(ShowContent.this,UpdateContent.class);
                                           startActivity(i);
                                           finish();
                                       }
                                   });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.grid_delete:
                deleteData();
                finish();
                break;
            default:
        }
        return true;

    }
	public void deleteData() {
		dbWriter.delete(NotesDB.TABLE_NAME, "_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
	}



}
