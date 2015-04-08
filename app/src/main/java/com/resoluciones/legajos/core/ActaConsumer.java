package com.resoluciones.legajos.core;

import android.os.StrictMode;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resoluciones.legajos.model.Legajo;
import com.resoluciones.legajos.model.PostLegajoBody;
import com.resoluciones.legajos.services.ActasService;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.SaveSharedPreference;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by resoluciones on 9/2/15.
 */
public class ActaConsumer {

    public static void getAllLegajos(Callback<List<Legajo>> callback) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(getGson()))
                .build();
        ActasService service = restAdapter.create(ActasService.class);
        String accessToken = SaveSharedPreference.getAccessToken(ApplicationHelper.i().getContext());
        service.getAllLegajos(accessToken, callback);
    }

    public static void getLegajo(String legajoId, Callback<Legajo> callback) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL)).setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(getGson())).build();
        ActasService service = restAdapter.create(ActasService.class);
        String accessToken = SaveSharedPreference.getAccessToken(ApplicationHelper.i().getContext());
        service.getLegajo(accessToken, legajoId, callback);
    }

    public static void finalizarResolucion(Legajo legajo, Callback<String> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL)).setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(getGson())).build();
        ActasService service = restAdapter.create(ActasService.class);

        PostLegajoBody postLegajoBody = new PostLegajoBody();
        postLegajoBody.setToken(SaveSharedPreference.getAccessToken(ApplicationHelper.i().getContext()));
        postLegajoBody.setJsonLegajo(legajo);
        service.finalizarResolucion(postLegajoBody, callback);
    }

    public static void suspenderResolucion(Legajo legajo, Callback<String> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL)).setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(getGson())).build();
        ActasService service = restAdapter.create(ActasService.class);

        PostLegajoBody postLegajoBody = new PostLegajoBody();
        postLegajoBody.setToken(SaveSharedPreference.getAccessToken(ApplicationHelper.i().getContext()));
        postLegajoBody.setJsonLegajo(legajo);
        service.suspenderResolucion(postLegajoBody, callback);
    }

    private static Gson getGson() {

        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

}
