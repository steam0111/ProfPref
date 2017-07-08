package com.example.stanislavk.profpref.ui.pretest.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.google.firebase.database.Query;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_MANAGE_BUTTONS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;

/**
 * Created by LasVegas on 03.07.2017.
 */
@InjectViewState
public class PreTestPresenter extends BasePresenter<PreTestView> {


   public void getAllsettingsTest(){

       Query manageButtons = mCoreServices.getFireBaseService().getDatabase()
               .child(FIREBASE_STUDENTS)
               .child(mCoreServices.getFireBaseService().getCurrentUser().getUid())
               .child(FIREBASE_TESTS)
               .child(mCoreServices.getFireBaseService().getCurrentUserTest())
               .child(FIREBASE_STUDENT_TEST_MANAGE_BUTTONS);

       RxFirebaseDatabase.observeSingleValueEvent(manageButtons, ModelManageButtons.class)
               .subscribe(buttons -> {
                   mCoreServices.getFireBaseService().setManageButtons(buttons);

                   mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/like_dislike")
                           .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                           .child("like.png");

                   getViewState().onStartPreTest(mCoreServices.getFireBaseService().getSettingsTest(),
                                                mCoreServices.getFireBaseService().getManageButtons(),
                           mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/like_dislike")
                                                                             .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                                                                             .child("like.png"),
                           mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/like_dislike")
                                   .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                                   .child("dislike.png")
                           );
               },trowable -> {

               });
   }

}
