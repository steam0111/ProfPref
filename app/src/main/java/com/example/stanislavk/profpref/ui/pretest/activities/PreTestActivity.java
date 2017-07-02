package com.example.stanislavk.profpref.ui.pretest.activities;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stanislavk.profpref.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
        ButterKnife.bind(this);

        //String text_animate = "If you like, pick this button like";
        //startAnimation(TV_text1, 4000,text_animate);
    }
    /*public static void startAnimation(final TextView tv_view ,
                                      final long speed,
                                      String text) {
        final ValueAnimator animator = ValueAnimator.ofInt(0,text.length());

        animator.setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(speed); //Sets the length of the animation.

        animator.addUpdateListener(animation -> {
            int  current_value = (int)animation.getAnimatedValue();
            String text2 = text.subSequence(0,current_value).toString();
            tv_view.setText(text2);
        });
        animator.start();
    }*/
}
