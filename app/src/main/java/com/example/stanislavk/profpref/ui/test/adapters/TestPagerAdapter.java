package com.example.stanislavk.profpref.ui.test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.stanislavk.profpref.ui.test.fragments.TestQuestionFragment;

import java.util.ArrayList;

/**
 * Created by LasVegas on 10.07.2017.
 */

public class TestPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mListLinks = new ArrayList<>();


    public TestPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TestQuestionFragment.newInstance(position,mListLinks.get(position));
    }

    @Override
    public int getCount() {
        return mListLinks.size();
    }

    public void setLinksList(ArrayList<String> listLinks) {
        this.mListLinks = listLinks;
        notifyDataSetChanged();
    }
}