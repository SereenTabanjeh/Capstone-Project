package com.example.android.myfitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.myfitnessapp.Database.UserEntity;
import com.example.android.myfitnessapp.Database.userDatabase;

public class LogInActivity  extends AppCompatActivity implements   View.OnClickListener{


    TextView mSignUp;
    EditText mEmail;
    EditText mPassword;
    Button mLogInButton;
    UserEntity user;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String IS_LOGIN = "IsLoggedIn";
    SharedPreferences settings;
    SharedPreferences sharedpreferences;
    String failedLoginMessage = "Invalid Email or password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSignUp = findViewById(R.id.signUpText);
        mEmail =findViewById(R.id.email);
        mPassword =findViewById(R.id.password);
        mLogInButton = findViewById(R.id.logInButton);
        user = new UserEntity();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        checkLogin();

        mLogInButton.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSignUp) {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        if (v == mLogInButton) {
            logInWithPassword();
        }
    }

    private void logInWithPassword() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        boolean invalidEmail = email.equals("");
        boolean invalidPassword = password.equals("");

        if (invalidEmail) {
            mEmail.setError("Please enter your email");
        }
        if (invalidPassword) {
            mPassword.setError("Please enter your password");
        }
        if (invalidEmail || invalidPassword) return;

        userDatabase db = userDatabase.getAppDatabase(this.getApplication());
         user = db.userDao().findByEmail(email,password);


        if (user != null){
            //settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String fullName = user.getFirstName()+" " +user.getLastName();
            String userId = Integer.toString( user.getUid());

            editor.putBoolean(IS_LOGIN, true);
            editor.putString("username", fullName);
            editor.putString("UserId",userId);
            editor.commit();
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.putExtra("fullName",user.getFirstName()+" " +user.getLastName());

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }

        else{

            Toast.makeText(getApplicationContext(), failedLoginMessage, Toast.LENGTH_SHORT).show();
        }

    }

    public void checkLogin(){
        if(isLoggedIn()){
            Intent i = new Intent(LogInActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }

    }

    public boolean isLoggedIn(){
        boolean loggedIn = false;
        if (sharedpreferences.getBoolean(IS_LOGIN,false)){
            loggedIn = true;
        }
        return loggedIn;

    }

}
