package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecAdStatus {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("ad_id")
    @Expose
    private int ad_id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

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

    @Override
    public String toString() {
        return "RecAdStatus{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +

                '}';
    }
}
