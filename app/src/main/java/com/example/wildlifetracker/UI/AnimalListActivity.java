package com.example.wildlifetracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class AnimalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.animalRecycler);
        Repository repo = new Repository(getApplication());
        List<AnimalEntity> animals = repo.getAllAnimals();
        final AnimalAdapter adapter = new AnimalAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setAnimals(animals);
    }



    public void loadAnimalDetail(View view) {
        Intent intent = new Intent(AnimalListActivity.this, AnimalDetail.class);
        startActivity(intent);
    }


}
