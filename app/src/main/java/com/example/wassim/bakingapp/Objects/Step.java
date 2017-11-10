package com.example.wassim.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wassim on 2017-11-06
 */

public class Step implements Parcelable{

    String shortDiscription;
    String description;
    String videoUrl;
    String thumbnailUrl;

    public Step(String shortDescription, String description, String videoUrl, String thumbnailUrl) {

        this.shortDiscription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDiscription() {
        return shortDiscription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public String toString() {
        return "Step{" +
                "\n shortDiscription='" + shortDiscription + '\'' +
                "\n, description='" + description + '\'' +
                "\n, videoUrl='" + videoUrl + '\'' +
                "\n thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shortDiscription);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }

    protected Step(Parcel in) {
        this.shortDiscription = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
