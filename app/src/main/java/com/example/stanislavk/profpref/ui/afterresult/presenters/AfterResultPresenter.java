package com.example.stanislavk.profpref.ui.afterresult.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelStudent;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelStateTesting;
import com.example.stanislavk.profpref.ui.afterresult.views.AfterResultView;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.utils.SharedPreferenceUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_COUNTER_LOGIN;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_CURRENT_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_INACTIVE;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_RESULT;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TESTS_RESULTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_SETTINGS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_STATE;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.LOGIN;

/**
 * Created by LasVegas on 27.06.2017.
 */
@InjectViewState
public class AfterResultPresenter extends BasePresenter<AfterResultView> {

    private ModelStateTesting mModelStateTesting = new ModelStateTesting();

    public void onShowAfterResult() {
        getViewState().onShowResult(
                mCoreServices.getFireBaseService().getSettingsTest(),
                mCoreServices.getFireBaseService().getManageButtons(),
                mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/stop_test")
                        .child(mCoreServices.getFireBaseService().getManageButtons().style_image_stop_test)
                        .child("stop.png"),
                mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/finish")
                        .child(mCoreServices.getFireBaseService().getManageButtons().style_image_finish)
                        .child("finish.png")
                );
    }

    public void setCurrentState(Context context){

        DatabaseReference query = mCoreServices.getFireBaseService().getDatabase()
                .child(FIREBASE_STUDENTS)
                .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                .child(FIREBASE_STUDENT_TEST_STATE);

        mModelStateTesting.state = FIREBASE_STUDENT_STATE_ON_INACTIVE;
        mModelStateTesting.current_question = "0";
        mModelStateTesting.current_result = (Integer.parseInt(mCoreServices.getFireBaseService().getModelStateTesting().current_result) + 1) + "";

        SharedPreferenceUtils.getInstance(context).clear();

        RxFirebaseDatabase.setValue(query, mModelStateTesting)
                .subscribe(()->{

                    DatabaseReference result = mCoreServices.getFireBaseService().getDatabase()
                            .child(FIREBASE_STUDENTS)
                            .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                            .child(FIREBASE_TESTS)
                            .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                            .child(FIREBASE_STUDENT_TESTS_RESULTS)
                            .child(mCoreServices.getFireBaseService().getModelStateTesting().current_result);
                });
    }
}

