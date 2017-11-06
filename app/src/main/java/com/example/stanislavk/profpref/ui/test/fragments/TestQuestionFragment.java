package com.example.stanislavk.profpref.ui.test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stanislavk.profpref.R;
import com.google.firebase.storage.FirebaseStorage;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class TestQuestionFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_LINK_PICTURE = "link";
    private ImageView mIVpicture;

    public static TestQuestionFragment newInstance(int page, String linkPicture) {
        TestQuestionFragment pageFragment = new TestQuestionFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_LINK_PICTURE, linkPicture);

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

        mIVpicture = (ImageView) view.findViewById(R.id.iv_question);
        Bundle arguments = new Bundle();

        int current_page = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        String link = getArguments().getString(ARGUMENT_LINK_PICTURE);

        FirebaseStorage storageRef = FirebaseStorage.getInstance();
        setImageFromFB(getContext(),mIVpicture,storageRef.getReference(link + ".jpg"));
        return view;
    }
}
