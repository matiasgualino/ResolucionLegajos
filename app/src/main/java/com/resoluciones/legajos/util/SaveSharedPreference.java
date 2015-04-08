package com.resoluciones.legajos.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resoluciones.legajos.activities.LoginActivity;
import com.resoluciones.legajos.activities.MainActivity;
import com.resoluciones.legajos.model.Legajo;

/**
 * Created by resoluciones on 14/2/15.
 */
public class SaveSharedPreference {

    static final String PREF_USERNAME = "username";
    static final String PREF_ACCESS_TOKEN = "access_token";
    static final String PREF_LEGAJO = "legajo";

    static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
            .create();

    public static void saveLegajo(Context ctx, Legajo legajo) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx.getApplicationContext()).edit();
        Gson gson = new Gson();
        String json = gson.toJson(legajo);
        editor.putString(PREF_LEGAJO, json);
        editor.commit();
    }

    public static void deleteLegajo(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx.getApplicationContext()).edit();
        editor.remove(PREF_LEGAJO);
        editor.commit();
        editor.apply();
    }

    public static Legajo getLegajo(Context ctx) {
        Gson gson = new Gson();
        String json = getSharedPreferences(ctx.getApplicationContext()).getString(PREF_LEGAJO, null);
        return gson.fromJson(json, Legajo.class);
    }

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
    }

    public static void setUserLogged(Context ctx, String accessToken) {
        String serializedToken = null;
        if (accessToken != null) {
            serializedToken = gson.toJson(accessToken);
        }
        SharedPreferences.Editor editor = getSharedPreferences(ctx.getApplicationContext()).edit();
        editor.putString(PREF_ACCESS_TOKEN, serializedToken);
        editor.commit();
    }

    public static String getAccessToken(Context ctx) {
        String accessToken = getSharedPreferences(ctx.getApplicationContext()).getString(PREF_ACCESS_TOKEN, null);
        return accessToken.replace("\"", "");
    }

    public static void setUsername(Context ctx, String username) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx.getApplicationContext()).edit();
        editor.putString(PREF_USERNAME, username);
        editor.commit();
    }

    public static String getUsername(Context ctx) {
        String username = getSharedPreferences(ctx.getApplicationContext()).getString(PREF_USERNAME, null);
        return username;
    }

    public static String isUserLogged(Context ctx) {

        String serializedToken = getSharedPreferences(ctx.getApplicationContext()).getString(PREF_ACCESS_TOKEN, null);
        if (serializedToken != null) {
            return gson.fromJson(serializedToken, String.class);
        } else {
            return null;
        }
    }

    public static void logoutUser(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx.getApplicationContext()).edit();
        editor.clear();
        editor.commit();

        Intent i = new Intent(ctx.getApplicationContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    public static void loginUser(Context ctx, String accessToken, String username){

        setUsername(ctx.getApplicationContext(), username);
        setUserLogged(ctx.getApplicationContext(), accessToken);

        Intent i = new Intent(ctx.getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.getApplicationContext().startActivity(i);
    }

}
