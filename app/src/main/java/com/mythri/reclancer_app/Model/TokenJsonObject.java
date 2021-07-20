package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenJsonObject {
    public TokenJsonObject(String token) {
        this.token = token;
    }

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @Override
    public String toString() {
        return "TokenJsonObject{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +

                '}';
    }
}
