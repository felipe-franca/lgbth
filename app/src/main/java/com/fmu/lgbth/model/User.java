package com.fmu.lgbth.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.nio.Buffer;
import java.sql.Blob;
import java.sql.Date;

@Entity
public class User {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    @PrimaryKey
    private String email;
    @NonNull
    private String password;
    private Integer age;
    private String birthDate;
    private String avatar;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User () {

    };

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getEmail() {
        return this.email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
