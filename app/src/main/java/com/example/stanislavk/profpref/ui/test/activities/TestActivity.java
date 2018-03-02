package com.example.stanislavk.profpref.ui.test.activities;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.example.stanislavk.profpref.R;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelCategories;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelQuestion;
import com.example.stanislavk.profpref.ui.base.activities.BaseActivity;
import com.example.stanislavk.profpref.ui.results.activities.ResultsActivity;
import com.example.stanislavk.profpref.ui.test.adapters.TestPagerAdapter;
import com.example.stanislavk.profpref.ui.test.custom.ViewPagerCustomDuration;
import com.example.stanislavk.profpref.ui.test.fragments.DialogExit;
import com.example.stanislavk.profpref.ui.test.models.TestAnswerModel;
import com.example.stanislavk.profpref.ui.test.presenters.TestPresenter;
import com.example.stanislavk.profpref.ui.test.views.TestView;
import com.example.stanislavk.profpref.utils.CameraSourcePreview;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.setImageFromFB;

/**
 * Created by LasVegas on 09.07.2017.
 */

public class TestActivity extends BaseActivity
        implements TestView, DialogExit.onDialogAction {

    private TextToSpeech mTTSobject;

    private static final String TAG = "FaceTracker";
    private static final int RC_HANDLE_GMS = 9001;
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @InjectPresenter TestPresenter mPresenter;
    @BindView(R.id.vp_test) ViewPagerCustomDuration mVPtest;

    @BindView(R.id.btn_like) ImageView mBTNlike;
    @BindView(R.id.btn_dislike) ImageView mBTNdislike;
    @BindView(R.id.btn_left_arrow) ImageView mBTNlefttArrow;
    @BindView(R.id.btn_right_arrow) ImageView mBTNrightArrow;
    @BindView(R.id.btn_stop) ImageView mBTNstopTest;
    @BindView(R.id.tv_title) TextView mTVtitle;

    private TestPagerAdapter mPagerAdapter;
    private Animation mFadeInAnimation, mFadeOutAnimation;

    private int mCurrentAnimation = 0;

    private int mCountEmotion = 0;
    private float mAverageHappines = 0;
    private float mCurrentHappines = 0;

    //Создание камеры
    private CameraSource mCameraSource = null;

    //Control camera
    private CameraSourcePreview mPreview;

    private DialogExit mDialogExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        mTTSobject = new TextToSpeech(getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR) {
                mTTSobject.setLanguage(new Locale("de_NL"));
            }
        });

        mPresenter.getAllsettingsTest();
        mVPtest.setScrollDurationFactor(12);

        mPagerAdapter = new TestPagerAdapter(getSupportFragmentManager());
        mVPtest.setAdapter(mPagerAdapter);
        mVPtest.setPageTransformer(true, new ForegroundToBackgroundTransformer());

        mVPtest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTVtitle.setText(mPresenter.getAnswers().get(position).getTitle());
                if (mTTSobject != null) {
                    mTTSobject.speak(mPresenter.getAnswers().get(position).getTitle(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ViewGroup) findViewById(R.id.ll_middle)).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);

        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }
    }

    @OnClick(R.id.btn_like)
    public void like() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 1, mAverageHappines, mCurrentHappines);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNlike.startAnimation(mFadeOutAnimation);

        mBTNlike.setClickable(false);
        mBTNdislike.setClickable(false);

        mCurrentAnimation=0;
    }

    @OnClick(R.id.btn_dislike)
    public void dislike() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), -1, mAverageHappines, mCurrentHappines);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);

        mBTNdislike.startAnimation(mFadeOutAnimation);

        mBTNdislike.setClickable(false);
        mBTNlike.setClickable(false);

        mCurrentAnimation++;
    }

    @OnClick(R.id.btn_left_arrow)
    public void arrowLeft() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 0, mAverageHappines, mCurrentHappines);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() - 1);
    }

    @OnClick(R.id.btn_right_arrow)
    public void arrowRight() {
        mPresenter.setAnswer(mVPtest.getCurrentItem(), 0, mAverageHappines, mCurrentHappines);

        mVPtest.setCurrentItem(mVPtest.getCurrentItem() + 1);
    }

    @OnClick(R.id.btn_stop)
    public void stop() {
        FragmentManager fm = getSupportFragmentManager();
        mDialogExit = new DialogExit();
        mDialogExit.show(fm, "fragment_dialog_exit");
    }


    @Override
    public void onStartTest(ModelSettings settings,
                            ModelManageButtons buttons,
                            StorageReference btnLike,
                            StorageReference btnDislike,
                            StorageReference btnLeftArrow,
                            StorageReference btnRightArrow,
                            StorageReference btnStopTest,
                            ArrayList<ModelCategories> ListCategories,
                            String key,
                            String currentTest,
                            int currentQuestion) {

        setImageFromFB(this, mBTNlike, btnLike);
        setImageFromFB(this, mBTNdislike, btnDislike);
        setImageFromFB(this, mBTNstopTest, btnStopTest);

        if (settings.text.equals("true")) {
            mTVtitle.setVisibility(View.VISIBLE);
        } else {
            mTVtitle.setVisibility(View.GONE);
        }
        if (settings.swap.equals("true")) {
            setImageFromFB(this, mBTNlefttArrow, btnLeftArrow);
            setImageFromFB(this, mBTNrightArrow, btnRightArrow);
        } else {
            mBTNlefttArrow.setVisibility(View.INVISIBLE);
            mBTNrightArrow.setVisibility(View.INVISIBLE);
        }


        ArrayList <TestAnswerModel> answers = new ArrayList<>();

        int catCounter = 0;
        for (ModelCategories categories : ListCategories){
            int quesCounter = 0;
            for (ModelQuestion question : categories.getQuestions()) {
                TestAnswerModel answerModel = new TestAnswerModel();

                String link = FIREBASE_TESTS + "/"
                        + currentTest+ "/"
                        + catCounter+ "/"
                        + quesCounter;

                answerModel.setCategory(categories.getNameCategory());
                answerModel.setFirebasePictureLink(link);
                answerModel.setTitle(question.getTitle());
                answerModel.setContentType(question.getContentType());

                answers.add(answerModel);

                quesCounter++;
            }
            catCounter++;
        }

        mPagerAdapter.setQuestionsList(answers);
        mPresenter.setAnswers(answers);

        if (currentQuestion > 0) {
            currentQuestion++;
        }

        mVPtest.setCurrentItem(currentQuestion);
        mTVtitle.setText(mPresenter.getAnswers().get(currentQuestion).getTitle());
    }

    @Override
    public void onShowQuestion(StorageReference imgQuestion) {

    }

    @Override
    public void onNextScreen() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }

    Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mCurrentAnimation > 0) {
                mBTNdislike.startAnimation(mFadeInAnimation);
            } else {
                mBTNlike.startAnimation(mFadeInAnimation);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}

        @Override
        public void onAnimationStart(Animation animation) {}
    };

    Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mBTNlike.setClickable(true);
            mBTNdislike.setClickable(true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        /*Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();*/
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        if(mTTSobject !=null){
            mTTSobject.stop();
            mTTSobject.shutdown();
        }
        super.onPause();
        mPreview.stop();

    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource
                        //, mGraphicOverlay
                );
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void onAction(int command) {
        if (DialogExit.DIALOG_COMMAD_CONTINUE == command) {
            mDialogExit.dismiss();
        } else {
            finish();
        }
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            Log.d("Face","Лицо опознано");
            return new GraphicFaceTracker();
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {



        GraphicFaceTracker() {


        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {

        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {

            mCountEmotion++;
            mAverageHappines += face.getIsSmilingProbability();
            mCurrentHappines = face.getIsSmilingProbability();

            Log.d("Face","Средний уровень счастья" + (mAverageHappines/mCountEmotion));
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {

        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {

        }
    }
}
