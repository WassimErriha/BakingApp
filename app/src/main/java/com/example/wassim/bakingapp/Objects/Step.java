package com.example.wassim.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wassim on 2017-11-06
 */

public class Step implements Parcelable {

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
    int stepId;
    String shortDiscription;
    String description;
    String videoUrl;
    String thumbnailUrl;

    public Step(int stepId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {

        this.stepId = stepId;
        this.shortDiscription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected Step(Parcel in) {
        this.stepId = in.readInt();
        this.shortDiscription = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public int getStepId() {
        return stepId;
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
                "stepId=" + stepId +
                ", shortDiscription='" + shortDiscription + '\'' +
                ", description='" + description + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepId);
        dest.writeString(this.shortDiscription);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }
}
