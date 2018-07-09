package com.regeorge.wnote.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgp on 2015/6/12.
 */
public class EverNoteUtils {

    private EvernoteSession mEvernoteSession;

    public static final String NOTE_BOOK_NAME = "SNotes";

    public EverNoteUtils() {
        mEvernoteSession = EvernoteSession.getInstance();
    }
    public boolean isLogin() {
        return mEvernoteSession != null && mEvernoteSession.isLoggedIn();
    }

    public void auth(Activity activity){
        if (activity == null)
            return;
        mEvernoteSession.authenticate(activity);
    }

    public void logout(){
        mEvernoteSession.logOut();
//        mPreferenceUtils.removeKey(PreferenceUtils.EVERNOTE_ACCOUNT_KEY);
    }

    public User getUser() throws Exception {
        return mEvernoteSession.getEvernoteClientFactory()
                .getUserStoreClient().getUser();
    }

    public void getUser(EvernoteCallback<User> callback) throws Exception {
        mEvernoteSession.getEvernoteClientFactory()
                .getUserStoreClient().getUserAsync(callback);
    }

    public String getUserAccount(User user) {
        if (user != null){
            String accountInfo = user.getEmail();
            if (!TextUtils.isEmpty(accountInfo)){
                return accountInfo;
            }else {
                accountInfo = user.getUsername();
            }
//            mPreferenceUtils.saveParam(PreferenceUtils.EVERNOTE_ACCOUNT_KEY, accountInfo);
            return accountInfo;
        }
        return "";
    }

    private Notebook findNotebook(String guid) throws Exception {
        Notebook notebook;
        try {
            notebook = mEvernoteSession.getEvernoteClientFactory()
                    .getNoteStoreClient().getNotebook(guid);

        } catch (EDAMNotFoundException e) {
            handleException(e);
            notebook = null;
        }
        return notebook;
    }

    private List<Notebook> listNotebooks() throws Exception {
        List<Notebook> books = new ArrayList<>();
        try {
            books = mEvernoteSession.getEvernoteClientFactory()
                    .getNoteStoreClient().listNotebooks();
        } catch (Exception e) {
            handleException(e);
        }
        return books;
    }


    public void expungeNote(String guid) throws Exception {
        if (TextUtils.isEmpty(guid))
            return;
        mEvernoteSession.getEvernoteClientFactory()
                .getNoteStoreClient().expungeNote(guid);
    }


    private void handleException(Exception e){
        if (e != null)
            e.printStackTrace();
    }

    public enum SyncResult{
        START,
        ERROR_NOT_LOGIN,
        ERROR_FREQUENT_API,
        ERROR_EXPUNGE,
        ERROR_DELETE,
        ERROR_RECOVER,
        ERROR_AUTH_EXPIRED,
        ERROR_PERMISSION_DENIED,
        ERROR_QUOTA_EXCEEDED,
        ERROR_OTHER,
        SUCCESS_SILENCE,
        SUCCESS
    }

    public enum SyncType{
        ALL,
        PULL,
        PUSH
    }
}
