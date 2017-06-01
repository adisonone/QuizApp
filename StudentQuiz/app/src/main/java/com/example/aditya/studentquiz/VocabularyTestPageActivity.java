package com.example.aditya.studentquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class VocabularyTestPageActivity extends Activity{

    private DatabaseReference databaseReference;
    private TextView tvQues;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private Button next;
    private Button previous;
    public static int quesNo=1;
    private String choice;
    private String ans;
    public  static int score;
    private TextView clock;
    int milSec=100;
    int min=19;
    int sec=60;
    public static boolean flag=false;
    private FirebaseAuth firebaseAuth;
    public static int count[]=new int[16];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_test_page);

        tvQues=(TextView)findViewById(R.id.ques);
        clock=(TextView)findViewById(R.id.clock);
        radioGroup=(RadioGroup)findViewById(R.id.rg) ;
        radioButton1=(RadioButton)findViewById(R.id.a);
        radioButton2=(RadioButton)findViewById(R.id.b);
        radioButton3=(RadioButton)findViewById(R.id.c);
        radioButton4=(RadioButton)findViewById(R.id.d);
        previous=(Button)findViewById(R.id.previous);
        next=(Button)findViewById(R.id.next);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        showQues();

        new CountDownTimer(1200000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(milSec>0){
                    milSec--;
                }
                if(milSec==0){
                    milSec=100;
                    sec--;
                }
                if(sec==0){
                    min--;
                    sec=60;
                }
                clock.setText(min+":"+sec+":"+milSec);
            }

            @Override
            public void onFinish() {
                calculateScore();
                submitScore();
                showScore();
            }
        }.start();

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                if(quesNo>1) {
                    quesNo--;
                    showQues();
                }
                if(quesNo<10){
                    next.setText("Next");
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                if(quesNo<10) {
                    quesNo++;
                    next.setText("Next");
                    showQues();
                }
                if(quesNo==10){
                    next.setText("submit");
                    quesNo++;
                    return;
                }
                if(quesNo>10) {
                    next.setText("Submit");
                    score = 0;
                    StudentPageActivity.flag = true;
                    calculateScore();

                    submitScore();

                    showScore();
                }
            }
        });
    }

    public void showScore() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(VocabularyTestPageActivity.this);
        alertDialog.setMessage("Your Score is"+score+"/15").setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StudentPageActivity.subject="vocab";
                        StudentPageActivity.count=count;
                        StudentPageActivity.flag=false;
                        finishAfterTransition();
                        startActivity(new Intent(VocabularyTestPageActivity.this,ShowQuesAnsActivity.class));

                    }
                });
        AlertDialog alert=alertDialog.create();
        alert.setTitle("Score");
        alert.show();
    }

    public void calculateScore() {

        for(int i=1;i<=4;i++){
            if(count[i]==1){
                score++;
            }
        }
    }

    public void submitScore() {

        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference1=databaseReference.child("student").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference1.child("vocab").setValue(score);
    }

    public void showQues(){

        DatabaseReference databaseReference1=databaseReference.child("puzzle-ques");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(quesNo+"")){

                    String ques=dataSnapshot.child(quesNo+"").child("ques").getValue().toString();
                    final String a=dataSnapshot.child(quesNo+"").child("a").getValue().toString();
                    final String b=dataSnapshot.child(quesNo+"").child("b").getValue().toString();
                    final String c=dataSnapshot.child(quesNo+"").child("c").getValue().toString();
                    final String d=dataSnapshot.child(quesNo+"").child("d").getValue().toString();
                    ans=dataSnapshot.child(quesNo+"").child("ans").getValue().toString();
                    choice="";

                    tvQues.setText(ques);
                    radioButton1.setText("A:"+a);
                    radioButton2.setText("B:"+b);
                    radioButton3.setText("C:"+c);
                    radioButton4.setText("D:"+d);


                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                            int rgid =group.getCheckedRadioButtonId();
                            switch (rgid){
                                case R.id.a:
                                    choice=a;
                                    break;
                                case R.id.b:
                                    choice=b;
                                    break;
                                case R.id.c:
                                    choice=c;
                                    break;
                                case R.id.d:
                                    choice=d;
                                    break;
                            }

                            checkAns();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
        startActivity(new Intent(VocabularyTestPageActivity.this,StudentPageActivity.class));
    }

    public void checkAns() {
        if(choice.equals(ans)){
            count[quesNo]=1;
        }
        else{
            count[quesNo]=0;
        }
    }
}
