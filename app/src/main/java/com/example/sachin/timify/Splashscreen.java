package com.example.sachin.timify;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splashscreen extends Activity {
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();         // Finishes splashscreen activity
            }
        }).start();
    }

    private void doWork() {
        for (int progress=10; progress<100; progress+=30) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);        //For Running Progress Bar
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(Splashscreen.this, MainActivity.class);  //This starts new MainActivity
        startActivity(intent);
    }
}
