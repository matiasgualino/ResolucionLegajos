package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.ConnectionDetector;
import com.resoluciones.legajos.util.CustomExceptionHandler;

/**
 * Created by resoluciones on 16/2/15.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(getApplicationContext()));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationHelper.i().setCurrentActivity(this);
        checkConnection();
    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_slide_out_right);
    }

    private void clearReferences() {
        Activity currActivity = ApplicationHelper.i().getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            ApplicationHelper.i().setCurrentActivity(null);
    }

    private void checkConnection() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (!connectionDetector.isConnectingToInternet()) {
            ApplicationHelper.i().showHideNetworkErrorDialog(this, false);
        }
    }

}