package com.example.wildlifetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT animalName, animalType, animalDailyTravel FROM reports ORDER BY animalDailyTravel DESC")
    List<ReportEntity> getDailyReports();

    @Query("SELECT animalName, animalType, animalMonthlyTravel FROM reports ORDER BY animalMonthlyTravel DESC")
    List<ReportEntity> getMonthlyReports();
}
