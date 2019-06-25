package com.company.jashan.mymoments.networking;

import com.company.jashan.mymoments.networking.UserImage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

class UrlAction {
    static final String IMAGE_URL_MAIN = "https://picsum.photos/";


    interface makeRequest {

        @GET("v2/list?page=1&limit=5")
        Call<ArrayList<UserImage>> getUserImage();


    }
}
