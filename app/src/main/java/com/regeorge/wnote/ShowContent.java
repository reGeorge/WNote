package com.regeorge.wnote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.regeorge.wnote.R.id.s_edtext;


public class ShowContent extends Activity implements OnClickListener {

	private Button s_delete,s_cancel,s_save;
	private EditText s_edtxt;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcontent);
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
    	s_delete = (Button) findViewById(R.id.s_deletebtn);
    	s_cancel = (Button) findViewById(R.id.s_cancelbtn);
		s_save = (Button) findViewById(R.id.s_savebtn);
    	s_edtxt = (EditText) findViewById(s_edtext);
    	notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
		s_edtxt.setText(getIntent().getStringExtra(NotesDB.CONTENT));

        s_edtxt. setCursorVisible ( false ) ;
        s_edtxt.setFocusable(true);
        s_edtxt.clearFocus();
        //s_edtxt.setSelection(index);

        s_edtxt.setOnClickListener(this);
        s_save.setOnClickListener(this);
        s_delete.setOnClickListener(this);
        s_cancel.setOnClickListener(this);

    }

	@Override
	public void onClick(View v) {
		//i = new Intent(this,AddContent.class);
    	
    	switch(v.getId()) {
			case R.id.s_savebtn:
                if("".equals(s_edtxt.getText().toString()))
                {
                    Toast.makeText(getBaseContext(), "不能保存空笔记", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateData();
                    //Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
                }
                finish();
				break;
            case R.id.s_deletebtn:
                deleteData();
                finish();
                break;
            case R.id.s_cancelbtn:
                if("".equals(s_edtxt.getText().toString()))
                {
                }
                else{
                    updateData();
                    Toast.makeText(getBaseContext(), "已保存", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

            case R.id.s_edtext:
                s_edtxt. setCursorVisible (true) ;
                s_edtxt.setFocusable(true);
                s_edtxt.setFocusableInTouchMode(true);
                break;
    	}
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
