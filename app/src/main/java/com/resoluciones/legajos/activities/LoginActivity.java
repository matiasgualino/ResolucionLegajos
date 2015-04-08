package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.core.LoginConsumer;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.LayoutUtil;
import com.resoluciones.legajos.util.SaveSharedPreference;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity {

    protected Activity mActivity;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationHelper.init(this);
        if (SaveSharedPreference.isUserLogged(this) != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            setContentView(R.layout.activity_login);
            mActivity = this;

            // Set up the login form.
            mUsernameView = (EditText) findViewById(R.id.username);

            mLoginFormView = findViewById(R.id.regularLayout);
            //mProgressView = findViewById(R.id.login_progress);

            mPasswordView = (EditText) findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin(mLoginFormView);
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    public void attemptLogin(View view) {

        LayoutUtil.hideKeyboard(mActivity);

        LoginConsumer.login(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL), mUsernameView.getText().toString(), mPasswordView.getText().toString(), new Callback<String>() {
            @Override
            public void success(String loginResponse, Response response) {
                if (loginResponse != null && loginResponse != null) {
                    SaveSharedPreference.loginUser(mActivity, loginResponse, mUsernameView.getText().toString());
                } else {
                    new MaterialDialog.Builder(mActivity)
                            .title("ERROR")
                            .content("El nombre de usuario o contraseña no son correctos.")
                            .positiveText("OK")
                            .show();
                }
            }

            @Override
            public void failure(RetrofitError error) {

                new MaterialDialog.Builder(mActivity)
                        .title("ERROR")
                        .content("Ocurrió un error en el login. Por favor, vuelve a intentarlo nuevamente.")
                        .positiveText("OK")
                        .show();
            }
        });
    }
}