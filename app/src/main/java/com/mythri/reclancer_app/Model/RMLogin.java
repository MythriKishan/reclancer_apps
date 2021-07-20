package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RMLogin {
    @SerializedName("mobile")
    @Expose
    private String mobile;

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

    @SerializedName("num")
    @Expose
    private String num;


    public String getMobile() {
        return mobile;
    }

    public String getID() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRandnum() {
        return num;
    }


    @Override
    public String toString() {
        return "RMLogin{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", mobile='" + mobile + '\'' +
                ", id='" + id + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

}