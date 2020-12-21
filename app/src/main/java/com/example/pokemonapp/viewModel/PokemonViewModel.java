package com.example.pokemonapp.viewModel;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokemonapp.pojo.PokemonModel;
import com.example.pokemonapp.repository.Repo;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends ViewModel {

    public  MutableLiveData<ArrayList<PokemonModel>> pokemonList = new MutableLiveData<>();
    public   MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<List<PokemonModel>> favList = new MutableLiveData<>();

    private Repo repo;

    public PokemonViewModel() {
        repo = Repo.getInstance();
        pokemonList = repo.pokemonList ;
        favList = repo.favList;
        error = repo.error;
    }

 public void  getPokemon(){
        repo.getPokemon();
 }

    public void insertPokemon(PokemonModel pokemon, Context context) {
        repo.insertPokemon(pokemon ,context);
    }

    public void deletePokemon(String pokemonName, Context context) {
        repo.deletePokemon(pokemonName ,context );
    }

    public void getFavPokemon(Context context) {
       repo.getFavPokemon(context);
    }

}
