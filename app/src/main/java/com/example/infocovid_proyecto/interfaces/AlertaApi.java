package com.example.infocovid_proyecto.interfaces;

import com.example.infocovid_proyecto.models.Alertas;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//https://disease.sh/v3/covid-19/countries?yesterday=true

public interface AlertaApi {
    @GET("v3/covid-19/countries?yesterday=true")
    public Call<List<JsonElement>> getAll();

    @GET("v3/covid-19/countries/{country}?yesterday=true")
    public Call<Alertas> getBy(@Path("country") String country);
}
