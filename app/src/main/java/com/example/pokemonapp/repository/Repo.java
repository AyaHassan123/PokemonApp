package com.example.pokemonapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapp.db.PokemonDB;
import com.example.pokemonapp.di.RetrofitModule;
import com.example.pokemonapp.pojo.PokemonModel;
import com.example.pokemonapp.pojo.PokemonResponseModel;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Repo {

   public MutableLiveData<ArrayList<PokemonModel>> pokemonList = new MutableLiveData<>();
   public   MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<List<PokemonModel>> favList = new MutableLiveData<>();
    public static Repo repo;

    public static synchronized  Repo getInstance() {
        if(repo ==null){
            repo = new Repo();
        }
        return  repo;
    }

    public void getPokemon(){
        RetrofitModule.getService()
                .getPokemon()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponseModel, ArrayList<PokemonModel>>() {
                    @Override
                    public ArrayList<PokemonModel> apply(PokemonResponseModel pokemonResponseModel) throws Throwable {

                        ArrayList<PokemonModel> list = pokemonResponseModel.getResults();
                        for(PokemonModel pokemonModel :list){
                            String url = pokemonModel.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemonModel.setUrl("https://pokeres.bastionbot.org/images/pokemon/"
                                    + pokemonIndex[pokemonIndex.length - 1] + ".png");
                        }
                        return  list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<PokemonModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ArrayList<PokemonModel> pokemonModels) {

                        Repo.getInstance().pokemonList.setValue(pokemonModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Repo.getInstance().error.setValue(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void insertPokemon(PokemonModel pokemon , Context context) {
        PokemonDB.getInstance(context)
                .pokemonDao()
                .insertPokemon(pokemon)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull io.reactivex.disposables.Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }
                });
    }
    public void deletePokemon(String pokemonName , Context context){
        PokemonDB.getInstance(context)
        .pokemonDao()
        .deletePokemon(pokemonName);
        ;}
    public void getFavPokemon(Context context) {
      PokemonDB.getInstance(context)
              .pokemonDao()
              .getPokemons()
              .subscribeOn(io.reactivex.schedulers.Schedulers.io())
              .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
              .subscribe(new SingleObserver<List<PokemonModel>>() {
                  @Override
                  public void onSubscribe(@io.reactivex.annotations.NonNull io.reactivex.disposables.Disposable d) {

                  }

                  @Override
                  public void onSuccess(@io.reactivex.annotations.NonNull List<PokemonModel> pokemonModels) {

                      Repo.getInstance().favList.setValue(pokemonModels);
                  }

                  @Override
                  public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                  }
              });
    }

}
