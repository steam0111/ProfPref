package com.example.stanislavk.profpref.di.services.firebase.models.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LasVegas on 11.07.2017.
 */

public class ModelCategories {

    private String nameCategory;
    private List<ModelQuestion> questions = new ArrayList<>();

    public ModelCategories() {

    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<ModelQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ModelQuestion> questions) {
        this.questions = questions;
    }
}
