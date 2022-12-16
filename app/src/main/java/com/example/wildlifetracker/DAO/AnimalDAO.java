package com.example.wildlifetracker.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wildlifetracker.Entity.AnimalEntity;

import java.util.List;

public interface AnimalDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAnimal(AnimalEntity animal);

    @Update
    void updateAnimal(AnimalEntity animal);

    @Delete
    void deleteAnimal(AnimalEntity animal);

    @Query("SELECT * FROM animals ORDER BY animalID ASC")
    List<AnimalEntity> getAllAnimals();
}
