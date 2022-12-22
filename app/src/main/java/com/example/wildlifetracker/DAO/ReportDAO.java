package com.example.wildlifetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.Entity.ReportEntity;

import java.util.List;

@Dao
public interface ReportDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReport(ReportEntity report);

    @Update
    void updateReport(ReportEntity report);

    @Delete
    void deleteReport(ReportEntity report);

    @Query("SELECT * FROM reports ORDER BY reportID ASC")
    List<ReportEntity> getAllReports();

    @Query("SELECT reportID, animalName, animalType, animalDailyTravel FROM reports ORDER BY animalDailyTravel DESC")
    List<DailyEntity> getDailyReports();

    @Query("SELECT reportID, animalName, animalType, animalMonthlyTravel FROM reports ORDER BY animalMonthlyTravel DESC")
    List<MonthlyEntity> getMonthlyReports();
}
