package com.example.stanislavk.profpref.ui.test.enums;

/**
 * Created by LasVegas on 12.01.2018.
 */

public enum QuestionContentType {

    GIF("gif"),
    JPG("jpg");

    private final String type;

    QuestionContentType(String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }
}
