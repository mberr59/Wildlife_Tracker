package com.example.wildlifetracker.Database;

import android.app.Application;

import com.example.wildlifetracker.DAO.AnimalDAO;
import com.example.wildlifetracker.DAO.ReportDAO;
import com.example.wildlifetracker.DAO.UserDAO;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.Entity.UserEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private UserDAO mUserDAO;
    private AnimalDAO mAnimalDAO;
    private ReportDAO mReportDAO;
    private List<UserEntity> mAllUsers;
    private List<AnimalEntity> mAllAnimals;
    private List<ReportEntity> mAllReports;
    private List<DailyEntity> mDailyReport;
    private List<MonthlyEntity> mMonthlyReport;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application app) {
        WildlifeDatabaseBuilder wildlifeDB = WildlifeDatabaseBuilder.getDatabase(app);
        mUserDAO = wildlifeDB.userDAO();
        mAnimalDAO = wildlifeDB.animalDAO();
        mReportDAO = wildlifeDB.reportDAO();
    }

    public void insertUser (UserEntity user) {
        databaseExecutor.execute(() -> mUserDAO.insertUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertAnimal (AnimalEntity animal) {
        databaseExecutor.execute(() -> mAnimalDAO.insertAnimal(animal));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertReport (ReportEntity report) {
        databaseExecutor.execute(() -> mReportDAO.insertReport(report));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateUser (UserEntity user) {
        databaseExecutor.execute(() -> mUserDAO.updateUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAnimal (AnimalEntity animal) {
        databaseExecutor.execute(() -> mAnimalDAO.updateAnimal(animal));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateReport (ReportEntity report) {
        databaseExecutor.execute(() -> mReportDAO.updateReport(report));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser (UserEntity user) {
        databaseExecutor.execute(() -> mUserDAO.deleteUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAnimal (AnimalEntity animal) {
        databaseExecutor.execute(() -> mAnimalDAO.deleteAnimal(animal));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteReport (ReportEntity report) {
        databaseExecutor.execute(() -> mReportDAO.deleteReport(report));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<UserEntity>getAllUsers() {
        databaseExecutor.execute(() -> mAllUsers = mUserDAO.getAllUsers());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public List<AnimalEntity>getAllAnimals() {
        databaseExecutor.execute(() -> mAllAnimals = mAnimalDAO.getAllAnimals());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAnimals;
    }

    public List<ReportEntity>getAllReports() {
        databaseExecutor.execute(() -> mAllReports = mReportDAO.getAllReports());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllReports;
    }

    public List<DailyEntity>getDailyReports() {
        databaseExecutor.execute(() -> mDailyReport = mReportDAO.getDailyReports());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mDailyReport;
    }

    public List<MonthlyEntity>getMonthlyReports() {
        databaseExecutor.execute(() -> mMonthlyReport = mReportDAO.getMonthlyReports());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mMonthlyReport;
    }
}
