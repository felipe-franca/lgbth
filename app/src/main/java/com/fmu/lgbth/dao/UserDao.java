package com.fmu.lgbth.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fmu.lgbth.model.User;

@Dao
public interface UserDao {
    @Insert
    void persist(User user);

    @Query("SELECT * FROM User LIMIT 1")
    User get();

    @Query("DELETE FROM User WHERE 1=1")
    void delete();
}
