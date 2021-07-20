package com.mythri.reclancer_app;


import android.app.Application;

public class Globals extends Application {

    public String Stoken;

    private static Globals singleton;

    public static Globals getInstance() {
        return singleton;
    }

    public String getToken() {
        return Stoken;
    }

    public void setToken(String Stoken) {
        this.Stoken = Stoken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
