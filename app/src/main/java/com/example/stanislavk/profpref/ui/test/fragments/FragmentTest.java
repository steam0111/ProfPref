package com.example.stanislavk.profpref.ui.test.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stanislavk.profpref.R;

import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class FragmentTest extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static FragmentTest newInstance(int page) {
        FragmentTest pageFragment = new FragmentTest();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);

        return view;
    }
}
