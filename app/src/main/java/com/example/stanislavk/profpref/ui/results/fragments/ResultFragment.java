package com.example.stanislavk.profpref.ui.results.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.results.models.ResultShowingModel;
import com.example.stanislavk.profpref.ui.test.enums.QuestionContentType;
import com.example.stanislavk.profpref.ui.test.fragments.TestQuestionFragment;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class ResultFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_LINK_PICTURE = "link";


    @BindViews({ R.id.iv_question1,
                 R.id.iv_question2,
                 R.id.iv_question3,
                 R.id.iv_question4,
                 R.id.iv_question5 })

    List<GifImageView> mIVresult;

    private Unbinder unbinder;


    public static ResultFragment newInstance(int page, ArrayList<ResultShowingModel> linkPicture) {
        ResultFragment pageFragment = new ResultFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);

        arguments.putParcelableArrayList(ARGUMENT_LINK_PICTURE, linkPicture);

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

        ArrayList<ResultShowingModel> resultShowingModel = getArguments().getParcelableArrayList(ARGUMENT_LINK_PICTURE);

        FirebaseStorage storageRef = FirebaseStorage.getInstance();

        int countPicture = 0;
        for (GifImageView imageView : mIVresult) {

            if (QuestionContentType.GIF.getType().equals(resultShowingModel.get(countPicture).getContentType())) {

                String fileName = resultShowingModel.get(countPicture).getFirestorageLink().replace("/","r");

                String fileFromDevice = getActivity().getFilesDir() + "/" + fileName  + ".gif";

                try {
                    GifDrawable gifFromFile = new GifDrawable(fileFromDevice);
                    imageView.setImageDrawable(gifFromFile);

                } catch (IOException e) {
                    File localFile = new File(getActivity().getFilesDir(), fileName  + ".gif");

                    storageRef.getReference(resultShowingModel.get(countPicture).getFirestorageLink() + ".gif").getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                        GifDrawable gifFromFile = null;
                        try {
                            gifFromFile = new GifDrawable(localFile);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        imageView.setImageDrawable(gifFromFile);

                    }).addOnFailureListener(exception -> {
                        Log.d(TestQuestionFragment.class.getName(), exception.toString());
                    });

                    e.printStackTrace();
                }

            } else {
                setImageFromFB(getContext(),imageView,storageRef.getReference(
                        resultShowingModel.get(countPicture).getFirestorageLink() + ".jpg"));
            }

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
