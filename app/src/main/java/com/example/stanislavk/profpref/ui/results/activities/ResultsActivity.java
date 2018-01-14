package com.example.stanislavk.profpref.ui.results.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.afterresult.activties.AfterResultActivity;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.results.adapters.ResultPagerAdapter;
import com.example.stanislavk.profpref.ui.results.models.ResultShowingModel;
import com.example.stanislavk.profpref.ui.results.presenters.ResultsPresenter;
import com.example.stanislavk.profpref.ui.results.views.ResultsView;
import com.example.stanislavk.profpref.ui.test.custom.ViewPagerCustomDuration;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

public class ResultsActivity extends BaseActivity implements ResultsView {

    @InjectPresenter ResultsPresenter mPresenter;

    @BindView(R.id.vp_test) ViewPagerCustomDuration mVPtest;
    private ResultPagerAdapter mPagerAdapter;

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @BindView(R.id.btn_left_arrow) ImageView mBTNlefttArrow;
    @BindView(R.id.btn_right_arrow) ImageView mBTNrightArrow;
    @BindView(R.id.btn_stop) ImageView mBTNstopTest;

    private Animation mFadeInAnimation, mFadeOutAnimation;
    private int mCurrentAnimation = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        mPresenter.setCurrentState();
        mPresenter.onLoadLinksCategory();
        mPresenter.getSettingsTest();

        mVPtest.setScrollDurationFactor(12);
        mPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());
        mVPtest.setAdapter(mPagerAdapter);
        mVPtest.setPageTransformer(true, new ForegroundToBackgroundTransformer());

        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
    }

    @Override
    public void onLoadLinksCategory(ArrayList<ArrayList<ResultShowingModel>> categoryLinks) {
        mPagerAdapter.setCategoryContentList(categoryLinks);
    }

    @Override
    public void onStartResult(ModelSettings settings,
                              ModelManageButtons buttons,
                              StorageReference btnLike,
                              StorageReference btnDislike,
                              StorageReference btnLeftArrow,
                              StorageReference btnRightArrow,
                              StorageReference btnStopTest) {
        setImageFromFB(this, mBTNlike, btnLike);
        setImageFromFB(this, mBTNdislike, btnDislike);
        setImageFromFB(this, mBTNstopTest, btnStopTest);

        if (settings.swap.equals("true")) {
            setImageFromFB(this, mBTNlefttArrow, btnLeftArrow);
            setImageFromFB(this, mBTNrightArrow, btnRightArrow);
        } else {
            mBTNlefttArrow.setVisibility(View.GONE);
            mBTNrightArrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNextScreen() {
        Intent intent = new Intent(this, AfterResultActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.btn_like)
    public void like() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 1);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNlike.startAnimation(mFadeOutAnimation);

        mBTNlike.setClickable(false);
        mBTNdislike.setClickable(false);

        mCurrentAnimation=0;
    }
    @OnClick(R.id.btn_dislike)
    public void dislike() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), -1);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNdislike.startAnimation(mFadeOutAnimation);

        mBTNdislike.setClickable(false);
        mBTNlike.setClickable(false);

        mCurrentAnimation++;
    }
    @OnClick(R.id.btn_left_arrow)
    public void arrowLeft() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 0);
        mVPtest.setCurrentItem(mVPtest.getCurrentItem() - 1);
    }
    @OnClick(R.id.btn_right_arrow)
    public void arrowRight() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 0);
        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

    }
    @OnClick(R.id.btn_stop)
    public void stop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this);

        builder.setPositiveButton(R.string.activity_test_dialog_continue, (dialog, id) -> {

        });
        builder.setNegativeButton(R.string.activity_test_dialog_exit, (dialog, id) -> {
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mCurrentAnimation > 0) {
                mBTNdislike.startAnimation(mFadeInAnimation);
            } else {
                mBTNlike.startAnimation(mFadeInAnimation);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };

    Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mBTNlike.setClickable(true);
            mBTNdislike.setClickable(true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };
}
