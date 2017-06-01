package com.example.aditya.studentquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aditya on 07-05-2017.
 */

public class StudentPageActivity extends Activity{
    private Button quant;
    private Button vocab;
    private Button iq;
    private Button currentAffairs;
    private Button puzzle;
    public  static boolean flag=false;
    private Button logout;
    public static String subject="";
    private Button viewProfile;
    private Button viewScore;
    public static int count[]=new int[16];
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        quant=(Button)findViewById(R.id.quant);
        iq=(Button)findViewById(R.id.iq);
        currentAffairs=(Button)findViewById(R.id.ca);
        puzzle=(Button)findViewById(R.id.puzzle);
        vocab=(Button)findViewById(R.id.vocab);
        logout=(Button)findViewById(R.id.logout);
        viewScore=(Button)findViewById(R.id.viewScore);
        viewProfile=(Button)findViewById(R.id.viewProfile);
        firebaseAuth=FirebaseAuth.getInstance();

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentPageActivity.this,ViewProfileActivity.class));
            }
        });

        viewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentPageActivity.this,ViewScoreActivity.class));
            }
        });

        quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentScore="N.A";
                databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(currentScore.equals(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).child("quant").getValue().toString())){
                            finishAfterTransition();
                            startActivity(new Intent(StudentPageActivity.this,QuantativeTestPageActivity.class));
                        }
                        else if(StudentPageActivity.flag){

                        }
                        else{
                            Toast.makeText(StudentPageActivity.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        vocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentScore="N.A";
                databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(currentScore.equals(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).child("vocab").getValue().toString())){
                            finishAfterTransition();
                            startActivity(new Intent(StudentPageActivity.this,VocabularyTestPageActivity.class));
                        }
                        else if(StudentPageActivity.flag){

                        }
                        else{
                            Toast.makeText(StudentPageActivity.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        iq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentScore="N.A";
                databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(currentScore.equals(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).child("iq").getValue().toString())){
                            finishAfterTransition();
                            startActivity(new Intent(StudentPageActivity.this,IQTestPageActivity.class));
                        }
                        else if(StudentPageActivity.flag){

                        }
                        else{
                            Toast.makeText(StudentPageActivity.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        currentAffairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentScore="N.A";
                databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(currentScore.equals(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).child("ca").getValue().toString())){
                            finishAfterTransition();
                            startActivity(new Intent(StudentPageActivity.this,CurrentAffairsTestPageActivity.class));
                        }
                        else if(StudentPageActivity.flag){

                        }
                        else{
                            Toast.makeText(StudentPageActivity.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentScore="N.A";
                databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(currentScore.equals(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).child("puzzle").getValue().toString())){
                            finishAfterTransition();
                            startActivity(new Intent(StudentPageActivity.this,PuzzleTestPageActivity.class));
                        }
                        else if(StudentPageActivity.flag){

                        }
                        else{
                            Toast.makeText(StudentPageActivity.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finishAfterTransition();
                startActivity(new Intent(StudentPageActivity.this,HomeActivity.class));
            }
        });

    }
    boolean backPressed=false;
    @Override
    public void onBackPressed() {

        if(backPressed){
            super.onBackPressed();
        }
        else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backPressed=true;
        }
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                backPressed=false;
            }
        }.start();

    }
}
