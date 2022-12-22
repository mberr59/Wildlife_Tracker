package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reports")
public class ReportEntity {
    @PrimaryKey
    private int reportID;

    private String animalName;
    private String animalType;
    private float animalDailyTravel;
    private float animalMonthlyTravel;

    public ReportEntity (int reportID, String animalName, String animalType, float animalDailyTravel,
                         float animalMonthlyTravel) {
        this.reportID = reportID;
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalDailyTravel = animalDailyTravel;
        this.animalMonthlyTravel = animalMonthlyTravel;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReportEntity{" +
                "reportID=" + reportID +
                ", animalName='" + animalName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", animalDailyTravel=" + animalDailyTravel +
                ", animalMonthlyTravel=" + animalMonthlyTravel +
                '}';
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public float getAnimalDailyTravel() {
        return animalDailyTravel;
    }

    public void setAnimalDailyTravel(float animalDailyTravel) {
        this.animalDailyTravel = animalDailyTravel;
    }

    public float getAnimalMonthlyTravel() {
        return animalMonthlyTravel;
    }

    public void setAnimalMonthlyTravel(float animalMonthlyTravel) {
        this.animalMonthlyTravel = animalMonthlyTravel;
    }
}
