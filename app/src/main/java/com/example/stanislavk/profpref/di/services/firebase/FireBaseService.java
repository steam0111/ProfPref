package com.example.stanislavk.profpref.di.services.firebase;

import com.example.stanislavk.profpref.di.services.firebase.models.ModelManageButtons;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
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


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseStorage mStorageRef;

    private String mCurrentUserTest;
    private ModelSettings mSettingsTest;
    private ModelManageButtons mModelManageButtons;

    public FireBaseService(FirebaseAuth auth, DatabaseReference database, FirebaseStorage storageRef){
        mAuth = auth;
        mDatabase = database;
        mStorageRef = storageRef;
    }
    public FirebaseAuth getAuth() {
        return mAuth;
    }


    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
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
}
