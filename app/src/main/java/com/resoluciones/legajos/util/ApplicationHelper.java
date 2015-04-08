package com.resoluciones.legajos.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.resoluciones.legajos.activities.MainActivity;
import com.resoluciones.legajos.activities.NoInternetActivity;
import com.resoluciones.legajos.model.Legajo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by resoluciones on 14/2/15.
 */
public class ApplicationHelper {

    public static String TAG = ApplicationHelper.class.getName();

    public static String API_BASE_URL = "api_base_url";

    private static ApplicationHelper instance;

    private Activity currentActivity;

    private Map<String, Object> map = new HashMap<String, Object>();

    private Legajo legajo;

    private static Context applicationContext;

    private Dialog networkingErrorDialog;

    private boolean[] solveSteps;

    private ApplicationHelper() {
        map.put(API_BASE_URL, "http://servicios.tech-mind.com/apiLegajo/api");
    }

    public static ApplicationHelper i() {
        if (instance == null) {
            instance = new ApplicationHelper();
        }
        return instance;
    }

    public static void init(Context c) {
        applicationContext = c.getApplicationContext();
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getString(String key) {
        return (String) map.get(key);
    }

    public void setLegajo(Legajo legajo) {
        this.legajo = legajo;
        this.solveSteps = new boolean[legajo.getActas().size()];
    }

    public Legajo getLegajo() {
        return legajo;
    }

    public boolean[] getSolveSteps(){
        return this.solveSteps;
    }

    public Dialog getNetworkingErrorDialog() {
        return networkingErrorDialog;
    }

    public void setNetworkingErrorDialog(Dialog networkingErrorDialog) {
        this.networkingErrorDialog = networkingErrorDialog;
    }

    public Context getContext() {
        return this.applicationContext;
    }

    public boolean isInitialized() {
        return applicationContext != null;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showHideNetworkErrorDialog(Context aContext, boolean isConnected) {
        if (isConnected) {
            if (networkingErrorDialog != null) {

                // go to new payment
                Intent intent = new Intent(aContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                aContext.startActivity(intent);
            }
        } else {

            Intent intent = new Intent(aContext, NoInternetActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            aContext.startActivity(intent);
        }
    }

}
