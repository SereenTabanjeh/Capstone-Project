package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {workoutEntity.class}, version = 5, exportSchema = false)
public abstract class workoutDatabase extends RoomDatabase {
    private static workoutDatabase sInstance;
    private static final String TAG = workoutDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "workoutDB";

    public abstract workoutDao workoutDao();

    public static workoutDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance =
                        Room.databaseBuilder(context.getApplicationContext(),
                                workoutDatabase.class, workoutDatabase.DB_NAME)
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
            }
        }
        return sInstance;

    }

    public static void destroyInstance() {
        sInstance = null;
    }

}
