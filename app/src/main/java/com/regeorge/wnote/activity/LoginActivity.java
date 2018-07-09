package com.regeorge.wnote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.login.EvernoteLoginFragment;
import com.regeorge.wnote.R;

/**
 * @author rwondratschek
 */
public class LoginActivity extends AppCompatActivity implements EvernoteLoginFragment.ResultCallback {

//    public static void launch(Activity activity) {
//        activity.startActivity(new Intent(activity, LoginActivity.class));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);

        setSupportActionBar(toolbar);
        EvernoteSession mEvernoteSession = EvernoteSession.getInstance();
        mEvernoteSession.authenticate(LoginActivity.this);

    }

    @Override
    public void onLoginFinished(boolean successful) {
        if (successful) {
            Toast.makeText(this, "成功绑定", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "取消绑定", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
