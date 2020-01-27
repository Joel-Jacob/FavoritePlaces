package com.example.googleplacesofintrest.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.googleplacesofintrest.R;
import com.example.googleplacesofintrest.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView signUpTextView;
    EditText signupEmailEdittext;
    EditText signupPasswordEditText;
    EditText signupVerifyPasswordEdittext;
    Button signupButton;
    ConstraintLayout constraintLayout;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        setUp();
        checkUserLoggedIn();

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLoginUserInput()){
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    loginUser(new User(email,password));
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSignUpUserInput()){
                    String email = signupEmailEdittext.getText().toString().trim();
                    String password = signupPasswordEditText.getText().toString().trim();

                    signUpNewUser(new User(email,password));
                }
            }
        });

    }

    private boolean checkSignUpUserInput() {
        if(signupEmailEdittext.getText().toString().trim().length() == 0||
                signupPasswordEditText.getText().toString().trim().length() == 0||
                signupVerifyPasswordEdittext.getText().toString().trim().length() == 0){
            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!signupPasswordEditText.getText().toString().trim().equals(signupVerifyPasswordEdittext.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
            return false;
        }else if(signupPasswordEditText.getText().toString().trim().length() < 8){
            Toast.makeText(getApplicationContext(), "Password must be greater than 8 charecters", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    private boolean checkLoginUserInput() {
        if(emailEditText.getText().toString().trim().length()==0||
                passwordEditText.getText().toString().trim().length()==0){
            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    private void setUp() {
        emailEditText = findViewById(R.id.email_et);
        passwordEditText = findViewById(R.id.password_et);
        loginButton = findViewById(R.id.login_bt);
        signUpTextView = findViewById(R.id.signup_tv);
        signupEmailEdittext = findViewById(R.id.signup_email_et);
        signupPasswordEditText = findViewById(R.id.signup_password_et);
        signupVerifyPasswordEdittext = findViewById(R.id.signup_verify_password_et);
        signupButton = findViewById(R.id.signup_bt);
        constraintLayout = findViewById(R.id.signup_constraintLayout);
    }

    private void checkUserLoggedIn() {
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            Toast.makeText(this, "User needs to log in", Toast.LENGTH_SHORT).show();

        }else{
            //loadHomeFragment();
            //take them to maps activity
            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
            startActivity(intent);
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUpNewUser(User signUpUser) {

        firebaseAuth.createUserWithEmailAndPassword(signUpUser.getEmailAddress(), signUpUser.getPassword())
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "User Created successfully", Toast.LENGTH_SHORT).show();
                        //go to maps activity
                        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, task.getException().getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void loginUser(User logInUser) {
        firebaseAuth.signInWithEmailAndPassword(
                logInUser.getEmailAddress(),
                logInUser.getPassword())
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Login Complete", Toast.LENGTH_SHORT).show();
                        //go to maps activity
                        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, task.getException().getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
