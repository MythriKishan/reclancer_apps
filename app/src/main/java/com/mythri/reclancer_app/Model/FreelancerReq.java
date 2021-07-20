package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreelancerReq {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("num")
    @Expose
    private String num;

    public int getID() {
        return id;
    }

    public String getMail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getRandnum() {return num;}

    @Override
    public String toString() {
        return "FreelancerReq{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

}