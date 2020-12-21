package com.example.pokemonapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemonapp.pojo.PokemonModel;

@Database(entities = PokemonModel.class, version = 1, exportSchema = false)

public abstract class PokemonDB extends RoomDatabase {

    private static PokemonDB instance;
    public abstract PokemonDao pokemonDao();

    public static synchronized PokemonDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PokemonDB.class, "pokemons")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
