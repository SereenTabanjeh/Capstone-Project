package com.example.android.myfitnessapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.example.android.myfitnessapp.Adapter.exerciseAdapter;
import com.example.android.myfitnessapp.Database.AppExecutors;
import com.example.android.myfitnessapp.Database.ExerciseEntity;
import com.example.android.myfitnessapp.Database.MainViewModel;
import com.example.android.myfitnessapp.Database.workoutDatabase;
import com.example.android.myfitnessapp.Database.workoutEntity;
import com.example.android.myfitnessapp.Widgets.WidgetUpdateService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.android.myfitnessapp.MainActivity.alreadyExecuted;

public class DayActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    TextView mEmptyView;
    TextView mCalories;
    Button mSaveButton;
    FloatingActionButton mFab;
    exerciseAdapter mAdapter;
    private Date mDate;
    String userId;
    List<Integer> exerciseIds;
    private static final String TAG = "DayActivity";
    Integer totalCal=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        SharedPreferences sharedpreferences = getSharedPreferences(LogInActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String userName = sharedpreferences.getString("username", null);
        userId = sharedpreferences.getString("UserId", null);


        mAdapter = new exerciseAdapter(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mEmptyView = findViewById(R.id.emptyView);
        mCalories = findViewById(R.id.Totalcalories);
        mSaveButton = findViewById(R.id.saveButton);
        mFab = findViewById(R.id.fab);

        Intent intent = getIntent();
        mDate = new Date();
        mDate.setTime(intent.getLongExtra("date", -1));
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DateFormat refIdFormatter = new SimpleDateFormat("MMddyyyy", Locale.US);
        String strDate = dateFormatter.format(mDate);

        setTitle(strDate);
        setBottomNavChecked(0);
        setUpViewModelForExercises(strDate);
        GetTotalCalories(strDate);
        if(!alreadyExecuted) {
            startWidgetService();
            alreadyExecuted = true;
        }

        mSaveButton.setOnClickListener(this);
        mFab.setOnClickListener(this);
    }


    public void noExercisesView() {
        mRecyclerView.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }


    public void hasExercisesView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }


    private void startWidgetService() {
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        ExerciseEntity exercise = new ExerciseEntity();
        exercise.setName("You don\\'t have any new exercise, go and log new exercise !");
        bundle.putParcelable(MainActivity.EXERCISES, exercise);
        i.putExtra(MainActivity.BUNDLE, bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);
    }

    @Override
    public void onClick(View v) {
        if (v == mFab) {
            Intent intent = new Intent(DayActivity.this, NewExerciseActivity.class);
            intent.putExtra("date", mDate.getTime());
            startActivity(intent);
        }
        if (v == mSaveButton) {
            launchAlertDialog();
        }
    }

    private void launchAlertDialog() {
        SaveWorkoutDialogFragment dialogFragment = new SaveWorkoutDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", mDate.toString());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "saveWorkout");

    }

    private void setUpViewModelForExercises(String date) {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.getExerciseList(date,userId).observe(this, new Observer<List<ExerciseEntity>>() {
                @Override
                public void onChanged(@Nullable List<ExerciseEntity> exerciseEntities) {
                    if (exerciseEntities.size() != 0) {
                        mAdapter.setExerciseList(exerciseEntities);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        hasExercisesView();
                    } else {
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        noExercisesView();
                        return;
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }


    private void GetTotalCalories(String date) {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.getCalories(date,userId).observe(this, new Observer<List<Integer>>() {

                @Override
                public void onChanged(@Nullable List<Integer> calories) {
                    if (calories != null) {
                        for (int cal:calories) {
                            totalCal += cal;
                        }
                        mCalories.append(totalCal.toString());


                    } else {
                        totalCal = 0;
                        mCalories.append(totalCal.toString());
                        return;
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }


   public  void SaveWorkouts(final String name, String date) throws ParseException {
       SimpleDateFormat date1 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
               Locale.ENGLISH);
       Date parsedDate = date1.parse(date);
       DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
       String strDate = dateFormatter.format(parsedDate);
       exerciseIds = new ArrayList<>();

       MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
       try {
           viewModel.getExerciseIds(strDate,userId).observe(this, new Observer<List<Integer>>() {

               @Override
               public void onChanged(@Nullable List<Integer> strings) {
                   if (strings != null) {
                       for (int id : strings) {

                           exerciseIds.add (id);
                       }

                     String exeIds = exerciseIds.toString();
                       InsertWorkout(name,userId,exeIds);
                   }
               }
           });


       } catch (Exception e) {
           e.printStackTrace();
           return;
       }

   }


    private void InsertWorkout(String name,String userId, String exerciseIds) {
        final String insertMessage = "Workout Added Successfully";

        final workoutEntity workout = new workoutEntity();
        workout.setName(name);
        workout.setExeIds(exerciseIds);
        workout.setUserId(userId);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {


            @Override
            public void run() {
                try {

                    workoutDatabase.getAppDatabase(getApplicationContext()).workoutDao().insertAll(workout);
                    Log.d(TAG, "run: insert done !");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), insertMessage, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                catch (Exception  e) {
                    Log.e(TAG, e.getMessage());
                }



            }
        });


    }

}