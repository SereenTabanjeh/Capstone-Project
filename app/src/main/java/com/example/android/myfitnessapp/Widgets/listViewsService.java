package com.example.android.myfitnessapp.Widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.myfitnessapp.Database.ExerciseEntity;
import com.example.android.myfitnessapp.R;


public class listViewsService extends RemoteViewsService
{

    public ListViewsFactory onGetViewFactory(Intent intent)
    {
        return new ListViewsFactory(this.getApplicationContext());
    }
}

class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context mContext;
    private ExerciseEntity mEcercise;

    public ListViewsFactory(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public void onCreate()
    {

    }

    @Override
    public void onDataSetChanged()
    {
        mEcercise = FitnessAppProvider.mExercises;
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public int getCount()
    {
        if (mEcercise == null)
            return 0;
        return 1;
    }


    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);

        if (mEcercise.getDate() != null) {
            views.setTextViewText(R.id.name,"Last Saved Exercise :"+"\n"+"Exercise Name : "+ mEcercise.getName()+"\n" +"Calories Burned : "+ mEcercise.getIntCalories()+"\n" +"Date : "+  mEcercise.getDate()+"\n"+ "Muscle : "+ mEcercise.getMuscle()+"\n");
        }
        else{

            views.setTextViewText(R.id.name,mEcercise.getName());
        }


        return views;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }
}
