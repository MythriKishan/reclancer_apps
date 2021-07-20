package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FProfileUpdate {
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

    @Override
    public String toString() {
        return "FProfileUpdate{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
