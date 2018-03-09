package com.example.stanislavk.profpref.ui.pretest.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelPreTest;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.pretest.presenters.PreTestPresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.example.stanislavk.profpref.ui.test.activities.TestActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

public class PreTestActivity extends BaseActivity implements PreTestView {

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @BindView(R.id.btn_left_arrow) ImageView mBTNlefttArrow;
    @BindView(R.id.btn_right_arrow) ImageView mBTNrightArrow;
    @BindView(R.id.btn_stop) ImageView mBTNstopTest;
    @BindView(R.id.tv_title) TextView mTVtitle;

    @InjectPresenter PreTestPresenter mPresenter;

    private boolean mIsText = false;
    private Activity mActivity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
        ButterKnife.bind(this);

        mPresenter.getAllsettingsTest();
    }

    @Override
    public void onStartPreTest(ModelSettings settings,
                               ModelManageButtons buttons,
                               StorageReference btnLike,
                               StorageReference btnDislike,
                               StorageReference btnLeftArrow,
                               StorageReference btnRightArrow,
                               StorageReference btnStopTest,
                               ModelPreTest preTest) {

        setImageFromFB(this, mBTNlike, btnLike);
        setImageFromFB(this, mBTNdislike, btnDislike);
        setImageFromFB(this, mBTNstopTest, btnStopTest);

        if (settings.text.equals("true")) {
            mTVtitle.setVisibility(View.VISIBLE);
        } else {
            mTVtitle.setVisibility(View.GONE);

            preTest.title_text_btn_like = "";
            preTest.description_text_btn_like = "";
            preTest.title_text_btn_dislike = "";
            preTest.description_text_btn_dislike = "";
        }
        if (settings.swap.equals("true")) {
            setImageFromFB(this, mBTNlefttArrow, btnLeftArrow);
            setImageFromFB(this, mBTNrightArrow, btnRightArrow);
        } else {
            mBTNlefttArrow.setVisibility(View.INVISIBLE);
            mBTNrightArrow.setVisibility(View.INVISIBLE);
        }

        ViewTarget targetBtnLike = new ViewTarget(mBTNlike);
        ViewTarget targetBtndisLike = new ViewTarget(mBTNdislike);
        ViewTarget targetBTNStop = new ViewTarget(mBTNstopTest);
        ViewTarget targetBTNback = new ViewTarget(mBTNlefttArrow);
        ViewTarget targetBTNnext = new ViewTarget(mBTNrightArrow);

        Button replaceButton = new Button(this);
        replaceButton.setVisibility(View.GONE);

        ShowcaseView.Builder  svBtnLike = new ShowcaseView.Builder(mActivity)
                .setTarget(targetBtnLike)
                .setContentTitle(preTest.title_text_btn_like)
                .setContentText(preTest.description_text_btn_like)
                .withHoloShowcase()
                .blockAllTouches()
                .hideOnTouchOutside()
                .replaceEndButton(replaceButton)
                .setStyle(R.style.CustomShowcaseTheme);

        replaceButton = new Button(this);
        replaceButton.setVisibility(View.GONE);

        ShowcaseView.Builder  svDisLike = new ShowcaseView.Builder(mActivity)
                .setTarget(targetBtndisLike)
                .setContentTitle(preTest.title_text_btn_dislike)
                .setContentText(preTest.description_text_btn_dislike)
                .withHoloShowcase()
                .blockAllTouches()
                .hideOnTouchOutside()
                .replaceEndButton(replaceButton)
                .setStyle(R.style.CustomShowcaseTheme);

        replaceButton = new Button(this);
        replaceButton.setVisibility(View.GONE);

        ShowcaseView.Builder  svArrowRight = new ShowcaseView.Builder(mActivity)
                .setTarget(targetBTNnext)
                .setContentTitle(preTest.title_text_btn_next)
                .setContentText(preTest.description_text_btn_next)
                .withHoloShowcase()
                .blockAllTouches()
                .hideOnTouchOutside()
                .replaceEndButton(replaceButton)
                .setStyle(R.style.CustomShowcaseTheme);

        replaceButton = new Button(this);
        replaceButton.setVisibility(View.GONE);

        ShowcaseView.Builder  svArrowLeft = new ShowcaseView.Builder(mActivity)
                .setTarget(targetBTNback)
                .setContentTitle(preTest.title_text_btn_back)
                .setContentText(preTest.description_text_btn_back)
                .withHoloShowcase()
                .blockAllTouches()
                .hideOnTouchOutside()
                .replaceEndButton(replaceButton)
                .setStyle(R.style.CustomShowcaseTheme);

        replaceButton = new Button(this);
        replaceButton.setVisibility(View.GONE);

        ShowcaseView.Builder  svBtnStop = new ShowcaseView.Builder(mActivity)
                .setTarget(targetBTNStop)
                .setContentTitle(preTest.title_text_btn_stop)
                .setContentText(preTest.description_text_btn_stop)
                .withHoloShowcase()
                .blockAllTouches()
                .hideOnTouchOutside()
                .replaceEndButton(replaceButton)
                .setStyle(R.style.CustomShowcaseTheme);

        ArrayList<ShowcaseView.Builder> svInterface = new ArrayList<>();

        svInterface.add(svBtnLike);
        svInterface.add(svDisLike);
        svInterface.add(svArrowRight);
        svInterface.add(svArrowLeft);
        svInterface.add(svBtnStop);

        final ShowcaseView[] showcaseBufferView = {null};

        Observable
                .interval(0,3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(second -> {

                    if (Integer.parseInt(second.toString()) == svInterface.size()) {
                        Intent intent = new Intent(getBaseContext(), TestActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (Integer.parseInt(second.toString()) < svInterface.size()) {

                        if (showcaseBufferView[0] != null) {
                            showcaseBufferView[0].hide();
                        }

                        showcaseBufferView[0] = svInterface.get(Integer.parseInt(second.toString())).build();
                    }
                });
    }
}
