package com.example.stanislavk.profpref.ui.pretest.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.google.firebase.database.Query;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_MANAGE_BUTTONS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_PRE_TEST;
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

                   Query preTest = mCoreServices.getFireBaseService().getDatabase()
                           .child(FIREBASE_STUDENTS)
                           .child(mCoreServices.getFireBaseService().getCurrentUser().getUid())
                           .child(FIREBASE_TESTS)
                           .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                           .child(FIREBASE_STUDENT_TEST_PRE_TEST);

                   RxFirebaseDatabase.observeSingleValueEvent(preTest, ModelPreTest.class)
                           .subscribe(pretest -> {


                               getViewState().onStartPreTest(mCoreServices.getFireBaseService().getSettingsTest(),
                                       mCoreServices.getFireBaseService().getManageButtons(),
                                       mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/like_dislike")
                                               .child(mCoreServices.getFireBaseService().getManageButtons().style_images_like_dislike)
                                               .child("like.png"),
                                       mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/like_dislike")
                                               .child(mCoreServices.getFireBaseService().getManageButtons().style_images_like_dislike)
                                               .child("dislike.png"),
                                       mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/next_back")
                                               .child(mCoreServices.getFireBaseService().getManageButtons().style_images_swap_arrows)
                                               .child("back.png"),
                                       mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/next_back")
                                               .child(mCoreServices.getFireBaseService().getManageButtons().style_images_swap_arrows)
                                               .child("next.png"),
                                       mCoreServices.getFireBaseService().getStorageRef().child("/manager_buttons/stop_test")
                                               .child(mCoreServices.getFireBaseService().getManageButtons().style_image_stop_test)
                                               .child("stop.png"),
                                       pretest
                               );
                           });

               },trowable -> {

               });
   }

}
