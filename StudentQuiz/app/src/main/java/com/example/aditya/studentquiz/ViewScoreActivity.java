package com.example.aditya.studentquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by aditya on 07-05-2017.
 */

public class ViewScoreActivity extends Activity{
    private TextView quant;
    private TextView vocab;
    private TextView iq;
    private TextView currentAffairs;
    private TextView puzzle;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        puzzle=(TextView)findViewById(R.id.puzzle);
        iq=(TextView)findViewById(R.id.iq);
        quant=(TextView)findViewById(R.id.quant);
        currentAffairs=(TextView)findViewById(R.id.ca);
        vocab=(TextView)findViewById(R.id.vocab);

        firebaseAuth=FirebaseAuth.getInstance();
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    vocab.setText(dataSnapshot.child("vocab").getValue().toString());
                    quant.setText(dataSnapshot.child("quant").getValue().toString());
                    iq.setText(dataSnapshot.child("iq").getValue().toString());
                    currentAffairs.setText(dataSnapshot.child("ca").getValue().toString());
                    puzzle.setText(dataSnapshot.child("puzzle").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
