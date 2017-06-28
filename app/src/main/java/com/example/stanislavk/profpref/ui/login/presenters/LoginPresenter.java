package com.example.stanislavk.profpref.ui.login.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.login.views.LoginView;

import durdinapps.rxfirebase2.RxFirebaseAuth;

/**
 * Created by LasVegas on 27.06.2017.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

  public static final String LOGIN = "@gmail.com";

  public void login(String login, String passwrod){
    RxFirebaseAuth.signInWithEmailAndPassword(mCoreServices.getFireBaseService().getAuth(), login + LOGIN, passwrod)
            .subscribe(user -> {
               getViewState().onNextScreen();
            },throwable -> {
              getViewState().onLoginFailed();
            });
  }
}
