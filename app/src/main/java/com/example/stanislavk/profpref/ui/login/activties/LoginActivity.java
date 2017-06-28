package com.example.stanislavk.profpref.ui.login.activties;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.login.presenters.LoginPresenter;
import com.example.stanislavk.profpref.ui.login.views.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    @InjectPresenter LoginPresenter mPresenter;

    @BindView(R.id.et_login) EditText mETlogin;
    @BindView(R.id.et_password) EditText mETpassword;
    @BindView(R.id.btn_login) Button mBTNlogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBTNlogin.setOnClickListener(this);
    }

    @Override
    public void onNextScreen() {
        Toast.makeText(getBaseContext(),"Авторизация успешна",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(),"Авторизация провалена",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            mPresenter.login(mETlogin.getText().toString(), mETpassword.getText().toString());
        }
    }
}
