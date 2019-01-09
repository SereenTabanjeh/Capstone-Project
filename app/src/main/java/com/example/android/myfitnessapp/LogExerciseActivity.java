package com.example.android.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogExerciseActivity extends BaseActivity implements View.OnClickListener{
    Spinner mWorkoutSpinner;
    Button mSelectButton;
    Button mNewExerciseButton;

    private Date mDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_exercise);

        mWorkoutSpinner = findViewById(R.id.workoutSpinner);
        mSelectButton = findViewById(R.id.selectButton);
        mNewExerciseButton = findViewById(R.id.newExerciseButton);

        Intent intent = getIntent();
        mDate = new Date();
        mDate.setTime(intent.getLongExtra("date", -1));

        setTitle("Log Exercise");
        setBottomNavChecked(0);

        mSelectButton.setOnClickListener(this);
        mNewExerciseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mSelectButton) {
            if (!mWorkoutSpinner.getSelectedItem().toString().equals("No saved workouts")) {
                int position = mWorkoutSpinner.getSelectedItemPosition();
                DateFormat refIdFormatter = new SimpleDateFormat("MMddyyyy", Locale.US);
               // mPresenter.addSavedWorkout(position, refIdFormatter.format(mDate));
            }
        }
        if (v == mNewExerciseButton) {
            navigateToNewExerciseActivity();
        }
    }

    private void navigateToNewExerciseActivity() {
        Intent intent = new Intent(LogExerciseActivity.this, NewExerciseActivity.class);
        intent.putExtra("date", mDate.getTime());
        startActivity(intent);
    }


    public void navigateToDayActivity() {
        Intent intent = new Intent(LogExerciseActivity.this, DayActivity.class);
        intent.putExtra("date", mDate.getTime());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}


