package com.mythri.reclancer_app.Model;

public class RMAds {
    private String id;
    private String email;
    private String ad_id;
    private String category;
    private String name;
    private String skills;
    private String exp;


    public String getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

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
