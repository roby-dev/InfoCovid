package com.example.infocovid_proyecto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alertas {
    private String country;
    private String flag;
    private double lat;
    @SerializedName("long")
    @Expose
    private double Long;
    private int cases;
    private int todayCases;
    private int death;
    private int todayDeath;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getTodayDeath() {
        return todayDeath;
    }

    public void setTodayDeath(int todayDeath) {
        this.todayDeath = todayDeath;
    }
}
