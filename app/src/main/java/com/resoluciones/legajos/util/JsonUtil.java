package com.resoluciones.legajos.util;

/**
 * Created by mgualino on 24/3/15.
 */
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonUtil {

    private static JsonUtil mInstance = null;
    private Gson mGson;

    protected JsonUtil() {
        mGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

    public static JsonUtil getInstance() {
        if (mInstance == null) {
            mInstance = new JsonUtil();
        }
        return mInstance;
    }

    public <T> T fromJson(String json, Class<T> classOfT) {

        return mGson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type type) {

        return mGson.fromJson(json, type);
    }

    public String toJson(Object src) {

        return mGson.toJson(src);
    }
}