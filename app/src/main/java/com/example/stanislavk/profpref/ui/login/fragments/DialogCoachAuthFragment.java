package com.example.stanislavk.profpref.ui.login.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.stanislavk.profpref.R;


public class DialogCoachAuthFragment extends DialogFragment {

    private DialogCoachAuthFragmentListener mListener;

    public interface DialogCoachAuthFragmentListener {
        public void coachLogin(String login, String password);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof DialogCoachAuthFragmentListener) {
            mListener = (DialogCoachAuthFragmentListener)activity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login_dialog_coach_auth, container, false);

        EditText etLogin = (EditText) rootView.findViewById(R.id.et_login);
        EditText etPassword = (EditText) rootView.findViewById(R.id.et_password);

        rootView.findViewById(R.id.btn_login).setOnClickListener(v -> {
            mListener.coachLogin(etLogin.getText().toString(),
                                 etPassword.getText().toString());
        });

        return rootView;
    }
}
