package com.example.android.myfitnessapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.myfitnessapp.Database.ExerciseEntity;
import com.example.android.myfitnessapp.R;


public class FitnessAppProvider extends AppWidgetProvider
{
    public static ExerciseEntity mExercises;

    public FitnessAppProvider()
    {

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetIds[], ExerciseEntity exercises)
    {
        mExercises = exercises;
        for (int appWidgetId : appWidgetIds)
        {
            Intent intent = new Intent(context, listViewsService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fitness_app_widget);
            views.setRemoteAdapter(R.id.appwidget_text, intent);
            ComponentName component = new ComponentName(context, FitnessAppProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_text);
            appWidgetManager.updateAppWidget(component, views);
        }

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onEnabled(Context context)
    {

    }

    @Override
    public void onDisabled(Context context)
    {

    }


}

