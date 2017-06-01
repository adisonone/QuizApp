package com.example.aditya.studentquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by aditya on 06-05-2017.
 */

public class SignupActivity extends Activity {
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etEnroll;
    private EditText etPassword;
    private Spinner spinner;
    private EditText etConfirmPassword;
    private Button register;
    private ArrayAdapter adapter;
    private String className;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        spinner=(Spinner) findViewById(R.id.cls);
        etName=(EditText)findViewById(R.id.name);
        etPhone=(EditText)findViewById(R.id.phone);
        etEmail=(EditText)findViewById(R.id.email);
        etPassword=(EditText)findViewById(R.id.password);
        etConfirmPassword=(EditText)findViewById(R.id.confirmPassword);
        etEnroll=(EditText)findViewById(R.id.enroll);
        register=(Button)findViewById(R.id.register);

        String []list={"select","1year","2year","3year","4year"};
        adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register() {

        final String name=etName.getText().toString();
        final String phone=etPhone.getText().toString();
        final String email=etEmail.getText().toString();
        final String enroll=etEnroll.getText().toString();
        final String password=etPassword.getText().toString();
        String confirmPassword=etConfirmPassword.getText().toString();
        className=spinner.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(SignupActivity.this, "enter Name", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(TextUtils.isEmpty(enroll)){
            Toast.makeText(SignupActivity.this, "enter Enrollment", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignupActivity.this, "enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(SignupActivity.this, "enter Phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if(className.equals("select")){
            Toast.makeText(this, "select class", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(SignupActivity.this, "enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(SignupActivity.this, "re-enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            databaseReference=FirebaseDatabase.getInstance().getReference().child("student");
                            DatabaseReference ref=databaseReference.child(firebaseAuth.getCurrentUser().getUid());
                            ref.child("name").setValue(name);
                            ref.child("enroll").setValue(enroll);
                            ref.child("email").setValue(email);
                            ref.child("phone").setValue(phone);
                            ref.child("password").setValue(password);
                            ref.child("class").setValue(className);
                            ref.child("quant").setValue("N.A");
                            ref.child("ca").setValue("N.A");
                            ref.child("iq").setValue("N.A");
                            ref.child("vocab").setValue("N.A");
                            ref.child("puzzle").setValue("N.A");
                        }
                    });

                }
            }
        });

        Toast.makeText(this, "Student Registred", Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();
        finish();
    }
}
