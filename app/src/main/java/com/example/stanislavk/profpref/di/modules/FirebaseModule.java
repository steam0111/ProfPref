package com.example.stanislavk.profpref.di.modules;

import com.example.stanislavk.profpref.di.services.firebase.FireBaseService;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LasVegas on 27.06.2017.
 */
@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FireBaseService provideDatabaseService() {
        return new FireBaseService(FirebaseAuth.getInstance());
    }
}
