package com.regeorge.wnote;

import android.content.ContentValues;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.regeorge.wnote.R.id.s_edtext;


public class ShowContent extends AppCompatActivity  {

	//private Button s_cancel,s_save;
	private EditText s_edtxt;
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
        initView();


        
    }

    @Override
    public void onBackPressed() {
        if("".equals(s_edtxt.getText().toString()))
        {
        }
        else{
            updateData();
            Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void initView() {
    	//s_delete = (Button) findViewById(R.id.s_deletebtn);
    	//s_cancel = (Button) findViewById(R.id.s_cancelbtn);
		//s_save = (Button) findViewById(R.id.s_savebtn);
    	s_edtxt = (EditText) findViewById(s_edtext);

    	notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
		s_edtxt.setText(getIntent().getStringExtra(NotesDB.CONTENT));

        s_edtxt. setCursorVisible ( false ) ;
        s_edtxt.setFocusable(true);
        s_edtxt.clearFocus();
        s_edtxt.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           s_edtxt. setCursorVisible (true) ;
                                           s_edtxt.setFocusable(true);
                                           s_edtxt.setFocusableInTouchMode(true);
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
                if("".equals(s_edtxt.getText().toString()))
                {
                }
                else{
                    updateData();
                    Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.trash:
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

	public void updateData() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.CONTENT,s_edtxt.getText().toString());
		cv.put(NotesDB.TIME, getTime());
		dbWriter.update(NotesDB.TABLE_NAME, cv, "_id="+getIntent().getIntExtra(NotesDB.ID, 0),null);
	}

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }
}
