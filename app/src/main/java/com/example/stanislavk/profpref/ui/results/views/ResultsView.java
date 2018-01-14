package com.example.stanislavk.profpref.ui.results.views;

import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.base.views.BaseView;
import com.example.stanislavk.profpref.ui.results.models.ResultShowingModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by LasVegas on 03.07.2017.
 */

public interface ResultsView extends BaseView{
    void onLoadLinksCategory(ArrayList<ArrayList<ResultShowingModel>> categoryLinks);

    void onStartResult (ModelSettings settings,
                        ModelManageButtons buttons,
                        StorageReference btnLike,
                        StorageReference btnDislike,
                        StorageReference btnLeftArrow,
                        StorageReference btnRightArrow,
                        StorageReference btnStopTest);

    void onNextScreen();
}
