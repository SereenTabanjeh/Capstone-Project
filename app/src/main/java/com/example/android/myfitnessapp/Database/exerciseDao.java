package com.example.android.myfitnessapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface exerciseDao {

    @Query("SELECT * FROM exercise")
    LiveData<List<exerciseEntity>> getAll();

    @Query("SELECT * FROM exercise where date LIKE  :date and userId= :userId")
    LiveData<List<exerciseEntity>>  findByDate(String date,String userId);

    @Query("SELECT * FROM exercise where id in (:exeList)")
    LiveData<List<exerciseEntity>>  findByIds(List<Integer> exeList);

    @Query("SELECT intCalories FROM exercise where date LIKE  :date and userId= :userId")
    LiveData<List<Integer>>  GetCalories(String date, String userId);

    @Query("SELECT id FROM exercise where date LIKE  :date and userId= :userId")
    LiveData<List<Integer>> GetExerciseIds(String date, String userId);

    @Query("SELECT COUNT(*) from exercise")
    int countExercise();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(exerciseEntity exercise);

    @Delete
    void delete(exerciseEntity exercise);
}
