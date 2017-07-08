package com.example.stanislavk.profpref.ui.pretest.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.pretest.presenters.PreTestPresenter;
import com.example.stanislavk.profpref.ui.pretest.views.PreTestView;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreTestActivity extends BaseActivity implements PreTestView {

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @BindView(R.id.tv_title) TextView mTVtitle;

    @InjectPresenter PreTestPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
        ButterKnife.bind(this);

        mPresenter.getAllsettingsTest();

        ViewTarget targetBtnLike = new ViewTarget(mBTNlike);
        ViewTarget targetBtndisLike = new ViewTarget(mBTNdislike);

        Activity activity = this;
        final ShowcaseView showCase = new ShowcaseView.Builder(this)
                                            .setTarget(targetBtnLike)
                                            .setContentTitle("Button like")
                                            .setContentText("If you like picture in middle pick here")
                                            .withHoloShowcase()
                .setStyle(R.style.CustomShowcaseTheme)
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                        new ShowcaseView.Builder(activity)
                                .setTarget(targetBtndisLike)
                                .setContentTitle("Button dislike")
                                .setContentText("If you dislike picture in middle pick here")
                                .withHoloShowcase()
                                .setStyle(R.style.CustomShowcaseTheme)
                                .build();
                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                    }
                })
                .build();


        /*new ShowcaseView.Builder(this)
                .setTarget(targetBtndisLike)
                .setContentTitle("ShowcaseView")
                .setContentText("This is highlighting the Home button")
                .hideOnTouchOutside()
                .build();*/
    }

    @Override
    public void onStartPreTest(ModelSettings settings,
                               ModelManageButtons buttons,
                               StorageReference btnLike,
                               StorageReference btnDislike) {
        if (settings.text) {
            mTVtitle.setVisibility(View.VISIBLE);
        } else {
            mTVtitle.setVisibility(View.GONE);
        }
        setImageFromFB(this, mBTNlike, btnLike);
        setImageFromFB(this, mBTNdislike, btnDislike);

    }

    public void setImageFromFB(Context context, ImageView imageView, StorageReference storageReference){
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }
}
