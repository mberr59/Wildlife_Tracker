package com.example.wildlifetracker.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "monthlyReport")
public class MonthlyEntity extends ReportEntity{
    @PrimaryKey
    private int reportID;

    private float animalMonthlyTravel;

    public MonthlyEntity (int reportID, String animalName, String animalType, Date dateCreated, float animalMonthlyTravel) {
        super(reportID, animalName, animalType, dateCreated);
        this.animalMonthlyTravel = animalMonthlyTravel;
    }

    public float getAnimalMonthlyTravel() {
        return animalMonthlyTravel;
    }

    public void setAnimalMonthlyTravel(float animalMonthlyTravel) {
        this.animalMonthlyTravel = animalMonthlyTravel;
    }
}
