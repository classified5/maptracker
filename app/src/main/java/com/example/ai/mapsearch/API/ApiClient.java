package com.example.ai.mapsearch.API;

import com.example.ai.mapsearch.Utils.Constant;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by A.I on 25/11/2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit createClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
