package com.example.pokemonapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.databinding.ActivityFavouriteBinding;
import com.example.pokemonapp.pojo.PokemonModel;
import com.example.pokemonapp.viewModel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private ActivityFavouriteBinding binding;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_favourite);
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        adapter = new PokemonAdapter(getApplicationContext());
        binding.favRecylerview.setAdapter(adapter);

        viewList();
        setupSwipe();
        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavouriteActivity.this, MainActivity.class));

            }
        });

    }

    private void viewList(){
        pokemonViewModel.getFavPokemon(getApplicationContext());
        pokemonViewModel.favList.observe(this, new Observer<List<PokemonModel>>() {
            @Override
            public void onChanged(List<PokemonModel> pokemonModels) {
                ArrayList<PokemonModel> favPokemon = new ArrayList<>();
                favPokemon.addAll(pokemonModels);
                adapter.setList(favPokemon);
            }
        });
    }
    private void setupSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                PokemonModel swipedPokemon = adapter.getPokemonAt(swipedPokemonPosition);
                pokemonViewModel.deletePokemon(swipedPokemon.getName(),getApplicationContext());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavouriteActivity.this, "pokemon deleted from database", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.favRecylerview);
    }
}
