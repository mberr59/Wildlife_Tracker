package com.example.wildlifetracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalListActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchView searchView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.animalRecycler);
        Repository repo = new Repository(getApplication());
        List<AnimalEntity> animals = repo.getAllAnimals();
        final AnimalAdapter adapter = new AnimalAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setAnimals(animals);
        checkThatReportExist();
    }

    private void filterList(String text) {
        Repository repo = new Repository(getApplication());
        List<AnimalEntity> allAnimals = repo.getAllAnimals();
        List<AnimalEntity> filteredList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.animalRecycler);
        final AnimalAdapter adapter = new AnimalAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter((adapter));

        for (AnimalEntity animal : allAnimals) {
            if (animal.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(animal);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No wildlife match search", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }


    public void loadAnimalDetail(View view) {
        Intent intent = new Intent(AnimalListActivity.this, AnimalDetail.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animal_list, menu);
        return true;
    }

    public void checkThatReportExist() {
        Repository repo = new Repository(getApplication());
        boolean animalFound = false;
        List<AnimalEntity> allAnimals = repo.getAllAnimals();
        List<ReportEntity> allAnimalsReport = repo.getAllReports();
        for (AnimalEntity animal : allAnimals) {
            for (ReportEntity animalReport : allAnimalsReport) {
                if (animalReport.getAnimalName().equals(animal.getName())) {
                    animalFound = true;
                    break;
                }
            }
            if (!animalFound) {
                Date currentDate = new Date(System.currentTimeMillis());
                ReportEntity newAnimal = new ReportEntity(animal.getAnimalID(),
                        animal.getName(), animal.getType(), currentDate);
                repo.insertReport(newAnimal);
            }
            animalFound = false;
        }
    }
}
