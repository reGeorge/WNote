package com.regeorge.wnote.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.regeorge.wnote.R;
import com.regeorge.wnote.database.NotesDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContent extends AppCompatActivity {
	//private Button savebtn,cancelbtn;
	private EditText a_edtxt;
	private EditText a_time;
	//private String val;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		Toolbar toolbar = (Toolbar) findViewById(R.id.a_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			//actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
		}
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
		try {
			initView();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		if("".equals(a_edtxt.getText().toString()))
		{
		}
		else {
			addData();
			//Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

	public void initView() throws ParseException {
		a_edtxt = (EditText) findViewById(R.id.a_edtext);
		a_time = (EditText) findViewById(R.id.a_time);

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date date = format.parse(getTime());
		format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		String time = format.format(date);
		a_time.setText(time);

		a_time. setCursorVisible ( false ) ;
		a_time.setFocusable(false);
		a_time.clearFocus();

		a_edtxt. setCursorVisible (true) ;
		//a_edtxt.setFocusable(true);
		a_edtxt.setFocusableInTouchMode(true);
		//a_edtxt.setSelection(a_edtxt.getText().toString().length());
	}



	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add,menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
			case R.id.check:
				if("".equals(a_edtxt.getText().toString()))
				{
					//Toast.makeText(getBaseContext(), "不能保存空笔记", Toast.LENGTH_SHORT).show();
				}
				else {
					addData();
					//Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
				}
				finish();
				break;
			default:
		}
		return true;

	}
	public void addData() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.CONTENT,a_edtxt.getText().toString());
		cv.put(NotesDB.TIME, getTime());
		dbWriter.insert(NotesDB.TABLE_NAME, null, cv);
	}

	private String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}
}
