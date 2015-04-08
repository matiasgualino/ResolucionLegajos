package com.resoluciones.legajos.services;

import com.resoluciones.legajos.model.Legajo;
import com.resoluciones.legajos.model.PostLegajoBody;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by resoluciones on 9/2/15.
 */
public interface ActasService {

    @POST("/Legajo")
    void finalizarResolucion(@Body PostLegajoBody postLegajoBody, Callback<String> callback);

    @POST("/Suspender")
    void suspenderResolucion(@Body PostLegajoBody postLegajoBody, Callback<String> callback);

    @GET("/Legajo")
    void getLegajo(@Query("token") String accessToken, @Query("legajo") String legajoId, Callback<Legajo> callback);

    @GET("/Legajos")
    void getAllLegajos(@Query("token") String accessToken, Callback<List<Legajo>> callback);

}
