package com.example.aditya.studentquiz;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.aditya.studentquiz.LoginActivity.currentUser;

/**
 * Created by aditya on 07-05-2017.
 */

public class ViewProfileActivity extends Activity {
    private EditText name;
    private EditText enroll;
    private EditText className;
    private EditText email;
    private Button save;
    private EditText phone;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference currentUsers;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        enroll=(EditText)findViewById(R.id.enroll);
        password=(EditText)findViewById(R.id.password);
        save=(Button)findViewById(R.id.save);
        className=(EditText)findViewById(R.id.cls);

        firebaseAuth=FirebaseAuth.getInstance();
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                phone.setText(dataSnapshot.child("phone").getValue().toString());
                enroll.setText(dataSnapshot.child("enroll").getValue().toString());
                className.setText(dataSnapshot.child("class").getValue().toString());
                password.setText(dataSnapshot.child("password").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("student");
         currentUsers = databaseReference.child(currentUser);
        currentUsers.child("name").setValue(name.getText().toString());
        currentUsers.child("phone").setValue(phone.getText().toString());
        currentUsers.child("enroll").setValue(enroll.getText().toString());
        Toast.makeText(ViewProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();
    }
}
