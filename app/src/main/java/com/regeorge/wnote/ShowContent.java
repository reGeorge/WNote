package com.regeorge.wnote;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowContent extends Activity implements OnClickListener {

	private Button s_delete,s_cancel;
	private TextView s_tv;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcontent);
        //System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
        initView();
        s_tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        
        
    }
    
    public void initView() {
    	s_delete = (Button) findViewById(R.id.s_deletebtn);
    	s_cancel = (Button) findViewById(R.id.s_cancelbtn);
    	s_tv = (TextView) findViewById(R.id.s_tv);
    	notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase(); 
        s_delete.setOnClickListener(this);
        s_cancel.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		//i = new Intent(this,AddContent.class);
    	
    	switch(v.getId()) {
    	case R.id.s_deletebtn:
    		deleteData();
    		finish();
    		break;
    	case R.id.s_cancelbtn:
    		finish();
    		break;
    	}
	}
	public void deleteData() {
		dbWriter.delete(NotesDB.TABLE_NAME, "_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
	}
}
