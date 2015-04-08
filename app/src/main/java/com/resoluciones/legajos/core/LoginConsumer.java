package com.resoluciones.legajos.core;

import android.os.StrictMode;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resoluciones.legajos.model.LoginBody;
import com.resoluciones.legajos.services.LoginService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by resoluciones on 13/2/15.
 */
public class LoginConsumer {

    public static void login(String baseUrl, String username, String password, Callback<String> callback) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(getGson())).build();
        LoginService service = restAdapter.create(LoginService.class);
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername(username);
        loginBody.setPassword(password);
        service.login(loginBody, callback);
    }

    private static Gson getGson() {

        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

}
