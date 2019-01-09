package com.example.android.myfitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.myfitnessapp.Database.AppExecutors;
import com.example.android.myfitnessapp.Database.exerciseDatabase;
import com.example.android.myfitnessapp.Database.userDatabase;
import com.example.android.myfitnessapp.Database.userEntity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    Button mSignUpButton;
    userEntity user ;
    userEntity userLoggedIn ;
    private final String TAG = "SignUpActivity";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String IS_LOGIN = "IsLoggedIn";
    SharedPreferences settings;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        mSignUpButton= findViewById(R.id.signUpButton);
        user = new userEntity();
        userLoggedIn = new userEntity();
        mSignUpButton.setOnClickListener(this);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v == mSignUpButton) {
            createNewUser();
        }
    }

    private void createNewUser() {
        sharedpreferences.edit().clear().commit();
        settings.edit().clear().commit();
        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String fullName = firstName + " " + lastName;
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();


        boolean validFirstName = isValidName(firstName, mFirstName);
        boolean validLastName = isValidName(lastName, mLastName);
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password, confirmPassword);

        if (validFirstName && validLastName && validEmail && validPassword) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        userDatabase.getAppDatabase(getApplicationContext()).userDao().insertAll(user);
                        Log.d(TAG, "run: insert done !");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userDatabase db = userDatabase.getAppDatabase(getApplicationContext());
                                userLoggedIn = db.userDao().findByEmail(email,password);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                String fullName = userLoggedIn.getFirstName()+" " +userLoggedIn.getLastName();
                                String userId = Integer.toString( userLoggedIn.getUid());

                                editor.putBoolean(IS_LOGIN, true);
                                editor.putString("username", fullName);
                                editor.putString("UserId",userId);
                                editor.commit();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra("fullName",user.getFirstName()+" " +user.getLastName());

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
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

    public boolean isValidName(String name, View v) {
        if (name.equals("")) {
            if (v == mFirstName) mFirstName.setError("Please enter your first name");
            if (v == mLastName) mLastName.setError("Please enter your last name");
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmail.setError("Please enter a valid email address");
        }
        return isGoodEmail;
    }

    public boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPassword.setError("Passwords must be at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }


}

