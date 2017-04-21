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

public class AddContent extends AppCompatActivity {
	//private Button savebtn,cancelbtn;
	private EditText a_edtxt;
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
		initView();
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

	public void initView() {
		a_edtxt = (EditText) findViewById(R.id.a_edtext);

		a_edtxt. setCursorVisible (true) ;
		a_edtxt.setFocusable(true);
		//a_edtxt.setFocusableInTouchMode(true);
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}
}
