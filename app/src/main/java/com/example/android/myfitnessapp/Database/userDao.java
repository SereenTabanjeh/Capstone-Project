package com.example.android.myfitnessapp.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface userDao {
    @Query("SELECT * FROM user")
    List<UserEntity> getAll();

    @Query("SELECT * FROM user where email LIKE  :email AND password LIKE :password")
    UserEntity findByEmail(String email, String password);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserEntity user);

    @Delete
    void delete(UserEntity user);
}
