package com.example.stanislavk.profpref.ui.results.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelStateTesting;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.example.stanislavk.profpref.ui.results.models.ResultAnswerModel;
import com.example.stanislavk.profpref.ui.results.models.ResultShowingModel;
import com.example.stanislavk.profpref.ui.results.views.ResultsView;
import com.example.stanislavk.profpref.ui.test.enums.QuestionContentType;
import com.example.stanislavk.profpref.ui.test.models.TestAnswerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_RESULT;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TESTS_RESULTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TESTS_RESULTS_CATEGORY;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_MANAGE_BUTTONS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_PRE_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_STATE;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_STATE_STATE;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;

/**
 * Created by LasVegas on 03.07.2017.
 */
@InjectViewState
public class ResultsPresenter extends BasePresenter<ResultsView> {

    private ArrayList<ArrayList<ResultShowingModel>> mCategoryLinks = new ArrayList<>();
    private ArrayList<String> mListCategoryLinks = new ArrayList<>();

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

                        mListCategoryLinks.add(category.child("name").getValue().toString());

                        int quesCounter = 0;

                        ArrayList<ResultShowingModel> linkCategory = new ArrayList<>();

                        for (DataSnapshot question : category.child("questions").getChildren()) {

                            String link = FIREBASE_TESTS + "/"
                                    + mCoreServices.getFireBaseService().getCurrentUserTest()+ "/"
                                    + catCounter+ "/"
                                    + quesCounter;


                            String contentType = "";
                            if (question.child("type").getValue() != null &&
                                    !question.child("type").getValue().toString().equals("") &&
                                    question.child("type").getValue().toString().equals(QuestionContentType.GIF.getType())) {
                                contentType = QuestionContentType.GIF.getType();
                            } else {
                                contentType = QuestionContentType.JPG.getType();
                            }

                            ResultShowingModel resultShowingModel = new ResultShowingModel(link, contentType);

                            linkCategory.add(resultShowingModel);
                            quesCounter++;
                        }
                        mCategoryLinks.add(linkCategory);
                        catCounter++;
                    }

                    getViewState().onLoadLinksCategory(mCategoryLinks);
                });
    }

    public void setCurrentState(){

        DatabaseReference query = mCoreServices.getFireBaseService().getDatabase()
                .child(FIREBASE_STUDENTS)
                .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                .child(FIREBASE_STUDENT_TEST_STATE)
                .child(FIREBASE_STUDENT_TEST_STATE_STATE);

        RxFirebaseDatabase.setValue(query, FIREBASE_STUDENT_STATE_ON_RESULT)
                .subscribe(() -> {

                });
    }
    public void getSettingsTest(){

        if (mCoreServices.getFireBaseService().getManageButtons() == null) {

            Query manageButtons = mCoreServices.getFireBaseService().getDatabase()
                    .child(FIREBASE_STUDENTS)
                    .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                    .child(FIREBASE_TESTS)
                    .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                    .child(FIREBASE_STUDENT_TEST_MANAGE_BUTTONS);

            RxFirebaseDatabase.observeSingleValueEvent(manageButtons, ModelManageButtons.class)
                    .subscribe(buttons -> {
                        mCoreServices.getFireBaseService().setManageButtons(buttons);

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
                    });
        } else {
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

    public void setAnswer(int pos, int answer){

        if (mListCategoryLinks.size() != 0) {
            ResultAnswerModel answerModel = new ResultAnswerModel();
            answerModel.setTitleCategory(mListCategoryLinks.get(pos));
            answerModel.setStateAccept(answer);

            DatabaseReference result = mCoreServices.getFireBaseService().getDatabase()
                    .child(FIREBASE_STUDENTS)
                    .child(mCoreServices.getFireBaseService().getCurrentUser().getKey())
                    .child(FIREBASE_TESTS)
                    .child(mCoreServices.getFireBaseService().getCurrentUserTest())
                    .child(FIREBASE_STUDENT_TESTS_RESULTS)
                    .child(mCoreServices.getFireBaseService().getModelStateTesting().current_result)
                    .child(FIREBASE_STUDENT_TESTS_RESULTS_CATEGORY)
                    .child(pos + "");

            RxFirebaseDatabase.setValue(result, answerModel)
                    .subscribe(() -> {
                        if (pos == (mCategoryLinks.size() - 1)) {
                            getViewState().onNextScreen();
                        }
                    });
        }
    }
}
