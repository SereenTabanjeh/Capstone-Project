package com.example.android.myfitnessapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;


import static com.example.android.myfitnessapp.LogInActivity.IS_LOGIN;


public class BaseActivity extends AppCompatActivity  {
    private ProgressDialog mProgressDialog;
    public BottomNavigationView mBottomNavigationView;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);

    }

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout fullView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer =  fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        mBottomNavigationView =  findViewById(R.id.bottom_navigation);
        mTopToolbar= findViewById(R.id.app_bar);
        initBottomNav();
        setSupportActionBar(mTopToolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void initBottomNav() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        navigateToMain();
                        break;
                    case R.id.action_workouts:
                        navigateToWorkouts();
                        break;

                    case R.id.action_maps:
                        navigateToMaps();
                        break;

                }
                return false;
            }
        });
    }


    public void setBottomNavChecked(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }


    public void navigateToMaps() {
        Intent intent = new Intent(BaseActivity.this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    public void navigateToMain() {
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }

    private void navigateToWorkouts() {
        Intent workoutIntent = new Intent(BaseActivity.this, WorkoutsActivity.class);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        startActivity(workoutIntent);
    }



    private void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(LogInActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

        Intent intent = new Intent(BaseActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
