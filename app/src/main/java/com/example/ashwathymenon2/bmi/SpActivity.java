package com.example.ashwathymenon2.bmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SpActivity extends AppCompatActivity {
ImageView im1;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        im1=(ImageView)findViewById(R.id.im1);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.a1);
        im1.startAnimation(animation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);

                }
                catch(InterruptedException e){

                    e.printStackTrace();
                }
                Intent i =new Intent(SpActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }).start();




    }
}
