package com.regeorge.wnote;

import android.app.Application;

import com.evernote.client.android.EvernoteSession;

/**
 * Created by reGeorge on 2018/5/2.
 */

public class WNoteAPP extends Application {
    /*
     * ********************************************************************
     * You MUST change the following values to run this sample application.
     *
     * It's recommended to pass in these values via gradle property files.
     * ********************************************************************
     */

    /*
     * Your Evernote API key. See http://dev.evernote.com/documentation/cloud/
     * Please obfuscate your code to help keep these values secret.
     */
    private static final String CONSUMER_KEY = "regeorge";
    private static final String CONSUMER_SECRET = "49714c14c6bcb562";

    /*
     * Initial development is done on Evernote's testing service, the sandbox.
     *
     * Change to PRODUCTION to use the Evernote production service
     * once your code is complete.
     */
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

    /*
     * Set this to true if you want to allow linked notebooks for accounts that
     * can only access a single notebook.
     */
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = true;

    @Override
    public void onCreate() {
        super.onCreate();

        //Set up the Evernote singleton session, use EvernoteSession.getInstance() later
        new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .setForceAuthenticationInThirdPartyApp(true)
//                .setLocale(Locale.SIMPLIFIED_CHINESE)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();

//        registerActivityLifecycleCallbacks(new LoginChecker());
    }
}
