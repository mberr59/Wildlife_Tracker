package com.example.wildlifetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wildlifetracker.Entity.DailyEntity;

import java.util.List;

@Dao
public interface DailyDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDailyReport(DailyEntity report);

    @Update
    void updateDailyReport(DailyEntity report);

    @Delete
    void deleteDailyReport(DailyEntity report);

    @Query("SELECT * FROM dailyReport ORDER BY animalDailyTravel DESC")
    List<DailyEntity> getAllDailyReports();
}
