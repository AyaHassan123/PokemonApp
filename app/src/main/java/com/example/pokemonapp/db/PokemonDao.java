package com.example.pokemonapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemonapp.pojo.PokemonModel;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface PokemonDao {

    @Insert
    Completable insertPokemon(PokemonModel pokemon);

    @Query("delete from fav_table where name =:pokemonName")
     void deletePokemon(String pokemonName);

    @Query("select * from fav_table")
    Single<List<PokemonModel>> getPokemons();
}
