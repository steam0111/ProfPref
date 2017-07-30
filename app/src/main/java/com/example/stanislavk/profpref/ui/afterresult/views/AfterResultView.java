package com.example.stanislavk.profpref.ui.afterresult.views;

import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.base.views.BaseView;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LasVegas on 27.06.2017.
 */

public interface AfterResultView extends BaseView {
  void onShowResult(ModelSettings settings,
                    ModelManageButtons buttons,
                    StorageReference btnStopTest,
                    StorageReference ivResult);
}
