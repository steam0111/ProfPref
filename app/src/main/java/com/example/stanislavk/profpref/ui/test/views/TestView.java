package com.example.stanislavk.profpref.ui.test.views;

import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.ui.base.views.BaseView;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by LasVegas on 09.07.2017.
 */

public interface TestView extends BaseView {
    void onStartTest(ModelSettings settings,
                     ModelManageButtons buttons,
                     StorageReference btnLike,
                     StorageReference btnDislike,
                     StorageReference btnLeftArrow,
                     StorageReference btnRightArrow,
                     StorageReference btnStopTest,
                     ArrayList<ModelCategories> ListCategories,
                     String key,
                     String currentTest);

    void onShowQuestion(StorageReference imgQuestion);
}
