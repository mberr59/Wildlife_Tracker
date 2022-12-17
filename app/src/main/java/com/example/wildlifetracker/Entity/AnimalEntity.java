package com.example.wildlifetracker.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(tableName = "animals")
public class AnimalEntity {

    @PrimaryKey(autoGenerate = true)
    private int animalID;

    private String name = "";
    private String type;
    private String latitude;
    private String longitude;
    private float distanceMonth;
    private float distanceDay;
    private String notes;
    private int dayOfMonth;
    private int monthOfYear;

    public AnimalEntity(int animalID, String name, String type, String latitude, String longitude,
                        float distanceMonth, float distanceDay, String notes, int dayOfMonth, int monthOfYear) {
        this.animalID = animalID;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceMonth = distanceMonth;
        this.distanceDay = distanceDay;
        this.notes = notes;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnimalEntity{" +
                "animalID=" + animalID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceMonth=" + distanceMonth +
                ", distanceDay=" + distanceDay +
                ", notes='" + notes + '\'' +
                ", dayOfMonth=" + dayOfMonth +
                ", monthOfYear=" + monthOfYear +
                '}';
    }


    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public float getDistanceMonth() {
        return distanceMonth;
    }

    public void setDistanceMonth(float distanceMonth) {
        this.distanceMonth = distanceMonth;
    }

    public float getDistanceDay() {
        return distanceDay;
    }

    public void setDistanceDay(float distanceDay) {
        this.distanceDay = distanceDay;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }
}
