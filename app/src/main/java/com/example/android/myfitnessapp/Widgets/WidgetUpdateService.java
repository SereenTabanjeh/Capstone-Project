package com.example.android.myfitnessapp.Widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.android.myfitnessapp.Database.exerciseEntity;
import com.example.android.myfitnessapp.MainActivity;


public class WidgetUpdateService extends IntentService
{
    public static final String WIDGET_UPDATE_ACTION = "com.example.android.myfitnessapp.Widgets.update_widget";
    private exerciseEntity mExercise;

    public WidgetUpdateService()
    {
        super("WidgetServiceUpdate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent != null && intent.getAction().equals(WIDGET_UPDATE_ACTION))
        {
            Bundle bundle = intent.getBundleExtra(MainActivity.BUNDLE);
            Parcelable parcelables = bundle.getParcelable(MainActivity.EXERCISES);
            if (parcelables != null)
            {
                mExercise = (exerciseEntity)  parcelables;

            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FitnessAppProvider.class));
            FitnessAppProvider.updateAppWidget(this, appWidgetManager, appWidgetIds,mExercise);
        }
    }
}
