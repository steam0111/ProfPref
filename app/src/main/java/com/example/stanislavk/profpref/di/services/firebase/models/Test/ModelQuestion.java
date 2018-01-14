package com.example.stanislavk.profpref.di.services.firebase.models.Test;

/**
 * Created by LasVegas on 11.07.2017.
 */

public class ModelQuestion {

    private String title;
    private String type;

    public ModelQuestion(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return type;
    }

    public void setContentType(String contentType) {
        this.type = contentType;
    }
}
