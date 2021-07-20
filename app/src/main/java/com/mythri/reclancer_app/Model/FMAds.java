package com.mythri.reclancer_app.Model;

public class FMAds {

    private String id;
    private String email;
    private String ad_id;
    private String category;


    public String getId() {return id;}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setid(String id) {this.id=id;}



    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
