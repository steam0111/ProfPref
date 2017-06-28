package com.example.stanislavk.profpref.di.services.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by LasVegas on 27.06.2017.
 */

public class FireBaseService {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    public FireBaseService(FirebaseAuth auth){
        mAuth = auth;
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
}
