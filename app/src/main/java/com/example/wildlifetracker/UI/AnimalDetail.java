package com.example.wildlifetracker.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.R;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalDetail extends AppCompatActivity {
    public static LatLng park = new LatLng(34.391442, -86.202289);
    EditText editName;
    EditText editType;
    EditText editLat;
    EditText editLong;
    EditText editNotes;
    Repository repo = new Repository(getApplication());
    long timeInMillis = Instant.now().toEpochMilli();
    int animalID;
    String animalName;
    String animalType;
    String animalLat;
    String animalLong;
    float monthlyTravelDistance;
    float dailyTravelDistance;
    String animalNotes;
    LocalDate currentDate = LocalDate.now();
    int dayOfTheMonth;
    int monthOfTheYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editName = findViewById(R.id.editAnimalName);
        editType = findViewById(R.id.editAnimalType);
        editLat = findViewById(R.id.editAnimalLat);
        editLong = findViewById(R.id.editAnimalLong);
        editNotes = findViewById(R.id.editAnimalNotes);
        dayOfTheMonth = currentDate.getDayOfMonth();
        monthOfTheYear = currentDate.getMonthValue();
        if (getIntent().getStringExtra("type") != null) {
            animalID = getIntent().getIntExtra("animalID", -1);
            animalName = getIntent().getStringExtra("name");
            animalType = getIntent().getStringExtra("type");
            animalLat = getIntent().getStringExtra("latitude");
            animalLong = getIntent().getStringExtra("longitude");
            monthlyTravelDistance = getIntent().getFloatExtra("distanceMonth", 0);
            dailyTravelDistance = getIntent().getFloatExtra("distanceDay", 0);
            animalNotes = getIntent().getStringExtra("notes");
            editName.setText(animalName);
            editType.setText(animalType);
            editLat.setText(animalLat);
            editLong.setText(animalLong);
            editNotes.setText(animalNotes);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animal_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent animalListIntent = new Intent(this, AnimalListActivity.class);
        int id = item.getItemId();
        List<AnimalEntity> currentAnimals = repo.getAllAnimals();

        if (id == R.id.action_save) {
            String currentAnimalName = editName.getText().toString();
            String currentAnimalType = editType.getText().toString();
            String currentAnimalLat = editLat.getText().toString();
            String currentAnimalLong = editLong.getText().toString();
            String currentAnimalNotes = editNotes.getText().toString();

            AnimalEntity newAnimal = new AnimalEntity(animalID, currentAnimalName, currentAnimalType, currentAnimalLat,
                    currentAnimalLong, monthlyTravelDistance, dailyTravelDistance, currentAnimalNotes, dayOfTheMonth,
                    monthOfTheYear);
            if (doesAnimalExist(currentAnimals, newAnimal)) {
                repo.updateAnimal(newAnimal);
            } else {
                repo.insertAnimal(newAnimal);
            }
            startActivity(animalListIntent);
            return true;
        } else if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AnimalDetail.this);
            for (AnimalEntity animal : currentAnimals) {
                if (animal.getAnimalID() == animalID) {
                    builder.setTitle("Delete this Animal?");
                    builder.setMessage("Are you sure you wish to delete the following animal: " + animal.getName());
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        repo.deleteAnimal(animal);
                        startActivity(animalListIntent);
                    });
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean doesAnimalExist(List<AnimalEntity> animalList, AnimalEntity currentAnimal) {
        int animalCheck = 0;
        for (AnimalEntity animal : animalList) {
            if (animal.getAnimalID() == currentAnimal.getAnimalID()) {
                animalCheck = 1;
                break;
            }
        }
        return animalCheck == 1;
    }

    public void openLocation(View view) {
        Intent intent = new Intent(AnimalDetail.this, MapsActivity.class);
        intent.putExtra("trackedName", animalName);
        intent.putExtra("coordinates", convertLocation(animalLat, animalLong));
        startActivity(intent);
    }

    public static double[] convertLocation(String latitude, String longitude) {
        double[] convertedCoordinates = new double[2];
        convertedCoordinates[0] = Double.parseDouble(latitude);
        convertedCoordinates[1] = Double.parseDouble(longitude);
        return convertedCoordinates;
    }

    public void areAnimalsInRange() {
        ArrayList<AnimalEntity> allAnimals = new ArrayList<>(repo.getAllAnimals());
        double[] location = new double[2];
        float[] distance = new float[1];
        for (AnimalEntity a : allAnimals) {
            location = convertLocation(a.getLatitude(), a.getLongitude());
            LatLng target = new LatLng(location[0], location[1]);
            Location.distanceBetween(park.latitude, park.longitude, target.latitude, target.longitude, distance);
            if (distance[0] > 3200.0) {
                Intent trackerNotif = new Intent(AnimalDetail.this, NotifReceiver.class);
                trackerNotif.putExtra("key", "Wildlife " + a.getName() + " is leaving range.");
                PendingIntent trackerSender = PendingIntent.getBroadcast(AnimalDetail.this, AnimalListActivity.numAlert++,
                       trackerNotif, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager trackerAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                trackerAlarmManager.set(AlarmManager.RTC_WAKEUP, 5000, trackerSender);
            }
        }

    }

    public void simAnimalMovement(View view) {
        ArrayList<AnimalEntity> allAnimals = new ArrayList<>(repo.getAllAnimals());
        List<ReportEntity> allReports = repo.getAllReports();
        LocalDate currentDate = LocalDate.now();
        double [] currentLocation;
        float[] distance = new float[1];
        LatLng endMovement;
        double maxLat = 34.422940;
        double maxLong = -86.173789;
        double minLat = 34.356645;
        double minLong = -86.226352;
        for (AnimalEntity a : allAnimals) {
            currentLocation = convertLocation(a.getLatitude(), a.getLongitude());
            LatLng currentLatLng = new LatLng(currentLocation[0], currentLocation[1]);
            do {
                double randomLat = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minLat, maxLat))
                        .setScale(6, RoundingMode.HALF_DOWN).doubleValue();
                double randomLong = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minLong, maxLong))
                        .setScale(6, RoundingMode.HALF_DOWN).doubleValue();
                LatLng movement = new LatLng(randomLat, randomLong);
                endMovement = movement;
                Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude,
                        movement.latitude, movement.longitude, distance);
            } while (distance[0] >= 200);
            a.setLatitude(Double.toString(endMovement.latitude));
            a.setLongitude(Double.toString(endMovement.longitude));
            if (currentDate.getMonthValue() == a.getMonthOfYear()) {
                for (ReportEntity report: allReports) {
                    if (report.getAnimalName().equals(a.getName())) {
                        report.setAnimalMonthlyTravel(report.getAnimalMonthlyTravel() + distance[0]);
                        repo.updateReport(report);
                    }
                }
                if (currentDate.getDayOfMonth() == a.getDayOfMonth()) {
                    for (ReportEntity report: allReports) {
                        if (report.getAnimalName().equals(a.getName())) {
                            report.setAnimalDailyTravel(report.getAnimalDailyTravel() + distance[0]);
                            repo.updateReport(report);
                        }
                    }
                } else {
                    for (ReportEntity report: allReports) {
                        if (report.getAnimalName().equals(a.getName())) {
                            report.setAnimalDailyTravel(0.0f + distance[0]);
                            repo.updateReport(report);
                        }
                    }
                }
            } else {
                for (ReportEntity report: allReports) {
                    if (report.getAnimalName().equals(a.getName())) {
                        report.setAnimalMonthlyTravel(0.0f + distance[0]);
                        repo.updateReport(report);
                    }
                }
            }
            repo.updateAnimal(a);
        }
        areAnimalsInRange();
    }
}
