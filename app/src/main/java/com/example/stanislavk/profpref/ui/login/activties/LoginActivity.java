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

    @BindView(R.id.tv_info) TextView mTVinfo;
    @BindView(R.id.iv_info) ImageView mIVinfo;


    private ArrayList<ImageView> mKeysBoardImages = new ArrayList<>();
    private ArrayList<ImageView> mKeysInputImages = new ArrayList<>();

    private String mPassword = "";
    private String mLogin = "";

    FirebaseStorage storageRef = FirebaseStorage.getInstance();

    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter.checkAuthFromSharedPref(this);

        ButterKnife.bind(this);

        mKeysBoardImages.add(mIVCell_1);
        mKeysBoardImages.add(mIVCell_2);
        mKeysBoardImages.add(mIVCell_3);
        mKeysBoardImages.add(mIVCell_4);
        mKeysBoardImages.add(mIVCell_5);
        mKeysBoardImages.add(mIVCell_6);
        mKeysBoardImages.add(mIVCell_7);

        mKeysInputImages.add(mIVlog_1);
        mKeysInputImages.add(mIVlog_2);
        mKeysInputImages.add(mIVlog_3);
        mKeysInputImages.add(mIVlog_4);
        mKeysInputImages.add(mIVlog_5);
        mKeysInputImages.add(mIVlog_6);
        mKeysInputImages.add(mIVlog_7);

        mIVCell_1.setOnClickListener(v -> {

            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_1, mKeysInputImages.get(mLogin.length()), 1);
                mLogin = mLogin.concat("1");
            } else if (mPassword.length() < 7){
                animateKeyToTarget(mIVCell_1, mKeysInputImages.get(mPassword.length()), 1);
                mPassword = mPassword.concat("1");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        mIVCell_2.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_2, mKeysInputImages.get(mLogin.length()), 2);
                mLogin = mLogin.concat("2");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_2, mKeysInputImages.get(mPassword.length()), 2);
                mPassword = mPassword.concat("2");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        mIVCell_3.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_3, mKeysInputImages.get(mLogin.length()), 3);
                mLogin = mLogin.concat("3");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_3, mKeysInputImages.get(mPassword.length()), 3);
                mPassword = mPassword.concat("3");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        mIVCell_4.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_4, mKeysInputImages.get(mLogin.length()), 4);
                mLogin = mLogin.concat("4");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_4, mKeysInputImages.get(mPassword.length()), 4);
                mPassword = mPassword.concat("4");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        mIVCell_5.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_5, mKeysInputImages.get(mLogin.length()), 5);
                mLogin = mLogin.concat("5");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_5, mKeysInputImages.get(mPassword.length()), 5);
                mPassword = mPassword.concat("5");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }

        });

        mIVCell_6.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_6, mKeysInputImages.get(mLogin.length()), 6);
                mLogin = mLogin.concat("6");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_6, mKeysInputImages.get(mPassword.length()), 6);
                mPassword = mPassword.concat("6");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        mIVCell_7.setOnClickListener(v -> {
            if (mLogin.length() < 7) {
                animateKeyToTarget(mIVCell_7, mKeysInputImages.get(mLogin.length()), 7);
                mLogin = mLogin.concat("7");
            } else if (mPassword.length() < 7) {
                animateKeyToTarget(mIVCell_7, mKeysInputImages.get(mPassword.length()), 7);
                mPassword = mPassword.concat("7");
            }

            if (mLogin.length() == 7 && mPassword.length() == 0) {
                mPresenter.checkLogin(mLogin);
            }

            if (mPassword.length() == 7) {
                mPresenter.checkPassword(mPassword);
            }
        });

        setKeysBoardImages(mKeysBoardImages);
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

         for (ImageView view : mKeysInputImages) {
             view.setVisibility(View.INVISIBLE);
         }
    }

    @Override
    public void onInVisibleProgressBar() {
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        for (ImageView view : mKeysBoardImages) {
            view.setVisibility(View.VISIBLE);
        }

        for (ImageView view : mKeysInputImages) {
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
            for (ImageView imageView : mKeysInputImages) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.activity_login_cell_drawable));
                setPulseAnimation(imageView);
            }
            mLogin = "";
        } else {

            for (ImageView object : mKeysInputImages) {
                object.setVisibility(View.GONE);
            }
            mPassword = "";
        }

    }

    @Override
    public void onSetupPasswordMode() {
       for (ImageView object : mKeysInputImages) {
           object.setImageResource(R.drawable.activity_login_cell_drawable);
       }

        float startX = mIVinfo.getX();
        float startY = mIVinfo.getY();

        int littleShift = 150;

        int durationAnimLeft = 800;
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

    private void setKeysBoardImages(ArrayList<ImageView> keyBoardImages){
        keyBoardImages.get(0).setImageResource(R.drawable.activity_login_keyboard_1);
        keyBoardImages.get(1).setImageResource(R.drawable.activity_login_keyboard_2);
        keyBoardImages.get(2).setImageResource(R.drawable.activity_login_keyboard_3);
        keyBoardImages.get(3).setImageResource(R.drawable.activity_login_keyboard_4);
        keyBoardImages.get(4).setImageResource(R.drawable.activity_login_keyboard_5);
        keyBoardImages.get(5).setImageResource(R.drawable.activity_login_keyboard_6);
        keyBoardImages.get(6).setImageResource(R.drawable.activity_login_keyboard_7);
    }

    private void setImageToTargetFromFB(ImageView target, int numberImage){
        String reference = "entrance/" + (numberImage) + ".png";
        setImageFromFB(getBaseContext(), target, storageRef.getReference(reference));
    }

    private void setImageToTargetFromLocal(ImageView target, int numberImage){
        target.setImageResource(
                getResources()
                        .getIdentifier("activity_login_keyboard_" + numberImage ,
                                       "drawable", getBaseContext().getPackageName())
        );
    }

    private void animateKeyToTarget(View object, View target, int numberImage) {

        float centreX = target.getX();
        float centreY = target.getY();

        float backX = object.getX();
        float backY = object.getY();

        AnimatorSet animSetXY = new AnimatorSet();

        ObjectAnimator animX = ObjectAnimator.ofFloat(object, "x", centreX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(object, "y", centreY);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(object, "scaleX", 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(object, "scaleY", 0.6f);

        ObjectAnimator fadeAnimObject = ObjectAnimator.ofFloat(object, "alpha", 1f, 0f);


        ObjectAnimator fadeAnimTarget = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);
        fadeAnimTarget.setDuration(1300);

        animSetXY.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                object.setClickable(false);
                target.setBackgroundColor(Color.TRANSPARENT);
                fadeAnimTarget.start();

                setImageToTargetFromLocal((ImageView) target, numberImage);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                object.animate().x(backX).setDuration(0).y(backY).scaleX(1f).scaleY(1f).start();
                object.animate().alpha(1f).setDuration(100).start();
                object.setClickable(true);

            }
        });
        animSetXY.playTogether(animX,animY,scaleX,scaleY);
        animSetXY.play(fadeAnimObject);
        animSetXY.setDuration(1200);
        animSetXY.start();
    }
}
