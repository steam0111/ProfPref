package com.example.stanislavk.profpref.ui.test.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stanislavk.profpref.R;

/**
 * Created by LasVegas on 14.01.2018.
 */

public class DialogExit extends DialogFragment {

    public static int DIALOG_COMMAD_EXIT = 0;
    public static int DIALOG_COMMAD_CONTINUE = 1;

    public DialogExit() {}

    public interface onDialogAction {
        void onAction(int command);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_exit, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    onDialogAction mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (onDialogAction) activity;
        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.iv_continue).setOnClickListener(v -> mListener.onAction(DIALOG_COMMAD_CONTINUE));
        view.findViewById(R.id.iv_exit).setOnClickListener(v -> mListener.onAction(DIALOG_COMMAD_EXIT));
    }

}
