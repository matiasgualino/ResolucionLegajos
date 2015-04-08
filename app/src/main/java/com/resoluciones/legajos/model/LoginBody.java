package com.resoluciones.legajos.model;

/**
 * Created by resoluciones on 13/2/15.
 */
public class LoginBody {
    private String Username;
    private String Password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }
}
