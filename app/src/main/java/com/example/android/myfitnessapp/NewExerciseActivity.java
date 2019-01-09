package com.example.android.myfitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.myfitnessapp.Database.AppExecutors;
import com.example.android.myfitnessapp.Database.exerciseDatabase;
import com.example.android.myfitnessapp.Database.exerciseEntity;
import com.example.android.myfitnessapp.Widgets.WidgetUpdateService;
import com.example.android.myfitnessapp.util.Utilities;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NewExerciseActivity  extends BaseActivity implements View.OnClickListener{
    EditText mExerciseName;
    EditText mRepsOrDuration;
    RadioButton mReps;
    EditText mWeight;
    EditText mCalories;
    Spinner mMuscleSpinner;
    Button mAddButton;
    private final String TAG = "NewExerciseActivity";
    private Date mDate;
    String userName ;
    String userId;
    exerciseEntity exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        mExerciseName = findViewById(R.id.nameEditText);
        mRepsOrDuration = findViewById(R.id.repsOrDuration);
        mReps = findViewById(R.id.reps);
        mWeight = findViewById(R.id.weightEditText);
        mCalories = findViewById(R.id.caloriesEditText);
        mMuscleSpinner = findViewById(R.id.muscleSpinner);
        mAddButton = findViewById(R.id.addButton);
        SharedPreferences sharedpreferences = getSharedPreferences(LogInActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        userName = sharedpreferences.getString("username",null);
        userId = sharedpreferences.getString("UserId",null);


        setTitle("New Exercise");
       setBottomNavChecked(0);

        Intent intent = getIntent();
        mDate = new Date();
        mDate.setTime(intent.getLongExtra("date", -1));


        mAddButton.setOnClickListener(this);
       initMuscleSpinner();
    }

    private void initMuscleSpinner() {
        ArrayAdapter<CharSequence> muscleAdapter = ArrayAdapter.createFromResource(this, R.array.muscles_array, android.R.layout.simple_spinner_dropdown_item);
        mMuscleSpinner.setAdapter(muscleAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v == mAddButton) {
            saveNewExercise();
        }
    }

    private void saveNewExercise() {
        String name = mExerciseName.getText().toString().trim();
        String repsDuration = mRepsOrDuration.getText().toString().trim();
        String calories = mCalories.getText().toString().trim();

        boolean validName = !Utilities.isInputEmpty(name, mExerciseName);
        boolean validNumber = !Utilities.isInputEmpty(repsDuration, mRepsOrDuration);
        boolean validCalories = !Utilities.isInputEmpty(calories, mCalories);

        if (!validName || !validNumber || !validCalories) return;

        String muscle = mMuscleSpinner.getSelectedItem().toString();
        int repsOrDuration = Integer.parseInt(repsDuration);
        int intCalories = Integer.parseInt(calories);

        boolean isReps = mReps.isChecked();
        int reps = 0;
        int minutes = 0;
        int intWeight = 0;

        String weight = mWeight.getText().toString().trim();
        if (!weight.equals("")) {
            intWeight = Integer.parseInt(weight);
        }
        if (isReps) {
            reps = repsOrDuration;
        } else {
            minutes = repsOrDuration;
        }

       saveExercise(name, reps, minutes, intWeight, muscle, intCalories, mDate);
    }

    private void saveExercise(String name, int reps, int minutes, int intWeight, String muscle, int intCalories, Date mDate) {
        final String insertMessage = "Exercise Added Successfully";

        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String strDate= dateFormatter.format(mDate);


        exercise = new exerciseEntity();
        exercise.setName(name);
        exercise.setReps(reps);
        exercise.setMinutes(minutes);
        exercise.setIntWeight(intWeight);
        exercise.setMuscle(muscle);
        exercise.setIntCalories(intCalories);
        exercise.setDate(strDate);
        exercise.setUserId(userId);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    exerciseDatabase.getAppDatabase(getApplicationContext()).exerciseDao().insertAll(exercise);
                    Log.d(TAG, "run: insert done !");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startWidgetService();
                            Toast.makeText(getApplicationContext(), insertMessage, Toast.LENGTH_SHORT).show();
                            navigateToDayActivity();
                        }
                    });

                }
                catch (Exception  e) {
                    Log.e(TAG, e.getMessage());
                }



            }
        });


    }

    private void startWidgetService() {
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.EXERCISES, exercise);
        i.putExtra(MainActivity.BUNDLE, bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);
    }

    public void navigateToDayActivity() {
        Intent intent = new Intent(NewExerciseActivity.this, DayActivity.class);
        intent.putExtra("date", mDate.getTime());
        startActivity(intent);
        finish();
    }
}
