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
    int mRecipeId;
    String mName;
    String mImage;
    ArrayList<Ingredient> mIngredientArrayList;
    ArrayList<Step> mStepArrayList;


    public Recipe(int recipeId, String name, String image, ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList) {
        mRecipeId = recipeId;
        mName = name;
        mImage = image;
        mIngredientArrayList = ingredientArrayList;
        mStepArrayList = stepArrayList;

    }

    protected Recipe(Parcel in) {
        this.mRecipeId = in.readInt();
        this.mName = in.readString();
        this.mImage = in.readString();
        this.mIngredientArrayList = new ArrayList<Ingredient>();
        in.readList(this.mIngredientArrayList, Ingredient.class.getClassLoader());
        this.mStepArrayList = new ArrayList<Step>();
        in.readList(this.mStepArrayList, Step.class.getClassLoader());
    }

    public int getmRecipeId() {
        return mRecipeId;
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

    public String getmImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "\n mName='" + mName + '\'' +
                "\n, mIngredientArrayList=" + mIngredientArrayList +
                "\n, mStepArrayList=" + mStepArrayList +
                "\n, mImage=" + mImage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRecipeId);
        dest.writeString(this.mName);
        dest.writeString(this.mImage);
        dest.writeList(this.mIngredientArrayList);
        dest.writeList(this.mStepArrayList);
    }
}
