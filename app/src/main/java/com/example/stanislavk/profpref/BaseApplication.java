package com.example.stanislavk.profpref;

import android.app.Application;

import com.example.stanislavk.profpref.di.components.AppComponent;
import com.example.stanislavk.profpref.di.components.DaggerAppComponent;
import com.example.stanislavk.profpref.di.modules.ContextModule;
import com.example.stanislavk.profpref.di.modules.FirebaseModule;

import butterknife.ButterKnife;

public class BaseApplication extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger
        mAppComponent = DaggerAppComponent.builder()
                .firebaseModule(new FirebaseModule())
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
