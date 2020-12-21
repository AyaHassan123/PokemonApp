package com.example.pokemonapp.pojo;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_table")

public class PokemonModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private  String name;
    private  String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
