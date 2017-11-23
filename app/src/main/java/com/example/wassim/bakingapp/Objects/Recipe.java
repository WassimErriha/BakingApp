package com.example.wassim.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-06
 */

public class

Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    String mName;
    ArrayList<Ingredient> mIngredientArrayList;
    ArrayList<Step> mStepArrayList;


    public Recipe(String name, ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList) {
        mName = name;
        mIngredientArrayList = ingredientArrayList;
        mStepArrayList = stepArrayList;

    }

    protected Recipe(Parcel in) {
        this.mName = in.readString();
        this.mIngredientArrayList = new ArrayList<Ingredient>();
        in.readList(this.mIngredientArrayList, Ingredient.class.getClassLoader());
        this.mStepArrayList = new ArrayList<Step>();
        in.readList(this.mStepArrayList, Step.class.getClassLoader());
    }

    public String getmName() {
        return mName;
    }

    public ArrayList<Ingredient> getmIngredientArrayList() {
        return mIngredientArrayList;
    }

    public ArrayList<Step> getmStepArrayList() {
        return mStepArrayList;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "\n mName='" + mName + '\'' +
                "\n, mIngredientArrayList=" + mIngredientArrayList +
                "\n, mStepArrayList=" + mStepArrayList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeList(this.mIngredientArrayList);
        dest.writeList(this.mStepArrayList);
    }
}
