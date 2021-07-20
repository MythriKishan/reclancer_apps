package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rec_mobval {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString() {
        return "Rec_mobval{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +

                '}';
    }

}
