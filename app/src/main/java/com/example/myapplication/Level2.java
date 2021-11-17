package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    Dialog dialog;
    public int numLeft;
    public int numRight;
    Array array = new Array();
    Random random = new Random();
    public int count = 0; // Count of right answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level2);

        final ImageView img_left = findViewById(R.id.img_left);
        img_left.setClipToOutline(true);

        final ImageView img_right = findViewById(R.id.img_right);
        img_right.setClipToOutline(true);

        final TextView text_left = findViewById(R.id.text_left);
        final TextView text_right = findViewById(R.id.text_right);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView button_close = (TextView) dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level2.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        Button button_continue = (Button) dialog.findViewById(R.id.button_continue);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        Button levelBack = findViewById(R.id.level_back);
        levelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level2.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                }
            }
        });


        //Array for progress of game
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
                R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
                R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,
        };

        //Animation
        final Animation a = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        int side = random.nextInt(2);
        if(side == 0) {
            numLeft = random.nextInt(7);
            img_left.setImageResource(array.images1[numLeft]);
            text_left.setText("");

            numRight = random.nextInt(6) + 7;
            img_right.setImageResource(array.images1[numRight]);
            text_right.setText("");
        } else{
            numLeft = random.nextInt(5)+7;
            img_left.setImageResource(array.images1[numLeft]);
            text_left.setText("");

            numRight = random.nextInt(7);
            img_right.setImageResource(array.images1[numRight]);
            text_right.setText("");
        }

        //Handling the touch on left image
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Touch img
                    img_right.setEnabled(false);//Blocked right img
                    if(numLeft<numRight){
                        img_left.setImageResource(R.drawable.green_true);
                    }else {
                        img_left.setImageResource(R.drawable.red_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    //Untouch img
                    if(numLeft<numRight){
                        //if left image > right image
                        if (count<20) {
                            count++;
                        }
                        //
                        for(int i = 0; i<20; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for(int i = 0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {
                        //if right image > left image
                        if(count>0){
                            if(count==1){
                                count=0;
                            } else
                                count-=2;
                        }
                        for(int i = 0; i<19; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for(int i = 0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if(count==20){
                        //Exit level
                        dialog.show();
                    } else {
                        int side = random.nextInt(2);
                        if(side == 0) {
                            numLeft = random.nextInt(7);
                            img_left.setImageResource(array.images1[numLeft]);
                            img_left.startAnimation(a);
                            text_left.setText("");

                            numRight = random.nextInt(6) + 7;
                            img_right.setImageResource(array.images1[numRight]);
                            img_right.startAnimation(a);
                            text_right.setText("");
                        } else{
                            numLeft = random.nextInt(5)+7;
                            img_left.setImageResource(array.images1[numLeft]);
                            img_left.startAnimation(a);
                            text_left.setText("");

                            numRight = random.nextInt(7);
                            img_right.setImageResource(array.images1[numRight]);
                            img_right.startAnimation(a);
                            text_right.setText("");
                        }

                        img_right.setEnabled(true);//Unblocked right image
                    }
                }

                return true;
            }
        });
        //Handling the touch on right image
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Touch img
                    img_left.setEnabled(false);//Blocked left img
                    if(numLeft>numRight){
                        img_right.setImageResource(R.drawable.green_true);
                    }else {
                        img_right.setImageResource(R.drawable.red_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    //Untouch img
                    if(numLeft>numRight){
                        //if left image < right image
                        if (count<20) {
                            count++;
                        }
                        //
                        for(int i = 0; i<20; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for(int i = 0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {
                        //if right image < left image
                        if(count>0){
                            if(count==1){
                                count=0;
                            } else
                                count-=2;
                        }
                        for(int i = 0; i<19; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for(int i = 0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if(count==20){
                        //Exit level
                        dialog.show();
                    } else {
                        int side = random.nextInt(2);
                        if(side == 0) {
                            numLeft = random.nextInt(7);
                            img_left.setImageResource(array.images1[numLeft]);
                            img_left.startAnimation(a);
                            text_left.setText("");

                            numRight = random.nextInt(6) + 7;
                            img_right.setImageResource(array.images1[numRight]);
                            img_right.startAnimation(a);
                            text_right.setText("");
                        } else{
                            numLeft = random.nextInt(5)+7;
                            img_left.setImageResource(array.images1[numLeft]);
                            img_left.startAnimation(a);
                            text_left.setText("");

                            numRight = random.nextInt(7);
                            img_right.setImageResource(array.images1[numRight]);
                            img_right.startAnimation(a);
                            text_right.setText("");
                        }

                        img_left.setEnabled(true);//Unblocked left image
                    }
                }

                return true;
            }
        });
    }



    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level2.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e){

        }
    }
}