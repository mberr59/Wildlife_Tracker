package com.example.wildlifetracker.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.reportRecycler);
        Repository repo = new Repository(getApplication());
        List<AnimalEntity> animals = repo.getAllAnimals();
        final AnimalAdapter adapter = new AnimalAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setAnimals(animals);
    }
}
