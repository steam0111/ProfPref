package com.example.stanislavk.profpref.ui.results.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LasVegas on 13.01.2018.
 */

public class ResultShowingModel implements Parcelable{

    private String firestorageLink;
    private String contentType;

    public ResultShowingModel(String firestorageLink, String contentType) {
        this.firestorageLink = firestorageLink;
        this.contentType = contentType;
    }

    protected ResultShowingModel(Parcel in) {
        firestorageLink = in.readString();
        contentType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firestorageLink);
        dest.writeString(contentType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultShowingModel> CREATOR = new Creator<ResultShowingModel>() {
        @Override
        public ResultShowingModel createFromParcel(Parcel in) {
            return new ResultShowingModel(in);
        }

        @Override
        public ResultShowingModel[] newArray(int size) {
            return new ResultShowingModel[size];
        }
    };

    public String getFirestorageLink() {
        return firestorageLink;
    }

    public void setFirestorageLink(String firestorageLink) {
        this.firestorageLink = firestorageLink;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
