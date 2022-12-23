package com.example.wildlifetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wildlifetracker.Entity.MonthlyEntity;

import java.util.List;

@Dao
public interface MonthlyDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMonthlyReport(MonthlyEntity report);

    @Update
    void updateMonthlyReport(MonthlyEntity report);

    @Delete
    void deleteMonthlyReport(MonthlyEntity report);

    @Query("SELECT * FROM monthlyReport ORDER BY animalMonthlyTravel DESC")
    List<MonthlyEntity> getAllMonthlyReports();
}
