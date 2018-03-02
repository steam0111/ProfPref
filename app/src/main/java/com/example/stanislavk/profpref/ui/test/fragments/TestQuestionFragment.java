package com.example.stanislavk.profpref.ui.test.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.test.enums.QuestionContentType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class TestQuestionFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_LINK_PICTURE = "link";
    static final String ARGUMENT_CONTENT_TYPE = "content_type";


    private ImageView mIVpicture;

    public static TestQuestionFragment newInstance(int page, String linkPicture, String contentType) {
        TestQuestionFragment pageFragment = new TestQuestionFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_LINK_PICTURE, linkPicture);
        arguments.putString(ARGUMENT_CONTENT_TYPE, contentType);

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

        mIVpicture = (GifImageView) view.findViewById(R.id.iv_question);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_load);
        progressBar.setProgress(0);

        Bundle arguments = new Bundle();

        int currentPage = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        String link = getArguments().getString(ARGUMENT_LINK_PICTURE);

        FirebaseStorage storageRef = FirebaseStorage.getInstance();

        String contentType = getArguments().getString(ARGUMENT_CONTENT_TYPE);

        if (QuestionContentType.GIF.getType().equals(contentType)) {

            String fileName = link.replace("/","r");

            String fileFromDevice = getActivity().getFilesDir() + "/" + fileName  + ".gif";

            try {
                GifDrawable gifFromFile = new GifDrawable(fileFromDevice);
                mIVpicture.setImageDrawable(gifFromFile);
                progressBar.setVisibility(View.GONE);

            } catch (IOException e) {
                File localFile = new File(getActivity().getFilesDir(), fileName  + ".gif");

                storageRef.getReference(link + ".gif").getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                    GifDrawable gifFromFile = null;
                    try {
                        gifFromFile = new GifDrawable(localFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    mIVpicture.setImageDrawable(gifFromFile);

                })
                        .addOnProgressListener(taskSnapshot -> {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressBar.setProgress((int) progress);

                            if ((int) progress == 100 ) {
                                progressBar.setVisibility(View.GONE);
                            }

                        }).addOnFailureListener(exception -> {
                    Log.d(TestQuestionFragment.class.getName(), exception.toString());
                });

                e.printStackTrace();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            setImageFromFB(getContext(), mIVpicture, storageRef.getReference(link + ".jpg"));
        }

        return view;
    }
}
