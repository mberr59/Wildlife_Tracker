package com.example.wildlifetracker.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "animals")
public class AnimalEntity {

    @PrimaryKey(autoGenerate = true)
    private int animalID;

    private String name = "";
    private String type;


}
