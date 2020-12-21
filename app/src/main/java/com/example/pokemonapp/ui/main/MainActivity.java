package com.example.pokemonapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.databinding.ActivityMainBinding;
import com.example.pokemonapp.pojo.PokemonModel;
import com.example.pokemonapp.viewModel.PokemonViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private PokemonAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        adapter = new PokemonAdapter(getApplicationContext());
        binding.pokemonRecyclerView.setAdapter(adapter);
        viewList();
        setupSwipe();

        binding.toFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
            }
        });
    }

    private void viewList(){
        pokemonViewModel.getPokemon();
        pokemonViewModel.pokemonList.observe(this, new Observer<ArrayList<PokemonModel>>() {
            @Override
            public void onChanged(ArrayList<PokemonModel> pokemonModels) {

                adapter.setList(pokemonModels);
                adapter.notifyDataSetChanged();
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
                pokemonViewModel.insertPokemon(swipedPokemon,getApplicationContext());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Pokemon Added to DB", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.pokemonRecyclerView);
    }
}