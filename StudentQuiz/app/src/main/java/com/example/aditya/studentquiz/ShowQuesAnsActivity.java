package com.example.aditya.studentquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 16-05-2017.
 */

public class ShowQuesAnsActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;
    List<QuesAns> list;
    int i=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ques_ans);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("puzzle-ques");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    QuesAns quesAns=postSnapshot.getValue(QuesAns.class);
                    if(StudentPageActivity.count[i]==1){
                        quesAns.setResult("Correct");
                    }
                    else{
                        quesAns.setResult("Incorrect");
                    }
                    list.add(quesAns);
                    i++;
                }
                adapter=new ShowQuesAnsAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
        startActivity(new Intent(ShowQuesAnsActivity.this,StudentPageActivity.class));
        super.onBackPressed();
    }
}
