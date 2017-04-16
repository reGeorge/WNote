package com.regeorge.wnote;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContent extends Activity implements OnClickListener{
	private Button savebtn,cancelbtn;
	private EditText edtext;
	//private String val;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		//val = getIntent().getStringExtra("flag");
		//Toast.makeText(getBaseContext(), val, Toast.LENGTH_SHORT ).show();
		initView();
	}

	@Override
	public void onBackPressed() {
		if("".equals(edtext.getText().toString()))
		{
		}
		else {
			addData();
			Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

	public void initView() {
		savebtn =  (Button) findViewById(R.id.a_save);
		cancelbtn =  (Button) findViewById(R.id.a_cancel);
		edtext = (EditText) findViewById(R.id.a_edtext);
		savebtn.setOnClickListener(this);
		cancelbtn.setOnClickListener(this);
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.a_save:
				//用equals判断输入，trim()--头尾空白被滤掉
				if("".equals(edtext.getText().toString()))
				{
					Toast.makeText(getBaseContext(), "不能保存空笔记", Toast.LENGTH_SHORT).show();
				}
				else {
					addData();
					//Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
				}
				finish();
				break;
			case R.id.a_cancel:
				if("".equals(edtext.getText().toString()))
				{
				}
				else {
					addData();
					Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
				}
				finish();
				break;
		}
	}

	public void addData() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.CONTENT,edtext.getText().toString());
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
