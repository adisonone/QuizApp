package com.example.aditya.studentquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class FlashActivity extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flash);

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    finishAfterTransition();
                    Intent intent = new Intent(FlashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            };

            Timer timer = new Timer();
            timer.schedule(tt,3000);
        }


    }



