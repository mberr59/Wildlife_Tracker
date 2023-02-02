package com.example.wildlifetracker.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.R;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * On Create method for the Animal Details screen. This method is responsible for pulling the data from the
     * database into the correct fields if the animal is in the database else will just leave the fields blank.
     */
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

    /**
     * Inflates the Options menu with the options saved into R.menu.menu_animal_detail file
     */
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animal_detail, menu);
        return true;
    }

    /**
     * Checks to see which item is selected in the Options menu.
     * Once determined, houses the logic depending on the selected item.
     * The save option attempts to store the input into SQLite database.
     * The delete option attempts to delete the designated animal from the SQLite database.
     * Also provides a user confirmation screen to verify that the user wishes to delete the animal from
     * the database.
     * @param item Options item that is selected by the user.
     */
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
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        repo.deleteAnimal(animal);
                        startActivity(animalListIntent);
                    });
                    builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Simple check method to determine if the selected animal already exist within the database.
     */
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

    /**
     * This method takes in the selected animal's latitude and longitude and passes the
     * coordinates to the Maps Activity.
     */
    public void openLocation(View view) {
        Intent intent = new Intent(AnimalDetail.this, MapsActivity.class);
        intent.putExtra("trackedName", animalName);
        intent.putExtra("coordinates", convertLocation(animalLat, animalLong));
        startActivity(intent);
    }

    /**
     * This method takes the latitude and longitude coordinates stored in the SQLite database as strings
     * and converts them into a double. The two doubles are then stored within a double array which is
     * later passed to the Maps Activity.
     * @param latitude string latitude coordinate stored within the database.
     * @param longitude string longitude coordinate stored within the database.
     * @return returns a double array with latitude point stored in index 0 and longitude point stored in
     * index 1.
     */
    public static double[] convertLocation(String latitude, String longitude) {
        double[] convertedCoordinates = new double[2];
        convertedCoordinates[0] = Double.parseDouble(latitude);
        convertedCoordinates[1] = Double.parseDouble(longitude);
        return convertedCoordinates;
    }

    /**
     * This method checks to see if the animals are still within the range of the park. It does this by check the
     * distance between a central point within the park and the target's current location. If the distance exceeds
     * 3500 meters, a notification is displayed on the phone letting the users know that the animal has moved
     * out of the park's range.
     */
    public void areAnimalsInRange() {
        ArrayList<AnimalEntity> allAnimals = new ArrayList<>(repo.getAllAnimals());
        double[] location;
        float[] distance = new float[1];
        for (AnimalEntity a : allAnimals) {
            location = convertLocation(a.getLatitude(), a.getLongitude());
            LatLng target = new LatLng(location[0], location[1]);
            Location.distanceBetween(park.latitude, park.longitude, target.latitude, target.longitude, distance);
            if (distance[0] > 3500.0) {
                Intent trackerNotif = new Intent(AnimalDetail.this, NotifReceiver.class);
                trackerNotif.putExtra("key", "Wildlife " + a.getName() + " is leaving range.");
                PendingIntent trackerSender = PendingIntent.getBroadcast(AnimalDetail.this, AnimalListActivity.numAlert++,
                       trackerNotif, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager trackerAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                trackerAlarmManager.set(AlarmManager.RTC_WAKEUP, 5000, trackerSender);
            }
        }

    }

    /**
     * For this project, I didn't have access to actual tracking devices so I created this method to
     * simulate the movement of the animals. For each animal in the list it creates a random latitude and
     * longitude point for the animal. It then checks to see if the random position is <= 500 meters from
     * the current animal's location. If this returns true, the animal's location is then updated with this
     * new random point. The areAnimalsInRange() method is then called after to verify that each animal is
     * still within the park's range.
     */
    public void simAnimalMovement(View view) {
        ArrayList<AnimalEntity> allAnimals = new ArrayList<>(repo.getAllAnimals());
        AlertDialog.Builder builder = new AlertDialog.Builder(AnimalDetail.this);
        List<MonthlyEntity> allMonthlyReports = repo.getMonthlyReports();
        List<DailyEntity> allDailyReports = repo.getDailyReports();
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
            } while (distance[0] >= 500);
            a.setLatitude(Double.toString(endMovement.latitude));
            a.setLongitude(Double.toString(endMovement.longitude));
            if (currentDate.getMonthValue() == a.getMonthOfYear()) {
                for (MonthlyEntity report: allMonthlyReports) {
                    if (report.getAnimalName().equals(a.getName())) {
                        report.setAnimalMonthlyTravel(report.getAnimalMonthlyTravel() + distance[0]);
                        a.setDistanceMonth(a.getDistanceMonth() + distance[0]);
                        repo.updateMonthlyReport(report);
                        repo.updateReport(report);
                    }
                }
                if (currentDate.getDayOfMonth() == a.getDayOfMonth()) {
                    for (DailyEntity report: allDailyReports) {
                        if (report.getAnimalName().equals(a.getName())) {
                            report.setAnimalDailyTravel(report.getAnimalDailyTravel() + distance[0]);
                            a.setDistanceDay(a.getDistanceDay() + distance[0]);
                            repo.updateDailyReport(report);
                            repo.updateReport(report);
                        }
                    }
                } else {
                    for (DailyEntity report: allDailyReports) {
                        if (report.getAnimalName().equals(a.getName())) {
                            report.setAnimalDailyTravel((0.0f * distance[0]) + distance[0]);
                            a.setDistanceDay((0.0f * distance[0]) + distance[0]);
                            repo.updateDailyReport(report);
                            repo.updateReport(report);
                        }
                    }
                }
            } else {
                for (MonthlyEntity report: allMonthlyReports) {
                    if (report.getAnimalName().equals(a.getName())) {
                        report.setAnimalMonthlyTravel((0.0f * distance[0]) + distance[0]);
                        a.setDistanceMonth((0.0f * distance[0]) + distance[0]);
                        repo.updateMonthlyReport(report);
                        repo.updateReport(report);
                    }
                }
            }
            repo.updateAnimal(a);
        }
        areAnimalsInRange();
        builder.setTitle("Simulation Complete");
        builder.setMessage("Animal movement simulation completed successfully.");
        builder.setPositiveButton("OK", (dialog, which) -> {
            finish();
            startActivity(getIntent());
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
