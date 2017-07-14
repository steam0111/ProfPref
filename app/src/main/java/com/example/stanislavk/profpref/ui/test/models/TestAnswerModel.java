package com.example.stanislavk.profpref.ui.test.models;

/**
 * Created by LasVegas on 14.07.2017.
 */

public class TestAnswerModel extends TestQuestionModel{

    private int mAnswer;
    private String mCategory;



    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int mAnswer) {
        this.mAnswer = mAnswer;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
