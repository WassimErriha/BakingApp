package com.example.wassim.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Utils.JsonUtils;

import java.util.ArrayList;

public class RecipesLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    private static final String LOG_TAG = RecipesLoader.class.getName();

    String mUrl;

    public RecipesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        ArrayList<Recipe> recipes = JsonUtils.fetchRecipe();
        return recipes;
    }
}


