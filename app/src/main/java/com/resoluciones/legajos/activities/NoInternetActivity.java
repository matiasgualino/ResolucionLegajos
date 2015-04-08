package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.SaveSharedPreference;

public class NoInternetActivity extends Activity {

    private static String TAG = NoInternetActivity.class.getName();

    private Typeface mFontAwesome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting ");
        setContentView(R.layout.activity_no_internet);

        TextView textView = (TextView) findViewById(R.id.refresh_icon);
        textView.setText("No hay conexión a internet. Haga click en \"Reintentar\".");
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public void setFontAwesomeToTextView(TextView textView) {
        if (mFontAwesome == null) {
            mFontAwesome = Typeface.createFromAsset(ApplicationHelper.i().getContext().getAssets(), "fontawesome-webfont.ttf");
        }
        if (textView != null) {
            textView.setTypeface(mFontAwesome);
        }
    }

    public void refresh(View v){
        if ( ApplicationHelper.i().isOnline() ) {
            Intent intent = new Intent(NoInternetActivity.this, LoginActivity.class);
            if (SaveSharedPreference.isUserLogged(ApplicationHelper.i().getContext()) != null) {
                intent = new Intent(NoInternetActivity.this, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(null);
    }
}