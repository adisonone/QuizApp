package com.example.aditya.studentquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by aditya on 06-05-2017.
 */

public class HomeActivity extends Activity {

    private Button login;
    private Button signup;
    private EditText etEmail;
    private EditText etPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public static String currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // startActivity(new Intent(HomeActivity.this,LoginActivity.class));

                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(HomeActivity.this);
                View view=getLayoutInflater().inflate(R.layout.layout_login,null);
                etEmail=(EditText) view.findViewById(R.id.email);
                etPassword=(EditText)view.findViewById(R.id.password);
                Button login=(Button)view.findViewById(R.id.login);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();
                    }
                });
                alertBuilder.setView(view);
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SignupActivity.class));
            }
        });
    }

    public void login() {

        progressDialog=new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Logging in......");

        final String email=etEmail.getText().toString();
        final String password=etPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "enter Enrollment", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    currentUser=email;
                    finishAfterTransition();
                    startActivity(new Intent(HomeActivity.this,StudentPageActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Wrong Email/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
