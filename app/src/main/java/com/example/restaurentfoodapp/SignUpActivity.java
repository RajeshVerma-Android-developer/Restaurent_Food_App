package com.example.restaurentfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputUserName,inputPhoneNumber,inputEmail,inputPassword;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //getSupportActionBar().setTitle("Sign Up");

        //Jump to LoginPage when already have an account..
        TextView textView = findViewById(R.id.tv_GoToLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        //taking firebase Instance
        mAuth=FirebaseAuth.getInstance();

        //Creating new account..
        inputUserName=findViewById(R.id.et_Register_UserName);
        inputPhoneNumber=findViewById(R.id.et_Register_Number);
        inputEmail=findViewById(R.id.et_Register_Email);
        inputPassword=findViewById(R.id.et_Register_Password);
        Button buttonRegister = findViewById(R.id.btn_Register);
        progressbar = findViewById(R.id.progressbar);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        progressbar.setVisibility(View.VISIBLE);
        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(),"Please enter UserName",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Please enter Password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Please enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(),"Please enter PhoneNumber",Toast.LENGTH_LONG).show();
            return;
        }

        //Create new user or Register new user..
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!",
                                    Toast.LENGTH_LONG).show();

                            //hide teh progressBar..
                            progressbar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent = new Intent(SignUpActivity.this,
                                    SignInActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // Registration failed
                            Toast.makeText(getApplicationContext(), "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG).show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}