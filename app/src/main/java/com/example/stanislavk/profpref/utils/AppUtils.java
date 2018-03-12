package com.example.stanislavk.profpref.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.GestureDetector;
import android.view.View;

/**
 * Created by LasVegas on 12.03.2018.
 */

public class AppUtils {

    public static void animationScaleUpDown(View target, float fromScale, float toScale, int duration) {

        ObjectAnimator scaleXup = ObjectAnimator.ofFloat(target, "scaleX", fromScale);
        ObjectAnimator scaleYup = ObjectAnimator.ofFloat(target, "scaleY", fromScale);

        ObjectAnimator scaleXdown = ObjectAnimator.ofFloat(target, "scaleX", toScale);
        ObjectAnimator scaleYdown = ObjectAnimator.ofFloat(target, "scaleY", toScale);


        AnimatorSet scaleXYup = new AnimatorSet();
        scaleXYup.playTogether(scaleXup, scaleYup);
        scaleXYup.setDuration(duration);

        AnimatorSet scaleXYdown = new AnimatorSet();
        scaleXYdown.playTogether(scaleXdown, scaleYdown);
        scaleXYdown.setDuration(duration);

        AnimatorSet scaleAnim  = new AnimatorSet();

        scaleAnim.playSequentially(scaleXYup, scaleXYdown);
        scaleAnim.start();

    }

}
