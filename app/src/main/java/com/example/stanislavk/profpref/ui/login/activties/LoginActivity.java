package com.example.stanislavk.profpref.ui.login.activties;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    @InjectPresenter LoginPresenter mPresenter;

    @BindView(R.id.iv_key_board_1) ImageView mIVCell_1;
    @BindView(R.id.iv_key_board_2) ImageView mIVCell_2;
    @BindView(R.id.iv_key_board_3) ImageView mIVCell_3;
    @BindView(R.id.iv_key_board_4) ImageView mIVCell_4;
    @BindView(R.id.iv_key_board_5) ImageView mIVCell_5;
    @BindView(R.id.iv_key_board_6) ImageView mIVCell_6;
    @BindView(R.id.iv_key_board_7) ImageView mIVCell_7;

    @BindView(R.id.iv_pass_1) ImageView mIVpass_1;
    @BindView(R.id.iv_pass_2) ImageView mIVpass_2;
    @BindView(R.id.iv_pass_3) ImageView mIVpass_3;
    @BindView(R.id.iv_pass_4) ImageView mIVpass_4;
    @BindView(R.id.iv_pass_5) ImageView mIVpass_5;
    @BindView(R.id.iv_pass_6) ImageView mIVpass_6;
    @BindView(R.id.iv_pass_7) ImageView mIVpass_7;



    private ArrayList<ImageView> mKeysBoardImages = new ArrayList<>();
    private ArrayList<ImageView> mKeysPasswordImages = new ArrayList<>();

    private String mPassword = "";
    FirebaseStorage storageRef = FirebaseStorage.getInstance();

    /*@BindView(R.id.et_password) EditText mETpassword;
    @BindView(R.id.btn_login) Button mBTNlogin;
    @BindView(R.id.iv_cloud2) ImageView mIVcloud2;
    @BindView(R.id.iv_cloud) ImageView mIVcloud1;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.til_password) TextInputLayout mTIL_password;
    @BindView(R.id.til_login) TextInputLayout mTIL_login;*/

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

        /*mBTNlogin.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        startAnimation(mIVcloud1, width + 450, 25000L, 0);
        startAnimation(mIVcloud2, width + 450, 25000L, width/2);*/




        mIVCell_1.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_1, mKeysPasswordImages.get(mPassword.length()), 1);
            mPassword = mPassword.concat("1");
        });

        mIVCell_2.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_2, mKeysPasswordImages.get(mPassword.length()),2);
            mPassword = mPassword.concat("2");
        });

        mIVCell_3.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_3, mKeysPasswordImages.get(mPassword.length()),3);
            mPassword = mPassword.concat("3");
        });

        mIVCell_4.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_4, mKeysPasswordImages.get(mPassword.length()),4);
            mPassword = mPassword.concat("4");
        });

        mIVCell_5.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_5, mKeysPasswordImages.get(mPassword.length()),5);
            mPassword = mPassword.concat("5");
        });

        mIVCell_6.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_6, mKeysPasswordImages.get(mPassword.length()),6);
            mPassword = mPassword.concat("6");
        });

        mIVCell_7.setOnClickListener(v -> {
            animateKeyToTarget(mIVCell_7, mKeysPasswordImages.get(mPassword.length()),7);
            mPassword = mPassword.concat("7");
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
         /*mProgressBar.setVisibility(ProgressBar.VISIBLE);
         mETlogin.setVisibility(View.INVISIBLE);
         mETpassword.setVisibility(View.INVISIBLE);
         mBTNlogin.setVisibility(View.INVISIBLE);
         mIVcloud2.setVisibility(View.INVISIBLE);
         mIVcloud1.setVisibility(View.INVISIBLE);
         mTIL_password.setVisibility(View.INVISIBLE);
         mTIL_login.setVisibility(View.INVISIBLE);*/

    }

    @Override
    public void onInVisibleProgressBar() {
        //mProgressBar.setVisibility(ProgressBar.INVISIBLE);
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
        /*Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
        mETlogin.setVisibility(View.VISIBLE);
        mETpassword.setVisibility(View.VISIBLE);
        mBTNlogin.setVisibility(View.VISIBLE);
        mIVcloud2.setVisibility(View.VISIBLE);
        mIVcloud1.setVisibility(View.VISIBLE);
        mTIL_password.setVisibility(View.VISIBLE);
        mTIL_login.setVisibility(View.VISIBLE);
        onInVisibleProgressBar();*/
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.btn_login) {
            if (!mETlogin.getText().toString().isEmpty() &&
                !mETpassword.getText().toString().isEmpty()) {
                mPresenter.login(mETlogin.getText().toString(), mETpassword.getText().toString());
            }
        }*/
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
            }
        });

        animSetXY.playTogether(animX,animY);
        animSetXY.play(fadeAnimObject);
        animSetXY.setDuration(1200);
        animSetXY.start();

    }
    /*public static void startAnimation(final ImageView iv_view ,
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
    }*/
}
