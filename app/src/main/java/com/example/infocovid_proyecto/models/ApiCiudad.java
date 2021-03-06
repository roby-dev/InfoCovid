package com.example.infocovid_proyecto.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCiudad {
    public static final String BASE_URL ="http://192.168.0.15:3000/api/cases/city/";
    public static Retrofit retrofit;
    public static OkHttpClient okHttpClient;
    public static Gson gson;

    public static Retrofit getRetrofitClient(){
        if(retrofit==null){
            gson=new GsonBuilder().setLenient().create();
            okHttpClient = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
