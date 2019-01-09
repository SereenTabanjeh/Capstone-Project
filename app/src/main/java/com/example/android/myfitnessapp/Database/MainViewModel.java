package com.example.android.myfitnessapp.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = MainViewModel.class.getSimpleName();
    public  LiveData<List<exerciseEntity>> exerciseList;
    private List<exerciseEntity> exercise;
    public LiveData<List<Integer>> Calories;
    public LiveData<List<Integer>> exerciseIds;
    public  LiveData<List<workoutEntity>> workoutList;

    public MainViewModel(@NonNull Application application) {
        super(application);

        exerciseDatabase exerciseDB = exerciseDatabase.getAppDatabase(this.getApplication());
        Log.e(TAG, "Actively retrieving the Exercises from the database");
      //  exerciseList =  exerciseDB.exerciseDao().getAll();
    }



    public LiveData<List<Integer>> getCalories(String date, String userId) {
        exerciseDatabase exerciseDB = exerciseDatabase.getAppDatabase(this.getApplication());
        Calories =  exerciseDB.exerciseDao().GetCalories(date,userId);
        return Calories;
    }

    public LiveData<List<exerciseEntity>> getExerciseList(String date, String userId) {
        exerciseDatabase exerciseDB = exerciseDatabase.getAppDatabase(this.getApplication());
        exerciseList =  exerciseDB.exerciseDao().findByDate(date,userId);
        return exerciseList;
    }

    public LiveData<List<Integer>> getExerciseIds(String date, String userId) {
        exerciseDatabase exerciseDB = exerciseDatabase.getAppDatabase(this.getApplication());
        exerciseIds =  exerciseDB.exerciseDao().GetExerciseIds(date,userId);
        return exerciseIds;
    }

    public LiveData<List<exerciseEntity>> getExerciseList() {
        exerciseDatabase exerciseDB = exerciseDatabase.getAppDatabase(this.getApplication());
        exerciseList =  exerciseDB.exerciseDao().getAll();
        return exerciseList;
    }

    public LiveData<List<workoutEntity>> getWorkoutsList(String userId) {
        workoutDatabase workoutDB = workoutDatabase.getAppDatabase(this.getApplication());
        workoutList =  workoutDB.workoutDao().FindByUserId(userId);
        return workoutList;
    }

    public LiveData<List<exerciseEntity>> getWorkoutExercises(List<Integer> ids) {
        exerciseDatabase exeDB = exerciseDatabase.getAppDatabase(this.getApplication());
        exerciseList =  exeDB.exerciseDao().findByIds(ids);
        return exerciseList;
    }

    public List<exerciseEntity> getExercise() {
        return exercise;
    }

    public void setExercise(List<exerciseEntity> exercise) {
        this.exercise = exercise;
    }
}


