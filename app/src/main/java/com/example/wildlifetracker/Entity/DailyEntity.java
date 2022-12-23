package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dailyReport")
public class DailyEntity extends ReportEntity {
    @PrimaryKey
    private int reportID;

    private String animalName;
    private String animalType;
    private Date dateCreated;
    private float animalDailyTravel;

    public DailyEntity (int reportID, String animalName, String animalType, Date dateCreated, float animalDailyTravel) {
        super(reportID, animalName, animalType, dateCreated);
        this.animalDailyTravel = animalDailyTravel;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReportEntity{" +
                "reportID=" + reportID +
                ", animalName='" + animalName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", animalDailyTravel=" + animalDailyTravel +
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

    public Date getDateCreated() { return dateCreated; }

    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }

    public float getAnimalDailyTravel() {
        return animalDailyTravel;
    }

    public void setAnimalDailyTravel(float animalDailyTravel) {
        this.animalDailyTravel = animalDailyTravel;
    }
}
