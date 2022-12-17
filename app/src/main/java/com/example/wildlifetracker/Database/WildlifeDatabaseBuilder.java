package com.example.wildlifetracker.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

import com.example.wildlifetracker.DAO.AnimalDAO;
import com.example.wildlifetracker.DAO.UserDAO;
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.UserEntity;

@Database(entities = {UserEntity.class, AnimalEntity.class}, version = 2, exportSchema = false)
public abstract class WildlifeDatabaseBuilder extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract AnimalDAO animalDAO();

    private static volatile WildlifeDatabaseBuilder INSTANCE;

    static WildlifeDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WildlifeDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WildlifeDatabaseBuilder.class, "Wildlife Database.db")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
