package com.example.stanislavk.profpref.di.services.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by LasVegas on 27.06.2017.
 */

public class FireBaseService {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;

    public FireBaseService(FirebaseAuth auth, DatabaseReference database){
        mAuth = auth;
        mDatabase = database;
    }
    public FirebaseAuth getAuth() {
        return mAuth;
    }


    public FirebaseUser getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser() {
        mCurrentUser = mAuth.getCurrentUser();
    }

    public DatabaseReference getDatabase() {
        return mDatabase;
    }
}
