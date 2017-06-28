package com.example.stanislavk.profpref.di.services;

import com.example.stanislavk.profpref.di.services.firebase.FireBaseService;

import javax.inject.Inject;

/**
 * Created by LasVegas on 01.04.2017.
 */

public class CoreServices {

    @Inject FireBaseService mFireBaseService;
    public FireBaseService getFireBaseService() {
        return mFireBaseService;
    }
}
