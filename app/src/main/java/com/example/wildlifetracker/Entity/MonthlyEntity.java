package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "monthlyReport")
public class MonthlyEntity extends ReportEntity{
    @PrimaryKey
    private int reportID;

    private String animalName;
    private String animalType;
    private Date dateCreated;
    private float animalMonthlyTravel;

    public MonthlyEntity (int reportID, String animalName, String animalType, Date dateCreated, float animalMonthlyTravel) {
        super(reportID, animalName, animalType, dateCreated);
        this.animalMonthlyTravel = animalMonthlyTravel;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReportEntity{" +
                "reportID=" + reportID +
                ", animalName='" + animalName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", animalDailyTravel=" + animalMonthlyTravel +
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

    public float getAnimalMonthlyTravel() {
        return animalMonthlyTravel;
    }

    public void setAnimalMonthlyTravel(float animalMonthlyTravel) {
        this.animalMonthlyTravel = animalMonthlyTravel;
    }
}
