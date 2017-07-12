package com.example.stanislavk.profpref.ui.test.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelTest;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.example.stanislavk.profpref.ui.test.views.TestView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_MANAGE_BUTTONS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_PRE_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;

/**
 * Created by LasVegas on 09.07.2017.
 */
@InjectViewState
public class TestPresenter extends BasePresenter<TestView> {

    public void getAllsettingsTest(){

        Query manageButtons = mCoreServices.getFireBaseService().getDatabase()
                .child(FIREBASE_STUDENTS)
                .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                .child(FIREBASE_TESTS)
                .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                .child(FIREBASE_STUDENT_TEST_MANAGE_BUTTONS);

        RxFirebaseDatabase.observeSingleValueEvent(manageButtons, ModelManageButtons.class)
                .subscribe(buttons -> {
                    mCoreServices.getFireBaseService().setManageButtons(buttons);

                    Query test = mCoreServices.getFireBaseService().getDatabase()
                            .child(FIREBASE_STUDENTS)
                            .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                            .child(FIREBASE_TESTS)
                            .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                            .child("categories");

                    RxFirebaseDatabase.observeSingleValueEvent(test)
                            .subscribe(allTest -> {

                                ArrayList<ModelCategories> ListCategories = new ArrayList<ModelCategories>();

                                for (DataSnapshot category: allTest.getChildren()) {
                                    ModelCategories cat = new ModelCategories();
                                    cat.setNameCategory(category.child("name").getValue().toString());


                                    ArrayList<ModelQuestion> questions= new ArrayList<>();

                                    for (DataSnapshot question: category.child("questions").getChildren()){
                                        ModelQuestion quest = new ModelQuestion();
                                        quest.setTitle(question.child("title").getValue().toString());
                                        questions.add(quest);
                                    }

                                    cat.setQuestions(questions);
                                    ListCategories.add(cat);
                                }

                                getViewState().onStartTest(mCoreServices.getFireBaseService().getSettingsTest(),
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
                                        ListCategories,
                                        mCoreServices.getFireBaseService().getCurrentUser().getKey(),
                                        mCoreServices.getFireBaseService().getCurrentUserTest()
                                );
                            });
                },trowable -> {

                });
    }
}
