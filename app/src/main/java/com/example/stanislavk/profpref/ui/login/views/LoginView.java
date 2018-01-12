package com.example.stanislavk.profpref.ui.login.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.stanislavk.profpref.ui.base.views.BaseView;

/**
 * Created by LasVegas on 27.06.2017.
 */

public interface LoginView extends BaseView {
    void onNextScreen();
    void onVisibleProgressBar();
    void onInVisibleProgressBar();
    void onTestScreen();
    void onResultScreen();
    void onShowInfoMsg(String msg);
    void onDropInputField(int type);
}
