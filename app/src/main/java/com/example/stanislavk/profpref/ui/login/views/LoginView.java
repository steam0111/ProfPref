package com.example.stanislavk.profpref.ui.login.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.stanislavk.profpref.ui.base.views.BaseView;

/**
 * Created by LasVegas on 27.06.2017.
 */

public interface LoginView extends BaseView {
    @StateStrategyType(SkipStrategy.class)
    void onNextScreen();
    @StateStrategyType(SkipStrategy.class)
    void onLoginFailed();
    @StateStrategyType(SkipStrategy.class)
    void onVisibleProgressBar();
    @StateStrategyType(SkipStrategy.class)
    void onInVisibleProgressBar();
}
