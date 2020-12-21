package com.example.pokemonapp.pojo;

import java.util.ArrayList;

public class PokemonResponseModel {

    private int count;
    private  String next;
    private  String previous;
    private ArrayList<PokemonModel> results;

    public PokemonResponseModel(int count, String next, String previous, ArrayList<PokemonModel> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<PokemonModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokemonModel> results) {
        this.results = results;
    }
}
