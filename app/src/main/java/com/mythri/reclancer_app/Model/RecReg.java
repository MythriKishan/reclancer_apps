package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecReg {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cpass")
    @Expose
    private String cpassword;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("num")
    @Expose
    private String num;


    public String getEmail() {
        return email;
    }

    public int getID() {
        return id;
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
        return "RecReg{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
