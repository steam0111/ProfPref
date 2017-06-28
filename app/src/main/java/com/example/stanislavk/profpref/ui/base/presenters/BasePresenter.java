package com.example.stanislavk.profpref.ui.base.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.example.stanislavk.profpref.BaseApplication;
import com.example.stanislavk.profpref.di.services.CoreServices;


/**
 * Created by LasVegas on 02.04.2017.
 */

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    public CoreServices mCoreServices = new CoreServices();

    public BasePresenter() {
        super();

        BaseApplication.getAppComponent().inject(mCoreServices);
    }
}
