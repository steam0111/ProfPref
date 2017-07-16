package com.example.stanislavk.profpref.ui.results.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stanislavk.profpref.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class ResultFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_LINK_PICTURE = "link";
    private ImageView mIVpicture;

    @BindViews({ R.id.iv_question1,
                 R.id.iv_question2,
                 R.id.iv_question3,
                 R.id.iv_question4,
                 R.id.iv_question5 })
    List<ImageView> mIVresult;

    private Unbinder unbinder;


    public static ResultFragment newInstance(int page, ArrayList<String> linkPicture) {
        ResultFragment pageFragment = new ResultFragment();
        Bundle arguments = new Bundle();


        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);

        arguments.putStringArrayList(ARGUMENT_LINK_PICTURE, linkPicture);

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
        View view = inflater.inflate(R.layout.fragment_result, null);

        Bundle arguments = new Bundle();

        unbinder = ButterKnife.bind(this, view);

        int current_page = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        ArrayList<String> link = getArguments().getStringArrayList(ARGUMENT_LINK_PICTURE);

        FirebaseStorage storageRef = FirebaseStorage.getInstance();

        int countPicture = 0;
        for (ImageView imageView : mIVresult) {

            setImageFromFB(getContext(),imageView,storageRef.getReference(
                    link.get(countPicture) + ".jpg"));
            countPicture++;
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
