package com.example.stanislavk.profpref.ui.test.models;

/**
 * Created by LasVegas on 14.07.2017.
 */

public class TestAnswerModel extends TestQuestionModel{

    private int mAnswer;
    private String mCategory;
    private float mAverageHappines;
    private float mPushHappines;



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

    public float getAverageHappines() {
        return mAverageHappines;
    }

    public void setAverageHappines(float averageHappines) {
        this.mAverageHappines = averageHappines;
    }

    public float getPushHappines() {
        return mPushHappines;
    }

    public void setPushHappines(float pushHappines) {
        this.mPushHappines = pushHappines;
    }
}
