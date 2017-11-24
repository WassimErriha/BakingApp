package com.example.wassim.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wassim on 2017-11-06
 */

public class Ingredient implements Parcelable {

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    String quantity;
    String measure;
    String ingredient;

    public Ingredient(String quantity, String measure, String ingrediant) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingrediant;
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "\n quantity='" + quantity + '\'' +
                "\n , measure='" + measure + '\'' +
                "\n , ingredient='" + ingredient + '\'' +
                '}';
    }

    public String getConcatenatedString() {
        return quantity + " " + measure + " " + ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }
}
