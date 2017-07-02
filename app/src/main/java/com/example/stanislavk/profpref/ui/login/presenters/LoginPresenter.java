package com.example.stanislavk.profpref.ui.login.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.login.views.LoginView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

/**
 * Created by LasVegas on 27.06.2017.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

  public static final String LOGIN = "@gmail.com";
  public static final String FIREBASE_OBJECT = "students";
  public static final String FIREBASE_STUDENT_COUNTER_LOGIN = "counter_login";

  public void login(String login, String passwrod){
      getViewState().onVisibleProgressBar();
      RxFirebaseAuth.signInWithEmailAndPassword(mCoreServices.getFireBaseService().getAuth(), login + LOGIN, passwrod)
            .subscribe(user -> {
                Query query = mCoreServices.getFireBaseService().getDatabase()
                        .child(FIREBASE_OBJECT)
                        .child(user.getUser().getUid())
                        .orderByKey()
                        .equalTo(FIREBASE_STUDENT_COUNTER_LOGIN);

                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long counter = 0;
                        if(dataSnapshot.getValue() == null){
                            RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_OBJECT)
                                    .child(user.getUser().getUid())
                                    .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                    () -> {
                                        getViewState().onNextScreen();
                                        getViewState().onInvisibleProgressBar();
                                    });
                        } else {
                            HashMap counter_value =  (HashMap) dataSnapshot.getValue();
                            counter = (long)counter_value.get(FIREBASE_STUDENT_COUNTER_LOGIN);
                            counter++;
                            RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_OBJECT)
                                    .child(user.getUser().getUid())
                                    .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                    () -> {
                                        getViewState().onNextScreen();
                                        getViewState().onInvisibleProgressBar();
                                    });
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        getViewState().onInvisibleProgressBar();
                    }
                });
            });
  }
}

