package com.resoluciones.legajos.services;

import com.resoluciones.legajos.model.LoginBody;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by resoluciones on 13/2/15.
 */
public interface LoginService {

    @POST("/Login")
    void login(@Body LoginBody login, Callback<String> callback);

}
