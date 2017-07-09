package com.example.stanislavk.profpref.ui.test.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.example.stanislavk.profpref.ui.test.fragments.FragmentTest;
import com.example.stanislavk.profpref.ui.test.presenters.TestPresenter;
import com.example.stanislavk.profpref.ui.test.views.TestView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class TestActivity extends BaseActivity implements TestView {

    @InjectPresenter TestPresenter mPresenter;
    static final int PAGE_COUNT = 10;
    @BindView(R.id.vp_test) ViewPager mVPtest;

    PagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mVPtest.setAdapter(pagerAdapter);
        mVPtest.setPageTransformer(true, new DepthPageTransformer());

        mVPtest.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentTest.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
}
