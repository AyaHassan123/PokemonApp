package com.example.pokemonapp.di;

import com.example.pokemonapp.network.ApiServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitModule {

    private static final String BASE_URl = "https://pokeapi.co/api/v2/";

    public  static synchronized ApiServices providerApiServices(){
        return  new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiServices.class);
    }

    public static ApiServices getService() {
        return providerApiServices();
    }
}
