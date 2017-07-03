package com.example.stanislavk.profpref.di.services.firebase;

import com.example.stanislavk.profpref.ui.login.ModelSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by LasVegas on 27.06.2017.
 */

public class FireBaseService {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String mCurrentUserTest;
    private ModelSettings mSettingsTest;
    public FireBaseService(FirebaseAuth auth, DatabaseReference database){
        mAuth = auth;
        mDatabase = database;
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
}
