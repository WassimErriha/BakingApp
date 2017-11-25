package com.example.wassim.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Utils.JsonUtils;

import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-25
 */

public class RecipesLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    public RecipesLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {
        return JsonUtils.fetchRecipe();
    }
}
