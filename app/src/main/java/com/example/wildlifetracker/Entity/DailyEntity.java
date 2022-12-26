package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dailyReport")
public class DailyEntity extends ReportEntity {
    @PrimaryKey
    private int reportID;

    private float animalDailyTravel;

    public DailyEntity (int reportID, String animalName, String animalType, Date dateCreated, float animalDailyTravel) {
        super(reportID, animalName, animalType, dateCreated);
        this.animalDailyTravel = animalDailyTravel;
    }


    public float getAnimalDailyTravel() {
        return animalDailyTravel;
    }

    public void setAnimalDailyTravel(float animalDailyTravel) {
        this.animalDailyTravel = animalDailyTravel;
    }
}
