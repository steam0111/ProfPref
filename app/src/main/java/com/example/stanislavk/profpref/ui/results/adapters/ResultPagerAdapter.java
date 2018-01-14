package com.example.stanislavk.profpref.ui.results.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.stanislavk.profpref.ui.results.fragments.ResultFragment;
import com.example.stanislavk.profpref.ui.results.models.ResultShowingModel;

import java.util.ArrayList;

/**
 * Created by LasVegas on 10.07.2017.
 */

public class ResultPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ArrayList<ResultShowingModel>> mCategoryContentList = new ArrayList<>();


    public ResultPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ResultFragment.newInstance(position, mCategoryContentList.get(position));
    }

    @Override
    public int getCount() {
        return mCategoryContentList.size();
    }

    public void setCategoryContentList(ArrayList<ArrayList<ResultShowingModel>> listLinks) {
        this.mCategoryContentList = listLinks;
        notifyDataSetChanged();
    }
}