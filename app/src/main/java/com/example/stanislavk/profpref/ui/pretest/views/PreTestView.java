package com.example.stanislavk.profpref.ui.pretest.views;

import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.base.views.BaseView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LasVegas on 03.07.2017.
 */

public interface PreTestView extends BaseView{
    void onStartPreTest(ModelSettings settings,
                        ModelManageButtons buttons,
                        StorageReference btnLike,
                        StorageReference btnDislike,
                        StorageReference btnLeftArrow,
                        StorageReference btnRightArrow,
                        StorageReference btnStopTest,
                        ModelPreTest preTest);
}
