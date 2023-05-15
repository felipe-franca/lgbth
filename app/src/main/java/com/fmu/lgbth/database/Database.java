package com.fmu.lgbth.database;

import androidx.room.RoomDatabase;

import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.model.User;

@androidx.room.Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UserDao getUserDao();
}
