package com.example.stanislavk.profpref.ui.test.activities;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.test.adapters.TestPagerAdapter;
import com.example.stanislavk.profpref.ui.test.custom.ViewPagerCustomDuration;
import com.example.stanislavk.profpref.ui.test.models.TestAnswerModel;
import com.example.stanislavk.profpref.ui.test.presenters.TestPresenter;
import com.example.stanislavk.profpref.ui.test.views.TestView;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class TestActivity extends BaseActivity implements TestView {

    @InjectPresenter TestPresenter mPresenter;
    @BindView(R.id.vp_test) ViewPagerCustomDuration mVPtest;

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @BindView(R.id.btn_left_arrow) ImageView mBTNlefttArrow;
    @BindView(R.id.btn_right_arrow) ImageView mBTNrightArrow;
    @BindView(R.id.btn_stop) ImageView mBTNstopTest;
    @BindView(R.id.tv_title) TextView mTVtitle;

    private TestPagerAdapter mPagerAdapter;
    private Animation mFadeInAnimation, mFadeOutAnimation;

    private int mCurrentAnimation = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


        mPresenter.getAllsettingsTest();
        mVPtest.setScrollDurationFactor(12);
        mPagerAdapter = new TestPagerAdapter(getSupportFragmentManager());
        mVPtest.setAdapter(mPagerAdapter);
        mVPtest.setPageTransformer(true, new ForegroundToBackgroundTransformer());

        mVPtest.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTVtitle.setText(mPresenter.getAnswers().get(position).getTitle());
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
               // mBTNlike.setVisibility(View.INVISIBLE);
               // mBTNdislike.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ViewGroup) findViewById(R.id.ll_middle)).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }
    }

    @OnClick(R.id.btn_like)
    public void like() {
        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNlike.startAnimation(mFadeOutAnimation);

        mBTNlike.setClickable(false);
        mBTNdislike.setClickable(false);

        mCurrentAnimation=0;

        mPresenter.setAnswer(mVPtest.getCurrentItem(), 1);
    }
    @OnClick(R.id.btn_dislike)
    public void dislike() {
        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNdislike.startAnimation(mFadeOutAnimation);

        mBTNdislike.setClickable(false);
        mBTNlike.setClickable(false);

        mCurrentAnimation++;

        mPresenter.setAnswer(mVPtest.getCurrentItem(), -1);
    }

    @Override
    public void onStartTest(ModelSettings settings,
                            ModelManageButtons buttons,
                            StorageReference btnLike,
                            StorageReference btnDislike,
                            StorageReference btnLeftArrow,
                            StorageReference btnRightArrow,
                            StorageReference btnStopTest,
                            ArrayList<ModelCategories> ListCategories,
                            String key,
                            String currentTest,
                            int currentQuestion) {

        setImageFromFB(this, mBTNlike, btnLike);
        setImageFromFB(this, mBTNdislike, btnDislike);
        setImageFromFB(this, mBTNstopTest, btnStopTest);

        if (settings.text.equals("true")) {
            mTVtitle.setVisibility(View.VISIBLE);
        } else {
            mTVtitle.setVisibility(View.GONE);
        }
        if (settings.swap.equals("true")) {
            setImageFromFB(this, mBTNlefttArrow, btnLeftArrow);
            setImageFromFB(this, mBTNrightArrow, btnRightArrow);
        } else {
            mBTNlefttArrow.setVisibility(View.INVISIBLE);
            mBTNrightArrow.setVisibility(View.INVISIBLE);
        }


        ArrayList <String> links = new ArrayList<>();
        ArrayList <TestAnswerModel> answers = new ArrayList<>();

        int catCounter = 0;
        for (ModelCategories categories : ListCategories){
            int quesCounter = 0;
            for (ModelQuestion question : categories.getQuestions()) {
                TestAnswerModel answerModel = new TestAnswerModel();

                String link = FIREBASE_TESTS + "/"
                        + currentTest+ "/"
                        + catCounter+ "/"
                        + quesCounter;

                answerModel.setCategory(categories.getNameCategory());
                answerModel.setFirebasePictureLink(link);
                answerModel.setTitle(question.getTitle());

                links.add(link);
                answers.add(answerModel);

                quesCounter++;
            }
            catCounter++;
        }

        mPagerAdapter.setLinksList(links);
        mPresenter.setAnswers(answers);

        mVPtest.setCurrentItem(currentQuestion);
        mTVtitle.setText(mPresenter.getAnswers().get(0).getTitle());
    }

    @Override
    public void onShowQuestion(StorageReference imgQuestion) {

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
