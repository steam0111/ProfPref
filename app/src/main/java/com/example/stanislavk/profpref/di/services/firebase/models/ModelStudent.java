package com.example.stanislavk.profpref.di.services.firebase.models;

/**
 * Created by LasVegas on 11.07.2017.
 */

public class ModelStudent {

    private String key;
    private String photoLinkFireStorage;
    private String login;

    public ModelStudent () {

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhotoLinkFireStorage() {
        return photoLinkFireStorage;
    }

    public void setPhotoLinkFireStorage(String photoLinkFireStorage) {
        this.photoLinkFireStorage = photoLinkFireStorage;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String password) {
        this.login = password;
    }
}
