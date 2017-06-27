package com.example.stanislavk.profpref.di.services.firebase;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by LasVegas on 27.06.2017.
 */

public class FireBaseService {

    private FirebaseAuth mAuth;

    public FireBaseService(FirebaseAuth auth){
        mAuth = auth;
    }
    public FirebaseAuth getAuth() {
        return mAuth;
    }

}
