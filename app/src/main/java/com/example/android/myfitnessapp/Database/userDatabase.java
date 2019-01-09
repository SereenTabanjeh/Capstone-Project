package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {userEntity.class}, version = 4, exportSchema = false)
public abstract class userDatabase extends RoomDatabase {
    private static userDatabase sInstance;
    private static final String TAG = userDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "userDB";

    public abstract userDao userDao();

    public static userDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance =
                        Room.databaseBuilder(context.getApplicationContext(),
                                userDatabase.class, userDatabase.DB_NAME)
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









