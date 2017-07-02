package com.example.stanislavk.profpref.ui.pretest.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.stanislavk.profpref.R;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreTestActivity extends AppCompatActivity {

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
        ButterKnife.bind(this);

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
}
