package com.example.wildlifetracker.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.R;

import java.util.List;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int reportLoadID = getIntent().getIntExtra("reportLoadID", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.reportRecycler);
        Repository repo = new Repository(getApplication());
        if (reportLoadID == 1) {
            List<DailyEntity> allDailyReports = repo.getDailyReports();
            final DailyAdapter adapter = new DailyAdapter(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.setDailyReport(allDailyReports);
        } else if (reportLoadID == 2) {
            List<MonthlyEntity> allMonthlyReports = repo.getMonthlyReports();
            final MonthlyAdapter adapter = new MonthlyAdapter(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.setMonthlyReports(allMonthlyReports);
        }
    }
}
