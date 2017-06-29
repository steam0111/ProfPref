package com.example.stanislavk.profpref.ui.login.activties;

import android.animation.ValueAnimator;
import android.os.Bundle;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBTNlogin.setOnClickListener(this);

        final ImageView backgroundOne = (ImageView) findViewById(R.id.iv_cloud);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        //int width = displayMetrics.widthPixels;

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(30000L); //Sets the length of the animation.
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            final float width = displayMetrics.widthPixels;
            final float translationX = width * progress;
            backgroundOne.setTranslationX(translationX);
            mIVcloud2.setTranslationX(translationX);
        });
        animator.start();
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
}
