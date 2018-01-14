package com.example.stanislavk.profpref.di.services.firebase;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelStudent;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelStateTesting;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LasVegas on 27.06.2017.
 */

public class FireBaseService {

    public static final String LOGIN = "@gmail.com";
    public static final String FIREBASE_STUDENTS = "students";
    public static final String FIREBASE_TESTS = "tests";
    public static final String FIREBASE_STUDENT_COUNTER_LOGIN = "counter_login";
    public static final String FIREBASE_STUDENT_CURRENT_TEST = "current_test";
    public static final String FIREBASE_STUDENT_TEST_SETTINGS = "settings";
    public static final String FIREBASE_STUDENT_TEST_MANAGE_BUTTONS = "manage_buttons";
    public static final String FIREBASE_STUDENT_TEST_PRE_TEST = "pre_test";
    public static final String FIREBASE_STUDENT_TEST_STATE = "student_state";
    public static final String FIREBASE_STUDENT_TEST_STATE_STATE = "state";

    public static final String FIREBASE_STUDENT_TESTS_RESULTS = "results";
    public static final String FIREBASE_STUDENT_TESTS_RESULTS_CATEGORY = "category";

    public static final String FIREBASE_STUDENT_STATE_ON_TEST = "test";
    public static final String FIREBASE_STUDENT_STATE_ON_RESULT = "result";
    public static final String FIREBASE_STUDENT_STATE_ON_INACTIVE = "inactive";


    public static final String MAIN_LOGIN = "android@gmail.com";
    public static final String MAIN_PASSWORD = "eqwdsfSAsadadsAsd1";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseStorage mStorageRef;

    private String mCurrentUserTest;
    private ModelSettings mSettingsTest;
    private ModelManageButtons mModelManageButtons;
    private ModelStudent mModelStudent;
    private ModelStateTesting mModelStateTesting;

    public FireBaseService(FirebaseAuth auth, DatabaseReference database, FirebaseStorage storageRef){
        mAuth = auth;
        mDatabase = database;
        mStorageRef = storageRef;
    }
    public static void setImageFromFB(Context context, ImageView imageView, StorageReference storageReference){
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }
    
    public FirebaseAuth getAuth() {
        return mAuth;
    }


    public ModelStudent getCurrentUser() {
        return mModelStudent;
    }


    public DatabaseReference getDatabase() {
        return mDatabase;
    }

    public String getCurrentUserTest() {
        return mCurrentUserTest;
    }

    public void setCurrentUserTest(String current_test) {
        this.mCurrentUserTest = current_test;
    }

    public ModelSettings getSettingsTest() {
        return mSettingsTest;
    }

    public void setSettingsTest(ModelSettings mSettingsTest) {
        this.mSettingsTest = mSettingsTest;
    }

    public ModelManageButtons getManageButtons() {
        return mModelManageButtons;
    }

    public void setManageButtons(ModelManageButtons mModelManageButtons) {
        this.mModelManageButtons = mModelManageButtons;
    }
    public StorageReference getStorageRef() {
        return mStorageRef.getReference();
    }

    public void setModelStudent(ModelStudent mModelStudent) {
        this.mModelStudent = mModelStudent;
    }

    public ModelStateTesting getModelStateTesting() {
        return mModelStateTesting;
    }

    public void setModelStateTesting(ModelStateTesting mModelStateTesting) {
        this.mModelStateTesting = mModelStateTesting;
    }
}
