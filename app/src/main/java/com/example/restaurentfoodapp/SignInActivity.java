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

import java.net.Authenticator;

public class SignInActivity extends AppCompatActivity {

    private EditText getUserName,getPassword;
    private Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //getSupportActionBar().setTitle("Sign In");

        //goto Register page if have no account..
        TextView textView = findViewById(R.id.tv_GoToRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //taking instance of FirebaseAuth
        mAuth=FirebaseAuth.getInstance();

        //initialising all view through their id..
        getUserName=findViewById(R.id.et_SignIn_UserName);
        getPassword=findViewById(R.id.et_SignIn_Password);
        loginButton=findViewById(R.id.btn_LogIn);
        progressBar=findViewById(R.id.progressbar);

        // Set on Click Listener on Sign-in button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {
        //setting the visibility of ProgressBar..
        progressBar.setVisibility(View.VISIBLE);

        //taking the values in both EditText..
        String userName = getUserName.getText().toString();
        String password = getPassword.getText().toString();

        //validations for input email and password
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!",
                            Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!",
                            Toast.LENGTH_LONG).show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG).show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(SignInActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }

                                else {
                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG).show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
    }

}