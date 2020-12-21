package com.example.pokemonapp.network;

import com.example.pokemonapp.pojo.PokemonResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("pokemon")
    Observable<PokemonResponseModel> getPokemon();
}
