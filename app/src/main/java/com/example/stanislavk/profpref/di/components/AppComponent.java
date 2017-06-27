package com.example.stanislavk.profpref.di.components;

import com.example.stanislavk.profpref.di.modules.FirebaseModule;
import com.example.stanislavk.profpref.di.services.CoreServices;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LasVegas on 01.04.2017.
 */
@Singleton
@Component(modules = {FirebaseModule.class})
public interface AppComponent {

    void inject(CoreServices coreServices);
}
