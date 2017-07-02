package com.example.stanislavk.profpref.ui.login.views;

import com.example.stanislavk.profpref.ui.base.views.BaseView;

/**
 * Created by LasVegas on 27.06.2017.
 */

public interface LoginView extends BaseView {
    void onNextScreen();
    void onLoginFailed();

    void onVisibleProgressBar();
    void onInvisibleProgressBar();
}
