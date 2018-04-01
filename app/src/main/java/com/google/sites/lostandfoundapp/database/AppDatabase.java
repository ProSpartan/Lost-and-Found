package com.google.sites.lostandfoundapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.google.sites.lostandfoundapp.database.dao.ImageDao;
import com.google.sites.lostandfoundapp.database.dao.UserDao;
import com.google.sites.lostandfoundapp.database.entities.ImageEntity;
import com.google.sites.lostandfoundapp.database.entities.UserEntity;

@Database(entities = {UserEntity.class, ImageEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static String databaseName = "LostNFoundDatabase";

    public abstract UserDao userDao();
    public abstract ImageDao imageDao();

    public static AppDatabase getINSTANCE(Context context) {
        if (null == INSTANCE) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, databaseName).build();
        }
        return INSTANCE;
    }
}
