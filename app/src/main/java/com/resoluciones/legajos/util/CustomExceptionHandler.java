package com.resoluciones.legajos.util;

/**
 * Created by resoluciones on 14/2/15.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.analytics.StandardExceptionParser;
import com.resoluciones.legajos.activities.LoginActivity;


public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CustomExceptionHandler.class.getName();

    private Context context;

    public CustomExceptionHandler(Context context) {
        this.context = context;
    }

    public void uncaughtException(Thread t, Throwable e) {

        Log.e(TAG, "Uncaught error", e);

        StandardExceptionParser exceptionParser =
                new StandardExceptionParser(context, null) {
                    @Override
                    public String getDescription(String threadName, Throwable t) {
                        return "{" + threadName + "} " + Log.getStackTraceString(t);
                    }
                };

        Intent mStartActivity = new Intent(context, LoginActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

//        SaveSharedPreference.setErrorString(context, "Se produjo un error se reinicio la app y se estan enviando los datos para su analisis");
  //      SaveSharedPreference.setErrorTrace(context, exceptionParser.getDescription(Thread.currentThread().getName(), e));

        System.exit(0);

    }
}