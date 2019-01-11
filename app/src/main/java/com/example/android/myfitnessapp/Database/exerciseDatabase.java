package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {ExerciseEntity.class}, version = 5, exportSchema = false)
public abstract class exerciseDatabase extends RoomDatabase {
    private static exerciseDatabase sInstance;
    private static final String TAG = exerciseDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "exerciseDB";

    public abstract exerciseDao exerciseDao();

    public static exerciseDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance =
                        Room.databaseBuilder(context.getApplicationContext(),
                                exerciseDatabase.class, exerciseDatabase.DB_NAME)
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
