package com.mythri.reclancer_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeSearch {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    public String getEmail() {
        return email;
    }

    public String getName() { return name;}

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FreeSearch{" +
                " email='" + email + '\'' +
                ", name='" +name + '\'' +
                '}';
    }
}
