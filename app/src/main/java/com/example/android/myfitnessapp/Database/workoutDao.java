package com.example.android.myfitnessapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface workoutDao {

    @Query("SELECT * FROM workout")
    LiveData<List<workoutEntity>> getAll();

    @Query("SELECT * FROM workout where userId = :userId")
    LiveData<List<workoutEntity>> FindByUserId(String userId);

    @Query("SELECT COUNT(*) from workout")
    int countWorkout();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(workoutEntity workout);

    @Delete
    void delete(workoutEntity workout);
}
