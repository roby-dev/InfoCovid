package com.example.infocovid_proyecto.subvistas.cifras;

public class Country {
    private String country;
    private String flag;
    public Country(String country, int totalCases, int negativeCases, int casesConfirmed, int todayCases, int deaths, int todayDeaths, String letal, int recovered, int recoveredToday, int critical,String flag) {
        this.country = country;
        this.totalCases = totalCases;
        this.negativeCases = negativeCases;
        this.casesConfirmed = casesConfirmed;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.letal = letal;
        this.recovered = recovered;
        this.recoveredToday = recoveredToday;
        this.critical = critical;
        this.flag=flag;
    }

    public Country(String country,String flag){
        this.country = country;
        this.flag=flag;
    }

    private int totalCases;
    private int negativeCases;
    private int casesConfirmed;
    private int todayCases;
    private int deaths;
    private int todayDeaths;
    private String letal;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getNegativeCases() {
        return negativeCases;
    }

    public void setNegativeCases(int negativeCases) {
        this.negativeCases = negativeCases;
    }

    public int getCasesConfirmed() {
        return casesConfirmed;
    }

    public void setCasesConfirmed(int casesConfirmed) {
        this.casesConfirmed = casesConfirmed;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getLetal() {
        return letal;
    }

    public void setLetal(String letal) {
        this.letal = letal;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getRecoveredToday() {
        return recoveredToday;
    }

    public void setRecoveredToday(int recoveredToday) {
        this.recoveredToday = recoveredToday;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    private int recovered;
    private int recoveredToday;
    private int critical;




}
