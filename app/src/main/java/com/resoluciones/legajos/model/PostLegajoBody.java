package com.resoluciones.legajos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by resoluciones on 10/3/15.
 */
public class PostLegajoBody {
    @SerializedName("Token")
    private String Token;
    @SerializedName("JsonLegajo")
    private Legajo JsonLegajo;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token.replace("\\", "");
    }

    public Legajo getJsonLegajo() {
        return JsonLegajo;
    }

    public void setJsonLegajo(Legajo jsonLegajo) {
        JsonLegajo = jsonLegajo;
    }
}
