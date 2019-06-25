package com.company.jashan.mymoments.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.company.jashan.mymoments.networking.UrlAction.IMAGE_URL_MAIN;

public class ApiClient {

    Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(IMAGE_URL_MAIN).client(new OkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }


}
