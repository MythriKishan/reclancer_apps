package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecLoginRes {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("jwt")
    @Expose
    private String jwt;



    public String getEmail() {
        return email;
    }

    public String getID() {
        return id;
    }
    public String getCode() {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getJwt(){return jwt;}

    @Override
    public String toString() {
        return "RecLoginRes{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }



}
