package com.example.stanislavk.profpref.ui.afterresult.activties;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.afterresult.presenters.AfterResultPresenter;
import com.example.stanislavk.profpref.ui.afterresult.views.AfterResultView;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.pretest.activities.PreTestActivity;
import com.example.stanislavk.profpref.ui.results.activities.ResultsActivity;
import com.example.stanislavk.profpref.ui.test.activities.TestActivity;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

public class AfterResultActivity extends BaseActivity implements AfterResultView{

    @InjectPresenter
    AfterResultPresenter mPresenter;

    @BindView(R.id.iv_after_result) ImageView mIVafterResult;
    @BindView(R.id.btn_stop) ImageView mIVstopTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_result);
        ButterKnife.bind(this);

        mPresenter.setCurrentState(this);
        mPresenter.onShowAfterResult();
    }

    @OnClick(R.id.btn_stop)
    public void stop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AfterResultActivity.this);

        builder.setPositiveButton(R.string.activity_test_dialog_continue, (dialog, id) -> {

        });

        builder.setNegativeButton(R.string.activity_test_dialog_exit, (dialog, id) -> {
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onShowResult(ModelSettings settings,
                             ModelManageButtons buttons,
                             StorageReference btnStopTest,
                             StorageReference ivResult) {

        setImageFromFB(this, mIVafterResult, ivResult);
        setImageFromFB(this, mIVstopTest, btnStopTest);
    }
}
