package com.example.android.myfitnessapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.myfitnessapp.Adapter.exerciseAdapter;
import com.example.android.myfitnessapp.Adapter.workoutAdapter;
import com.example.android.myfitnessapp.Database.ExerciseEntity;
import com.example.android.myfitnessapp.Database.MainViewModel;
import com.example.android.myfitnessapp.Database.workoutEntity;
import com.example.android.myfitnessapp.listener.ItemClickListenerObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutsActivity  extends BaseActivity implements ItemClickListenerObject {
    RecyclerView mRecyclerView;
    TextView mEmptyView;
    workoutAdapter mAdapter;
    exerciseAdapter exeAdapter;
    String userId;
    private Date mDate;
    String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        SharedPreferences sharedpreferences = getSharedPreferences(LogInActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String userName = sharedpreferences.getString("username",null);
        userId = sharedpreferences.getString("UserId",null);


        mAdapter = new workoutAdapter(this,this);
        exeAdapter = new exerciseAdapter(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mEmptyView = findViewById(R.id.emptyView);
        setTitle(getString(R.string.my_workouts));
        setBottomNavChecked(1);
        setUpViewModelForWorkouts(userId);
    }

    private void setUpViewModelForWorkouts(String userId) {

      MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.getWorkoutsList(userId).observe(this, new Observer<List<workoutEntity>>() {
                @Override
                public void onChanged(@Nullable List<workoutEntity> workoutEntity) {
                    if (workoutEntity.size()!=0) {
                        mAdapter.setWorkoutList(workoutEntity);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                }
                else {
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        noWorkoutView();
                        return;
                    }

                }
            });


        }

        catch(Exception e){
            e.printStackTrace();
            return;
        }

    }

    private void noWorkoutView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickItemObject(int position) {
        workoutEntity workout = mAdapter.getWorkoutList().get(position);
        String exeIds = workout.getExeIds();
        String replace = exeIds.replaceAll("^\\[|]$", "");
        List<Integer> lst = new ArrayList<Integer>();
        for (String field : replace.split(",")) {
            field=field.trim();
            Integer i = Integer.parseInt(field);
            lst.add(i);
        }
        setUpViewModelForExercises(lst);
        }


    private void setUpViewModelForExercises(List<Integer> list) {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.getWorkoutExercises(list).observe(this, new Observer<List<ExerciseEntity>>() {
                @Override
                public void onChanged(@Nullable List<ExerciseEntity> exerciseEntities) {
                    if (exerciseEntities.size() != 0) {
                        exeAdapter.setExerciseList(exerciseEntities);
                        mRecyclerView.setAdapter(exeAdapter);
                        exeAdapter.notifyDataSetChanged();

                    } else {
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        return;
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}