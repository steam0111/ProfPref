package com.example.stanislavk.profpref.ui.test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.ui.test.fragments.TestQuestionFragment;
import com.example.stanislavk.profpref.ui.test.models.TestAnswerModel;

import java.util.ArrayList;

/**
 * Created by LasVegas on 10.07.2017.
 */

public class TestPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<TestAnswerModel> mQuestionList = new ArrayList<>();


    public TestPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TestQuestionFragment.newInstance(position,
                                                mQuestionList.get(position).getFirebasePictureLink(),
                                                mQuestionList.get(position).getContentType());
    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }

    public void setQuestionsList(ArrayList<TestAnswerModel> listLinks) {
        this.mQuestionList = listLinks;
        notifyDataSetChanged();
    }
}