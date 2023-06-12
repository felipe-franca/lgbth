package com.fmu.lgbth.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fmu.lgbth.model.User;

@Dao
public interface UserDao {
    @Insert
    void persist(User user);

    @Query("UPDATE User " +
            "SET name=:name,  " +
            "email=:email, " +
            "age=:age, " +
            "avatar=:avatar, " +
            "birthDate=:birthDate " +
            "WHERE id = :id")
    void update(long id, String name, String email, Integer age, String avatar, String birthDate);

    @Query("SELECT * FROM User LIMIT 1")
    User get();

    @Query("DELETE FROM User WHERE 1=1")
    void delete();
}
