package com.example.stanislavk.profpref.ui.login.activties;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.login.presenters.LoginPresenter;
import com.example.stanislavk.profpref.ui.login.views.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    @InjectPresenter LoginPresenter mPresenter;

    @BindView(R.id.et_login) EditText mETlogin;
    @BindView(R.id.et_password) EditText mETpassword;
    @BindView(R.id.btn_login) Button mBTNlogin;
    @BindView(R.id.iv_cloud2) ImageView mIVcloud2;
    @BindView(R.id.iv_cloud) ImageView mIVcloud1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBTNlogin.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        startAnimation(mIVcloud1, width + 450, 25000L, 0);
        startAnimation(mIVcloud2, width + 450, 25000L, width/2);
    }

    @Override
    public void onNextScreen() {
        Toast.makeText(getBaseContext(),"Авторизация успешна",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(),"Авторизация провалена",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (!mETlogin.getText().toString().isEmpty() &&
                !mETpassword.getText().toString().isEmpty()) {
                mPresenter.login(mETlogin.getText().toString(), mETpassword.getText().toString());
            }
        }
    }
    public static void startAnimation(final ImageView iv_view ,
                                      final float width,
                                      final long speed,
                                      final int start_translation_x) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, 1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(speed); //Sets the length of the animation.

        animator.addUpdateListener(animation -> {
            final float progress = animation.getAnimatedFraction();
            final float translationX = width* progress;
            iv_view.setTranslationX(translationX);
        });
        animator.start();
        animator.setCurrentPlayTime(start_translation_x * 15);
    }
}
