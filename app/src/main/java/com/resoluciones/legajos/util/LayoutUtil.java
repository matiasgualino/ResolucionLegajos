package com.resoluciones.legajos.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.resoluciones.legajos.R;

/**
 * Created by resoluciones on 14/2/15.
 */
public class LayoutUtil {

    public static void hideKeyboard(Activity activity) {

        try {
            EditText editText = (EditText) activity.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        catch (Exception ex) {
        }
    }

    public static void showProgress(Activity activity) {
        showLayout(activity, true, false, false);
    }

    public static void showRegularLayout(Activity activity) {
        showLayout(activity, false, true, false);
    }

    private static void showLayout(Activity activity, final boolean showProgress, final boolean showLayout, final boolean refresh) {

        final View status = activity.findViewById(R.id.cs_status);
        final View form = activity.findViewById(R.id.regularLayout);

        int shortAnimTime = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);

        if(status != null) {
            status.setVisibility(refresh || showLayout ? View.GONE : View.VISIBLE);
            status.animate()
                    .setDuration(shortAnimTime)
                    .alpha(showProgress ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            status.setVisibility(!refresh && showProgress ? View.VISIBLE : View.GONE);
                        }
                    });
        }

        if(form != null) {
            form.setVisibility(refresh || showProgress ? View.GONE : View.VISIBLE);
            form.animate()
                    .setDuration(shortAnimTime)
                    .alpha(showLayout ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            form.setVisibility(!refresh && showLayout ? View.VISIBLE : View.GONE);
                        }
                    });
        }
    }
}
