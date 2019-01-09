package com.example.android.myfitnessapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.myfitnessapp.Database.MainViewModel;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements  View.OnClickListener {
    TextView mDateTextView;
    TextView mCalories;
    Button mSeeDetailsButton;
    private CaldroidFragment mCaldroidFragment;
    private Date mDate;
    private DateFormat mRefIdFormatter;
    String userId;
    Integer totalCal = 0;
    String strDate;
    public static final String BUNDLE = "bundle";
    public static final String EXERCISES = "exercises";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedpreferences = getSharedPreferences(LogInActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String userName = sharedpreferences.getString("username",null);
        userId = sharedpreferences.getString("UserId",null);

        setTitle(userName);

        mDateTextView = findViewById(R.id.date);
        mCalories = findViewById(R.id.calories );
        mSeeDetailsButton = findViewById(R.id.seeDetailsButton);

        mDate = new Date();
        mRefIdFormatter = new SimpleDateFormat("MMddyyyy", Locale.US);

        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        strDate = dateFormatter.format(mDate);

        setDateTextView(mDate);
        getCalories(mDate);
        initCaldroidFragment();
       setBottomNavChecked(0);

        mSeeDetailsButton.setOnClickListener(this);
    }

    private void setDateTextView(Date date) {
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        mDateTextView.setText(dateFormatter.format(date));
    }

    private void getCalories(Date date) {
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateRefId = dateFormatter.format(date);
        GetTotalCalories(dateRefId);
    }

    private void initCaldroidFragment() {
        mCaldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        mCaldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, mCaldroidFragment);
        t.commit();

        setCaldroidListener();
    }

    private void setCaldroidListener() {
        mCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                mDate = date;

                mCaldroidFragment.clearSelectedDates();
                mCaldroidFragment.setSelectedDates(date, date);
                mCaldroidFragment.refreshView();

                setDateTextView(date);
                getCalories(date);
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCaldroidViewCreated() {
                Button leftButton = mCaldroidFragment.getLeftArrowButton();
                Button rightButton = mCaldroidFragment.getRightArrowButton();
                leftButton.setBackground(getResources().getDrawable(R.drawable.left));
                rightButton.setBackground(getResources().getDrawable(R.drawable.right));

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mSeeDetailsButton) {
            Intent intent = new Intent(MainActivity.this, DayActivity.class);
            intent.putExtra("date", mDate.getTime());
            startActivity(intent);
        }

    }

    private void GetTotalCalories(String mDate) {
        mCalories.clearComposingText();
        totalCal=0;
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        try {
            viewModel.getCalories(mDate,userId).observe(this, new Observer<List<Integer>>() {

                @Override
                public void onChanged(@Nullable List<Integer> calories) {
                    if (calories != null) {
                        for (int cal:calories) {
                            totalCal += cal;
                        }
                        mCalories.setText(totalCal.toString());


                    } else {
                        mCalories.setText("0");
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
