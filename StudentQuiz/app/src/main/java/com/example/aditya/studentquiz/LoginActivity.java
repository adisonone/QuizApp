package com.example.aditya.studentquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends Activity {

    private DatabaseReference databaseReference;
    private EditText etEmail;
    private EditText etPassword;
    private Button login;
    private FirebaseAuth firebaseAuth;
    public static String currentUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        etEmail=(EditText)findViewById(R.id.email);
        etPassword=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {

        progressDialog=new ProgressDialog(LoginActivity.this);
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

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    currentUser=email;
                    finishAfterTransition();
                    startActivity(new Intent(LoginActivity.this,StudentPageActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Wrong Email/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
