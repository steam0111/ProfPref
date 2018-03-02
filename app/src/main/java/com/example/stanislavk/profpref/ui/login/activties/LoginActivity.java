package com.example.stanislavk.profpref.ui.login.activties;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.login.presenters.LoginPresenter;
import com.example.stanislavk.profpref.ui.login.views.LoginView;
import com.example.stanislavk.profpref.ui.pretest.activities.PreTestActivity;
import com.example.stanislavk.profpref.ui.results.activities.ResultsActivity;
import com.example.stanislavk.profpref.ui.test.activities.TestActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

public class LoginActivity extends BaseActivity implements LoginView {

    @InjectPresenter LoginPresenter mPresenter;

    @BindView(R.id.iv_key_board_1) ImageView mIVCell_1;
    @BindView(R.id.iv_key_board_2) ImageView mIVCell_2;
    @BindView(R.id.iv_key_board_3) ImageView mIVCell_3;
    @BindView(R.id.iv_key_board_4) ImageView mIVCell_4;
    @BindView(R.id.iv_key_board_5) ImageView mIVCell_5;
    @BindView(R.id.iv_key_board_6) ImageView mIVCell_6;
    @BindView(R.id.iv_key_board_7) ImageView mIVCell_7;

    @BindView(R.id.iv_log_1) ImageView mIVlog_1;
    @BindView(R.id.iv_log_2) ImageView mIVlog_2;
    @BindView(R.id.iv_log_3) ImageView mIVlog_3;
    @BindView(R.id.iv_log_4) ImageView mIVlog_4;
    @BindView(R.id.iv_log_5) ImageView mIVlog_5;
    @BindView(R.id.iv_log_6) ImageView mIVlog_6;
    @BindView(R.id.iv_log_7) ImageView mIVlog_7;

    @BindView(R.id.iv_pass_1) ImageView mIVpass_1;
    @BindView(R.id.iv_pass_2) ImageView mIVpass_2;
    @BindView(R.id.iv_pass_3) ImageView mIVpass_3;
    @BindView(R.id.iv_pass_4) ImageView mIVpass_4;
    @BindView(R.id.iv_pass_5) ImageView mIVpass_5;
    @BindView(R.id.iv_pass_6) ImageView mIVpass_6;
    @BindView(R.id.iv_pass_7) ImageView mIVpass_7;

    @BindView(R.id.tv_info) TextView mTVinfo;
    @BindView(R.id.iv_info) ImageView mIVinfo;


    private ArrayList<ImageView> mKeysBoardImages = new ArrayList<>();
    private ArrayList<ImageView> mKeysPasswordImages = new ArrayList<>();
    private ArrayList<ImageView> mKeysLoginImages = new ArrayList<>();

    private String mPassword = "";
    private String mLogin = "";

    FirebaseStorage storageRef = FirebaseStorage.getInstance();

    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mKeysBoardImages.add(mIVCell_1);
        mKeysBoardImages.add(mIVCell_2);
        mKeysBoardImages.add(mIVCell_3);
        mKeysBoardImages.add(mIVCell_4);
        mKeysBoardImages.add(mIVCell_5);
        mKeysBoardImages.add(mIVCell_6);
        mKeysBoardImages.add(mIVCell_7);

        mKeysPasswordImages.add(mIVpass_1);
        mKeysPasswordImages.add(mIVpass_2);
        mKeysPasswordImages.add(mIVpass_3);
        mKeysPasswordImages.add(mIVpass_4);
        mKeysPasswordImages.add(mIVpass_5);
        mKeysPasswordImages.add(mIVpass_6);
        mKeysPasswordImages.add(mIVpass_7);

        mKeysLoginImages.add(mIVlog_1);
        mKeysLoginImages.add(mIVlog_2);
        mKeysLoginImages.add(mIVlog_3);
        mKeysLoginImages.add(mIVlog_4);
        mKeysLoginImages.add(mIVlog_5);
        mKeysLoginImages.add(mIVlog_6);
        mKeysLoginImages.add(mIVlog_7);

        mIVCell_1.setOnClickListener(v -> {

            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_1, mKeysLoginImages.get(mLogin.length()), 1);
                mLogin = mLogin.concat("1");
            } else if (mPassword.length() < 7){
                animateKeyToTarget(mIVCell_1, mKeysPasswordImages.get(mPassword.length()), 1);
                mPassword = mPassword.concat("1");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        mIVCell_2.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_2, mKeysLoginImages.get(mLogin.length()), 2);
                mLogin = mLogin.concat("2");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_2, mKeysPasswordImages.get(mPassword.length()), 2);
                mPassword = mPassword.concat("2");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        mIVCell_3.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_3, mKeysLoginImages.get(mLogin.length()), 3);
                mLogin = mLogin.concat("3");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_3, mKeysPasswordImages.get(mPassword.length()), 3);
                mPassword = mPassword.concat("3");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        mIVCell_4.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_4, mKeysLoginImages.get(mLogin.length()), 4);
                mLogin = mLogin.concat("4");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_4, mKeysPasswordImages.get(mPassword.length()), 4);
                mPassword = mPassword.concat("4");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        mIVCell_5.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_5, mKeysLoginImages.get(mLogin.length()), 5);
                mLogin = mLogin.concat("5");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_5, mKeysPasswordImages.get(mPassword.length()), 5);
                mPassword = mPassword.concat("5");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }

        });

        mIVCell_6.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_6, mKeysLoginImages.get(mLogin.length()), 6);
                mLogin = mLogin.concat("6");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_6, mKeysPasswordImages.get(mPassword.length()), 6);
                mPassword = mPassword.concat("6");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        mIVCell_7.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_7, mKeysLoginImages.get(mLogin.length()), 7);
                mLogin = mLogin.concat("7");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_7, mKeysPasswordImages.get(mPassword.length()), 7);
                mPassword = mPassword.concat("7");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPasswrod(mPassword);
            }
        });

        setKeyBoardImages(mKeysBoardImages, storageRef);
    }

    @Override
    public void onNextScreen() {
        Intent intent = new Intent(this, PreTestActivity.class);
        startActivity(intent);
        Toast.makeText(getBaseContext(),"Авторизация успешна",Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public void onVisibleProgressBar() {
         mProgressBar.setVisibility(ProgressBar.VISIBLE);

         for (ImageView view : mKeysBoardImages) {
             view.setVisibility(View.INVISIBLE);
         }

         for (ImageView view : mKeysPasswordImages) {
             view.setVisibility(View.INVISIBLE);
         }

         for (ImageView view : mKeysLoginImages) {
             view.setVisibility(View.INVISIBLE);
         }
    }

    @Override
    public void onInVisibleProgressBar() {
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        for (ImageView view : mKeysBoardImages) {
            view.setVisibility(View.VISIBLE);
        }

        for (ImageView view : mKeysPasswordImages) {
            view.setVisibility(View.VISIBLE);
        }

        for (ImageView view : mKeysLoginImages) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTestScreen() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResultScreen() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onShowInfoMsg(String msg) {
        onInVisibleProgressBar();
    }

    @Override
    public void onDropInputField(int type) {
        if (type == LoginPresenter.DROP_LOGIN) {
            for (ImageView imageView : mKeysLoginImages) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.activity_login_cell_drawable));
                setPulseAnimation(imageView);
            }
            mLogin = "";
        } else {

            for (ImageView object : mKeysLoginImages) {
                object.setVisibility(View.GONE);
            }

            for (ImageView imageView : mKeysPasswordImages) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.activity_login_cell_drawable));
                setPulseAnimation(imageView);
            }
            mPassword = "";
        }

    }

    @Override
    public void onSetupPasswordMode() {
       for (ImageView object : mKeysLoginImages) {
           object.setVisibility(View.GONE);
       }

        for (ImageView object : mKeysPasswordImages) {
            object.setVisibility(View.VISIBLE);
        }

        float startX = mIVinfo.getX();
        float startY = mIVinfo.getY();

        int littleShift = 150;

        int durationAnimLeft = 1000;
        int durationAnimBottom = 600;

        AnimatorSet animChangeInfoLoginToPassword = new AnimatorSet();

        AnimatorSet transXwithFade = new AnimatorSet();

        ObjectAnimator transX = ObjectAnimator.ofFloat(mIVinfo, "translationX", -mIVinfo.getWidth() - littleShift);
        ObjectAnimator fadeDown = ObjectAnimator.ofFloat(mIVinfo, "alpha", 0);
        ObjectAnimator scaleX0 = ObjectAnimator.ofFloat(mTVinfo, "scaleX", 0);
        ObjectAnimator scaleY0 = ObjectAnimator.ofFloat(mTVinfo, "scaleY", 0);

        transXwithFade.playTogether(transX, fadeDown, scaleX0, scaleY0);
        transXwithFade.setDuration(durationAnimLeft);

        transXwithFade.addListener(new  AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mTVinfo.setText(R.string.activity_login_info_text_input_password);
                mIVinfo.setImageResource(R.drawable.activity_login_ic_password);
            }

        });

        ObjectAnimator toFirstX = ObjectAnimator.ofFloat(mIVinfo, "x", startX);
        toFirstX.setDuration(0);

        ObjectAnimator toTop = ObjectAnimator.ofFloat(mIVinfo, "translationY", -mIVinfo.getWidth() - littleShift);
        toTop.setDuration(0);

        AnimatorSet transYwithFade = new AnimatorSet();

        ObjectAnimator fadeUp = ObjectAnimator.ofFloat(mIVinfo, "alpha", 1);
        fadeUp.setDuration(500);

        ObjectAnimator toFirstY = ObjectAnimator.ofFloat(mIVinfo, "y", startY);
        toFirstY.setDuration(durationAnimBottom);

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mTVinfo, "scaleX", 1);
        scaleX1.setDuration(durationAnimBottom);

        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mTVinfo, "scaleY", 1);
        scaleY1.setDuration(durationAnimBottom);

        transYwithFade.playTogether(fadeUp, toFirstY, scaleX1, scaleY1);

        animChangeInfoLoginToPassword.playSequentially(transXwithFade, toFirstX, toTop, transYwithFade);

        animChangeInfoLoginToPassword.start();

    }

    private void setPulseAnimation(ImageView iv) {

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                iv,
                PropertyValuesHolder.ofFloat("scaleX", 1.15f),
                PropertyValuesHolder.ofFloat("scaleY", 1.15f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(5);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }
    private void setKeyBoardImages(ArrayList<ImageView> keyBoardImages, FirebaseStorage storage) {

        int numberPhoto = 1;
        for (ImageView imageView : keyBoardImages) {
            String reference = "entrance/" + numberPhoto;
            setImageFromFB(getBaseContext(), imageView, storage.getReference(reference + ".png"));
            numberPhoto++;
        }
    }

    private void animateKeyToTarget(View object, View target, int numberImage) {

        float centreX = target.getX();
        float centreY = target.getY();

        float backX = object.getX();
        float backY = object.getY();

        AnimatorSet animSetXY = new AnimatorSet();

        ObjectAnimator animX = ObjectAnimator.ofFloat(object, "x", centreX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(object, "y", centreY);
        ObjectAnimator fadeAnimObject = ObjectAnimator.ofFloat(object, "alpha", 1f, 0f);


        ObjectAnimator fadeAnimTarget = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);
        fadeAnimTarget.setDuration(1300);

        animSetXY.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                object.setClickable(false);
                target.setBackgroundColor(Color.TRANSPARENT);
                fadeAnimTarget.start();

                String reference = "entrance/" + (numberImage) + ".png";
                setImageFromFB(getBaseContext(), (ImageView) target, storageRef.getReference(reference));
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                object.animate().x(backX).setDuration(0).y(backY).start();
                object.animate().alpha(1f).setDuration(100).start();
                object.setClickable(true);

            }
        });
        animSetXY.playTogether(animX,animY);
        animSetXY.play(fadeAnimObject);
        animSetXY.setDuration(1200);
        animSetXY.start();
    }
}
