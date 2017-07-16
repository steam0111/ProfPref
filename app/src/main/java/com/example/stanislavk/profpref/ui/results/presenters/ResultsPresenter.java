package com.example.stanislavk.profpref.ui.results.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.example.stanislavk.profpref.ui.results.views.ResultsView;
import com.example.stanislavk.profpref.ui.test.models.TestAnswerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_MANAGE_BUTTONS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_PRE_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;

/**
 * Created by LasVegas on 03.07.2017.
 */
@InjectViewState
public class ResultsPresenter extends BasePresenter<ResultsView> {

    private ArrayList<ArrayList<String>> mCategoryLinks = new ArrayList<>();

    public void onLoadLinksCategory() {

        Query test = mCoreServices.getFireBaseService().getDatabase()
                .child(FIREBASE_STUDENTS)
                .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                .child(FIREBASE_TESTS)
                .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                .child("categories");

        RxFirebaseDatabase.observeSingleValueEvent(test)
                .subscribe(allTest -> {

                    int catCounter = 0;
                    for (DataSnapshot category : allTest.getChildren()){
                        int quesCounter = 0;

                        ArrayList<String> linkCategory = new ArrayList<String>();

                        for (DataSnapshot question : category.child("questions").getChildren()) {
                            TestAnswerModel answerModel = new TestAnswerModel();

                            String link = FIREBASE_TESTS + "/"
                                    + mCoreServices.getFireBaseService().getCurrentUserTest()+ "/"
                                    + catCounter+ "/"
                                    + quesCounter;

                            linkCategory.add(link);
                            quesCounter++;
                        }
                        mCategoryLinks.add(linkCategory);
                        catCounter++;
                    }

                    getViewState().onLoadLinksCategory(mCategoryLinks);
                });
    }

    public void getSettingsTest(){

        getViewState().onStartResult(
                mCoreServices.getFireBaseService().getSettingsTest(),
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
                        .child("stop.png"));
    }
}
