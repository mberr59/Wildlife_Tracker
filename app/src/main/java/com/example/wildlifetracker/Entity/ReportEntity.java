package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "reports")
public class ReportEntity {
    @PrimaryKey
    private int reportID;

    private String animalName;
    private String animalType;
    private Date dateCreated;


    public ReportEntity (int reportID, String animalName, String animalType, Date dateCreated) {
        this.reportID = reportID;
        this.animalName = animalName;
        this.animalType = animalType;
        this.dateCreated = dateCreated;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReportEntity{" +
                "reportID=" + reportID +
                ", animalName='" + animalName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
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

    public Date getDateCreated() { return dateCreated;}


}
